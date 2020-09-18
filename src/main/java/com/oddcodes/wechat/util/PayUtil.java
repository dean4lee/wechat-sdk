package com.oddcodes.wechat.util;

import com.alibaba.fastjson.JSON;
import com.oddcodes.wechat.api.pay.ISVJSAPI;
import com.oddcodes.wechat.config.PayConfig;
import com.oddcodes.wechat.model.notify.ISVPayNotify;
import com.oddcodes.wechat.model.notify.ISVRefundNotify;
import com.oddcodes.wechat.model.param.InvokeJSAPIPayParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dean.lee
 */
public class PayUtil {

    private Logger log = LoggerFactory.getLogger(ISVJSAPI.class);

    private PayConfig config;
    private AesUtil aesUtil;

    public PayUtil(PayConfig config) {
        this.config = config;
        this.aesUtil = new AesUtil(config.getV3Key());
    }

    /**
     * 获取v3接口token，
     * nonceStr为UUID去除 -
     * timestamp为当前时间戳
     *
     * @param method 请求方法
     * @param url    请求地址
     * @param body   请求参数体
     * @return v3 token
     */
    public String getV3Token(String method, String url, String body) {
        String nonceStr = StringUtil.uuid();
        String timestamp = StringUtil.currentTime();
        return this.getV3Token(method, url, body, nonceStr, timestamp);
    }

    /**
     * 获取v3接口token
     *
     * @param method    请求方法
     * @param url       请求地址
     * @param body      请求参数体
     * @param nonceStr  随机字符串
     * @param timestamp 时间戳
     * @return v3 token
     */
    public String getV3Token(String method, String url, String body, String nonceStr, String timestamp) {
        String signStr = this.v3SignStr(method, url, body, nonceStr, timestamp);
        String sign = SignUtil.sha256WithRsaSign(config.getPrivateKey(), signStr);

        return "mchid=\"" + config.getMchid() + "\","
                + "nonce_str=\"" + nonceStr + "\","
                + "timestamp=\"" + timestamp + "\","
                + "serial_no=\"" + config.getSerialNo() + "\","
                + "signature=\"" + sign + "\"";
    }

    /**
     * 获取v3接口请求的签名串
     *
     * @param method    请求方法
     * @param url       请求地址
     * @param body      请求参数体
     * @param nonceStr  随机字符串
     * @param timestamp 时间戳
     * @return 签名串
     */
    public String v3SignStr(String method, String url, String body, String nonceStr, String timestamp) {
        return method + "\n"
                + url + "\n"
                + timestamp + "\n"
                + nonceStr + "\n"
                + body + "\n";
    }

    /**
     * 获取调起JSAPI支付的签名串
     *
     * @param prepayId  预支付id
     * @param nonceStr  随机字符串
     * @param timestamp 时间戳
     * @return 签名串
     */
    public String jsapiSignStr(String prepayId, String nonceStr, String timestamp) {
        return config.getAppid() + "\n"
                + timestamp + "\n"
                + nonceStr + "\n"
                + "prepay_id=" + prepayId + "\n";
    }

    /**
     * 获取调起JSAPI支付的参数
     *
     * @param prepayId 微信预支付id
     * @return InvokeJSAPIPayParam
     */
    public InvokeJSAPIPayParam invokeJSAPIPay(String prepayId) {
        String nonceStr = StringUtil.uuid();
        String timestamp = StringUtil.currentTime();

        String signStr = this.jsapiSignStr(prepayId, nonceStr, timestamp);
        String sign = SignUtil.sha256WithRsaSign(config.getPrivateKey(), signStr);

        InvokeJSAPIPayParam param = new InvokeJSAPIPayParam()
                .setAppId(config.getAppid())
                .setTimeStamp(timestamp)
                .setNonceStr(nonceStr)
                .setWxPackage("prepay_id=" + prepayId)
                .setSignType("RSA")
                .setPaySign(sign);

        if (log.isDebugEnabled()) {
            log.debug("wechat-sdk >>> JSAPI调起参数: {}", JSON.toJSONString(param));
        }
        return param;
    }

    /**
     * 解密支付异步通知参数
     *
     * @param associatedData 附加数据
     * @param nonce          加密使用的随机串
     * @param ciphertext     Base64编码后的开启/停用结果数据密文
     * @return 解密后的异步通知参数
     */
    public ISVPayNotify decryptISVPayNotify(String associatedData, String nonce, String ciphertext) {
        return JSON.parseObject(decryptNotify(associatedData, nonce, ciphertext), ISVPayNotify.class);
    }

    public ISVRefundNotify decryptISVRefundNotify(String associatedData, String nonce, String ciphertext) {
        return JSON.parseObject(decryptNotify(associatedData, nonce, ciphertext), ISVRefundNotify.class);
    }

    public String decryptNotify(String associatedData, String nonce, String ciphertext) {
        AesUtil aesUtil = new AesUtil(config.getV3Key());
        return aesUtil.decryptToString(associatedData, nonce, ciphertext);
    }
}
