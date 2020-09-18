package com.oddcodes.wechat.factory;

import com.oddcodes.wechat.api.offiaccount.Base;
import com.oddcodes.wechat.api.offiaccount.JsSdk;
import com.oddcodes.wechat.api.offiaccount.OAuth;
import com.oddcodes.wechat.api.offiaccount.TemplateMessage;
import com.oddcodes.wechat.config.OffiaccountConfig;

/**
 * 公众号相关的操作
 *
 * @author dean.lee
 */
public class OffiaccountFactory {

    private OffiaccountConfig config;

    public OffiaccountFactory(OffiaccountConfig config) {
        this.config = config;
    }

    /**
     * 微信公众号基础支持
     *
     * @return Base
     */
    public Base Base() {
        return new Base(config);
    }

    /**
     * 模板消息
     *
     * @return TemplateMessage
     */
    public TemplateMessage TemplateMessage() {
        return new TemplateMessage();
    }

    /**
     * 网页授权
     *
     * @return OAuth
     */
    public OAuth OAuth() {
        return new OAuth(config);
    }

    /**
     * js-sdk操作
     *
     * @return JsSdk
     */
    public JsSdk JsSdk() {
        return new JsSdk(config);
    }
}
