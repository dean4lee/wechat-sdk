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
public class TicketResponse extends OffiaccountResponse {

    private String ticket;
    private Integer expires_in;
}
