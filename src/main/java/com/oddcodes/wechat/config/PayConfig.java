package com.oddcodes.wechat.config;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 微信支付配置参数
 *
 * @author dean.lee
 */
@Data
@Accessors(chain = true)
public class PayConfig {

    // 公众号id
    private String appid;

    // 微信支付商户号id
    private String mchid;

    // 微信支付默认的微信回调地址
    private String notifyUrl;

    // 微信支付证书私钥
    private String privateKey;

    // 微信支付证书号
    private String serialNo;

    // 微信支付v3密钥
    private String v3Key;

    // 微信支付的host
    private String host = "https://api.mch.weixin.qq.com";
}
