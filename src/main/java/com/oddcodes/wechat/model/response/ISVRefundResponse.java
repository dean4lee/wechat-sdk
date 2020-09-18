package com.oddcodes.wechat.model.response;

import com.oddcodes.wechat.model.base.PayResponse;
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
public class ISVRefundResponse extends PayResponse {

    private String refund_id;
    private String out_refund_no;
    private String create_time;
    private String refund_account;
    private Amount amount;
    private List<PromotionDetail> promotion_detail;

    @Data
    public static class Amount{

        private Integer refund;
        private Integer payer_refund;
        private Integer discount_refund;
        private String currency;
    }

    @Data
    public static class PromotionDetail{

        private String promotion_id;
        private String scope;
        private String type;
        private Integer amount;
        private Integer refund_amount;
    }
}
