package com.oddcodes.wechat.api.pay;

import com.alibaba.fastjson.JSON;
import com.oddcodes.wechat.config.PayConfig;
import com.oddcodes.wechat.model.JSONParam;
import com.oddcodes.wechat.model.response.ISVRefundResponse;
import com.oddcodes.wechat.util.HttpUtil;
import com.oddcodes.wechat.util.PayUtil;
import org.apache.http.client.methods.HttpPost;

/**
 * @author dean.lee
 */
public class ISVRefund {

    private PayConfig config;
    private PayUtil payUtil;

    static final String REFUND_URL = "/v3/ecommerce/refunds/apply";

    private JSONParam body;

    public ISVRefund(PayConfig config) {
        this.config = config;
        this.payUtil = new PayUtil(config);
        this.body = new JSONParam();
    }

    public ISVRefund set(String key, Object value) {
        this.body.put(key, value);
        return this;
    }

    public ISVRefund setNotifyUrl(String notifyUrl) {
        this.body.put("notify_url", notifyUrl);
        return this;
    }

    public ISVRefund setTransactionId(String transaction_id) {
        this.body.put("transaction_id", transaction_id);
        return this;
    }

    public ISVRefund setOutTradeNo(String out_trade_no) {
        this.body.put("out_trade_no", out_trade_no);
        return this;
    }

    /**
     * 调用退款前必须调用setOutTradeNo或setTransactionId
     *
     * @param sub_mchid     子商户号
     * @param out_refund_no 商户退款单号
     * @param total         订单金额
     * @param refund        退款金额
     * @return ISVRefundResponse
     */
    public ISVRefundResponse refund(String sub_mchid, String out_refund_no, Integer total, Integer refund) {
        JSONParam amount = new JSONParam()
                .put("refund", refund)
                .put("total", refund)
                .put("currency", "CNY");

        body.put("sp_appid", config.getAppid())
                .put("sub_mchid", sub_mchid)
                .put("out_refund_no", out_refund_no)
                .put("amount", amount);

        String StringBody = body.toJSONString();
        String token = payUtil.getV3Token(HttpPost.METHOD_NAME, REFUND_URL, StringBody);
        String result = HttpUtil.v3Post(config.getHost() + REFUND_URL, StringBody, token);

        return JSON.parseObject(result, ISVRefundResponse.class);
    }
}
