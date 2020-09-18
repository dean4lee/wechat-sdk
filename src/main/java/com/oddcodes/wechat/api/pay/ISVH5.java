package com.oddcodes.wechat.api.pay;

import com.alibaba.fastjson.JSON;
import com.oddcodes.wechat.config.PayConfig;
import com.oddcodes.wechat.model.JSONParam;
import com.oddcodes.wechat.model.response.H5Response;
import com.oddcodes.wechat.util.HttpUtil;
import com.oddcodes.wechat.util.PayUtil;
import org.apache.http.client.methods.HttpPost;

/**
 * @author dean.lee
 */
public class ISVH5 {

    private PayConfig config;
    private PayUtil payUtil;

    static final String H5_URL = "/v3/pay/partner/transactions/h5";

    private JSONParam body;

    public ISVH5(PayConfig config) {
        this.config = config;
        this.payUtil = new PayUtil(config);
        this.body = new JSONParam();
    }

    public ISVH5 set(String key, Object value) {
        this.body.put(key, value);
        return this;
    }

    public ISVH5 setNotifyUrl(String notifyUrl) {
        this.body.put("notify_url", notifyUrl);
        return this;
    }

    public ISVH5 setAttach(String attach) {
        this.body.put("attach", attach);
        return this;
    }

    public H5Response create(String sub_mchid, String description, String out_trade_no, Integer total, String payer_client_ip, String type) {
        body.put("sub_mchid", sub_mchid)
                .put("description", description)
                .put("out_trade_no", out_trade_no)
                .put("sp_appid", config.getAppid())
                .put("sp_mchid", config.getMchid());

        JSONParam amount = new JSONParam().put("total", total);
        JSONParam h5Info = new JSONParam().put("type", type);
        JSONParam sceneInfo = new JSONParam()
                .put("payer_client_ip", payer_client_ip)
                .put("h5_info", h5Info);
        if (body.get("amount") == null) {
            body.put("amount", amount);
        } else {
            body.getJSONObject("amount").putAll(amount);
        }
        if (body.get("scene_info") == null) {
            body.put("scene_info", sceneInfo);
        } else {
            body.getJSONObject("scene_info").putAll(sceneInfo);
        }

        if (body.getString("notify_url") == null) {
            body.put("notify_url", config.getNotifyUrl());
        }

        String StringBody = body.toJSONString();
        String token = payUtil.getV3Token(HttpPost.METHOD_NAME, H5_URL, StringBody);
        String result = HttpUtil.v3Post(config.getHost() + H5_URL, StringBody, token);

        return JSON.parseObject(result, H5Response.class);
    }
}
