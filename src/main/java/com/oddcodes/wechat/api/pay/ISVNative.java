package com.oddcodes.wechat.api.pay;

import com.alibaba.fastjson.JSON;
import com.oddcodes.wechat.config.PayConfig;
import com.oddcodes.wechat.model.JSONParam;
import com.oddcodes.wechat.model.response.NativeResponse;
import com.oddcodes.wechat.util.HttpUtil;
import com.oddcodes.wechat.util.PayUtil;
import org.apache.http.client.methods.HttpPost;

/**
 * @author dean.lee
 */
public class ISVNative {

    private PayConfig config;
    private PayUtil payUtil;

    static final String NATIVE_URL = "/v3/pay/partner/transactions/native";

    private JSONParam body;

    public ISVNative(PayConfig config){
        this.config = config;
        this.payUtil = new PayUtil(config);
        this.body = new JSONParam();
    }

    public ISVNative set(String key, Object value) {
        this.body.put(key, value);
        return this;
    }

    public ISVNative setNotifyUrl(String notifyUrl) {
        this.body.put("notify_url", notifyUrl);
        return this;
    }

    public ISVNative setAttach(String attach) {
        this.body.put("attach", attach);
        return this;
    }

    public NativeResponse create(String sub_mchid, String description, String out_trade_no, Integer total) {
        body.put("sub_mchid", sub_mchid)
                .put("description", description)
                .put("out_trade_no", out_trade_no)
                .put("sp_appid", config.getAppid())
                .put("sp_mchid", config.getMchid());

        JSONParam amount = new JSONParam().put("total", total);
        if (body.get("amount") == null) {
            body.put("amount", amount);
        } else {
            body.getJSONObject("amount").putAll(amount);
        }
        if (body.getString("notify_url") == null) {
            body.put("notify_url", config.getNotifyUrl());
        }

        String StringBody = body.toJSONString();
        String token = payUtil.getV3Token(HttpPost.METHOD_NAME, NATIVE_URL, StringBody);
        String result = HttpUtil.v3Post(config.getHost() + NATIVE_URL, StringBody, token);

        return JSON.parseObject(result, NativeResponse.class);
    }
}
