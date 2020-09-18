package com.oddcodes.wechat.model.response;

import com.oddcodes.wechat.model.base.OffiaccountResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

/**
 * @author dean.lee
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserinfoResponse extends OffiaccountResponse {

    private String openid;
    private String nickname;
    private Integer sex;
    private String province;
    private String city;
    private String country;
    private String headimgurl;
    private List<String> privilege;
    private String unionid;
    private String language;
}
