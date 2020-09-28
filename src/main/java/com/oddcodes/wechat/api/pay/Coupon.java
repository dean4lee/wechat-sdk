package com.oddcodes.wechat.api.pay;

import com.alibaba.fastjson.JSON;
import com.oddcodes.wechat.config.PayConfig;
import com.oddcodes.wechat.model.JSONParam;
import com.oddcodes.wechat.model.response.CouponSendResponse;
import com.oddcodes.wechat.util.HttpUtil;
import com.oddcodes.wechat.util.PayUtil;
import com.oddcodes.wechat.util.StringUtil;
import org.apache.http.client.methods.HttpPost;

/**
 * @author dean.lee
 */
public class Coupon {

    private PayConfig config;
    private PayUtil payUtil;

    static final String SEND_URL = "/v3/marketing/favor/users/%s/coupons";

    public Coupon(PayConfig config){
        this.config = config;
        this.payUtil = new PayUtil(config);
    }

    public CouponSendResponse send(String stock_id, String openid) {
        String url = String.format(SEND_URL, openid);
        String out_request_no = config.getMchid() + StringUtil.currentDate() + StringUtil.uuid();
        JSONParam body = new JSONParam()
                .put("stock_id", stock_id)
                .put("out_request_no", out_request_no)
                .put("appid", config.getAppid())
                .put("stock_creator_mchid", config.getMchid());

        String StringBody = body.toJSONString();
        String token = payUtil.getV3Token(HttpPost.METHOD_NAME, url, StringBody);
        String result = HttpUtil.v3Post(config.getHost() + url, StringBody, token);

        return JSON.parseObject(result, CouponSendResponse.class);
    }
}
