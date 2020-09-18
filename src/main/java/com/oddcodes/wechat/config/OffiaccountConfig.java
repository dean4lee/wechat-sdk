package com.oddcodes.wechat.config;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 公众号配置信息
 *
 * @author dean.lee
 */
@Data
@Accessors(chain = true)
public class OffiaccountConfig {

    // 公众号id
    private String appid;

    // 公众号appSecret
    private String appSecret;
}
