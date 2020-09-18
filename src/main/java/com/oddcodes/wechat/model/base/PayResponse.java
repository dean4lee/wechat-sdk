package com.oddcodes.wechat.model.base;

import lombok.Data;

/**
 * @author dean.lee
 */
@Data
public class PayResponse {

    private String code;

    private String message;

    private Detail detail;

    @Data
    public static class Detail {
        private String field;
        private String value;
        private String issue;
        private String location;
    }
}
