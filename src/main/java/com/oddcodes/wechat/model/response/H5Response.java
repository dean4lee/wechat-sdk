package com.oddcodes.wechat.model.response;

import com.oddcodes.wechat.model.base.PayResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author dean.lee
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class H5Response extends PayResponse {

    private String h5_url;
}
