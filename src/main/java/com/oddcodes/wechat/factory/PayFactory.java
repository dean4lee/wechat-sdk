package com.oddcodes.wechat.factory;

import com.oddcodes.wechat.api.pay.ISVH5;
import com.oddcodes.wechat.api.pay.ISVJSAPI;
import com.oddcodes.wechat.api.pay.ISVNative;
import com.oddcodes.wechat.api.pay.ISVRefund;
import com.oddcodes.wechat.config.PayConfig;
import com.oddcodes.wechat.util.PayUtil;

/**
 * @author dean.lee
 */
public class PayFactory {

    private PayConfig config;

    public PayFactory(PayConfig config) {
        this.config = config;
    }

    public PayUtil PayUtil(){
        return new PayUtil(config);
    }

    public ISVJSAPI ISVJSAPI(){
        return new ISVJSAPI(config);
    }

    public ISVNative ISVNative(){
        return new ISVNative(config);
    }

    public ISVH5 ISVH5(){
        return new ISVH5(config);
    }

    public ISVRefund ISVRefund(){
        return new ISVRefund(config);
    }
}
