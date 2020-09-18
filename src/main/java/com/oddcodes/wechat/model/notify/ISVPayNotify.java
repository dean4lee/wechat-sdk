package com.oddcodes.wechat.model.notify;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author dean.lee
 */
@Data
public class ISVPayNotify {

    private String sp_appid;
    private String sp_mchid;
    private String sub_appid;
    private String sub_mchid;
    private String out_trade_no;
    private String transaction_id;
    private String trade_type;
    private String trade_state;
    private String trade_state_desc;
    private String bank_type;
    private String attach;
    private Date success_time;
    private Payer payer;
    private Amount amount;
    private SceneInfo scene_info;
    private List<PromotionDetail> promotion_detail;

    @Data
    public static class Payer {

        private String sp_openid;
        private String sub_openid;
    }

    @Data
    public static class Amount {

        private Integer total;
        private Integer payer_total;
        private String currency;
        private String payer_currency;
    }

    @Data
    public static class SceneInfo {

        private String device_id;
    }

    @Data
    public static class PromotionDetail {

        private String coupon_id;
        private String name;
        private String scope;
        private String type;
        private Integer amount;
        private String stock_id;
        private Integer wechatpay_contribute;
        private Integer merchant_contribute;
        private Integer other_contribute;
        private String currency;
        private List<GoodsDetail> goods_detail;

        @Data
        public static class GoodsDetail {

            private String goods_id;
            private Integer quantity;
            private Integer unit_price;
            private Integer discount_amount;
            private String goods_remark;
        }
    }
}
