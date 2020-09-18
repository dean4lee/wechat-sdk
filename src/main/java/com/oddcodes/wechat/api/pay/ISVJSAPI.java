package com.oddcodes.wechat.api.pay;

import com.alibaba.fastjson.JSON;
import com.oddcodes.wechat.config.PayConfig;
import com.oddcodes.wechat.model.JSONParam;
import com.oddcodes.wechat.model.response.JSAPIResponse;
import com.oddcodes.wechat.util.HttpUtil;
import com.oddcodes.wechat.util.PayUtil;
import org.apache.http.client.methods.HttpPost;

/**
 * @author dean.lee
 */
public class ISVJSAPI {

    private PayConfig config;
    private PayUtil payUtil;

    static final String JSAPI_URL = "/v3/pay/partner/transactions/jsapi";

    private JSONParam body;

    public ISVJSAPI(PayConfig config) {
        this.config = config;
        this.payUtil = new PayUtil(config);
        this.body = new JSONParam();
    }

    public ISVJSAPI set(String key, Object value) {
        this.body.put(key, value);
        return this;
    }

    public ISVJSAPI setNotifyUrl(String notifyUrl) {
        this.body.put("notify_url", notifyUrl);
        return this;
    }

    public ISVJSAPI setAttach(String attach) {
        this.body.put("attach", attach);
        return this;
    }

    /**
     * 创建jsapi支付订单
     *
     * @param sub_mchid    子商户号
     * @param description  订单描述
     * @param out_trade_no 商户订单号
     * @param total        订单金额
     * @param sp_openid    支付用户openid
     * @return JSAPIResponse
     */
    public JSAPIResponse create(String sub_mchid, String description, String out_trade_no, Integer total, String sp_openid) {
        body.put("sub_mchid", sub_mchid)
                .put("description", description)
                .put("out_trade_no", out_trade_no)
                .put("sp_appid", config.getAppid())
                .put("sp_mchid", config.getMchid());

        JSONParam amount = new JSONParam().put("total", total);
        JSONParam payer = new JSONParam().put("sp_openid", sp_openid);

        if (body.get("amount") == null) {
            body.put("amount", amount);
        } else {
            body.getJSONObject("amount").putAll(amount);
        }
        if (body.get("payer") == null) {
            body.put("payer", payer);
        } else {
            body.getJSONObject("payer").putAll(amount);
        }

        if (body.getString("notify_url") == null) {
            body.put("notify_url", config.getNotifyUrl());
        }

        String StringBody = body.toJSONString();
        String token = payUtil.getV3Token(HttpPost.METHOD_NAME, JSAPI_URL, StringBody);
        String result = HttpUtil.v3Post(config.getHost() + JSAPI_URL, StringBody, token);

        return JSON.parseObject(result, JSAPIResponse.class);
    }

}
