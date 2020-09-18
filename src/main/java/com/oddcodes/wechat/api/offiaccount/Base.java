package com.oddcodes.wechat.api.offiaccount;

import com.alibaba.fastjson.JSON;
import com.oddcodes.wechat.config.OffiaccountConfig;
import com.oddcodes.wechat.model.response.AccessTokenResponse;
import com.oddcodes.wechat.model.response.CallBackIpResponse;
import com.oddcodes.wechat.util.HttpUtil;

/**
 * 微信公众号基础支持
 *
 * @author dean.lee
 */
public class Base {

    private OffiaccountConfig config;

    static final String TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
    static final String CALLBACKIP_URL = "https://api.weixin.qq.com/cgi-bin/getcallbackip?access_token=%s";

    public Base(OffiaccountConfig config){
        this.config = config;
    }

    /**
     * 获取access_token
     * 官方文档：https://developers.weixin.qq.com/doc/offiaccount/Basic_Information/Get_access_token.html
     *
     * @return access_token
     */
    public AccessTokenResponse getToken(){
        String url = String.format(TOKEN_URL, config.getAppid(), config.getAppSecret());
        String result = HttpUtil.get(url);
        return JSON.parseObject(result, AccessTokenResponse.class);
    }

    /**
     * 获取微信服务器IP地址
     * 官方文档：https://developers.weixin.qq.com/doc/offiaccount/Basic_Information/Get_the_WeChat_server_IP_address.html
     *
     * @param accessToken {@link Base#getToken()}
     * @return 微信服务器IP地址
     */
    public CallBackIpResponse getCallbackip(String accessToken){
        String url = String.format(CALLBACKIP_URL, accessToken);
        String result = HttpUtil.get(url);
        return JSON.parseObject(result, CallBackIpResponse.class);
    }
}
