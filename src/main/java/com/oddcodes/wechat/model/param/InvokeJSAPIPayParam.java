package com.oddcodes.wechat.model.param;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * JSAPI调起支付的参数
 *
 * @author dean.lee
 */
@Data
@Accessors(chain = true)
public class InvokeJSAPIPayParam {

    private String appId;
    private String timeStamp;
    private String nonceStr;
    private String wxPackage;
    private String signType;
    private String paySign;
}
