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
public class CallBackIpResponse extends OffiaccountResponse {

    private List<String> ip_list;
}
