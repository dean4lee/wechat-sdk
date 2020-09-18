package com.oddcodes.wechat.model.base;

import lombok.Data;

/**
 * 公众号公共的返回参数
 *
 * @author dean.lee
 */
@Data
public class OffiaccountResponse {

    private Integer errcode;

    private String errmsg;
}
