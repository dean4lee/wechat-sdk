package com.oddcodes.wechat.model.notify;

import lombok.Data;

/**
 * @author dean.lee
 */
@Data
public class ISVRefundNotify {

    private String sp_mchid;
    private String sub_mchid;
    private String out_trade_no;
    private String transaction_id;
    private String out_refund_no;
    private String refund_id;
    private String refund_status;
    private String success_time;
    private String user_received_account;
    private String refund_account;
    private String amount;

    @Data
    public static class Amount{

        private Integer total;
        private Integer refund;
        private Integer payer_total;
        private Integer payer_refund;
    }
}
