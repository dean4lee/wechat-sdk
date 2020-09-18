package com.oddcodes.wechat.model.param;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * JS-SDK配置参数
 *
 * @author dean.lee
 */
@Data
@Accessors(chain = true)
public class InvokeJSSDKParam {

    private String appId;
    private String timestamp;
    private String nonceStr;
    private String signature;
}
