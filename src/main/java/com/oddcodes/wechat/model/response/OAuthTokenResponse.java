package com.oddcodes.wechat.model.response;

import com.oddcodes.wechat.model.base.OffiaccountResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author dean.lee
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OAuthTokenResponse extends OffiaccountResponse {

    private String access_token;
    private Integer expires_in;
    private String refresh_token;
    private String openid;
    private String scope;
}
