package com.oddcodes.wechat.api.offiaccount;

import com.alibaba.fastjson.JSON;
import com.oddcodes.wechat.config.OffiaccountConfig;
import com.oddcodes.wechat.model.response.OAuthTokenResponse;
import com.oddcodes.wechat.model.response.UserinfoResponse;
import com.oddcodes.wechat.util.HttpUtil;

/**
 * 网页授权
 * 官方文档：https://developers.weixin.qq.com/doc/offiaccount/OA_Web_Apps/Wechat_webpage_authorization.html
 *
 * @author dean.lee
 */
public class OAuth {

    private OffiaccountConfig config;

    static final String OAUTH_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";
    static final String REFRESH_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=%s&grant_type=refresh_token&refresh_token=%s";
    static final String USERINFO_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN";

    public OAuth(OffiaccountConfig config){
        this.config = config;
    }

    /**
     * 通过code换取网页授权access_token
     *
     * @param code code
     * @return OAuthTokenResponse
     */
    public OAuthTokenResponse getToken(String code){
        String url = String.format(OAUTH_URL, config.getAppid(), config.getAppSecret(), code);
        String result = HttpUtil.get(url);
        return JSON.parseObject(result, OAuthTokenResponse.class);
    }

    /**
     * 刷新access_token（如果需要）
     *
     * @param refreshToken 填写通过access_token获取到的refresh_token参数
     * @return OAuthTokenResponse
     */
    public OAuthTokenResponse refreshToken(String refreshToken){
        String url = String.format(REFRESH_TOKEN_URL, config.getAppid(), refreshToken);
        String result = HttpUtil.get(url);
        return JSON.parseObject(result, OAuthTokenResponse.class);
    }

    /**
     * 拉取用户信息(需scope为 snsapi_userinfo)
     *
     * @param accessToken 网页授权接口调用凭证
     * @param openid 用户的唯一标识
     * @return UserinfoResponse
     */
    public UserinfoResponse userinfo(String accessToken, String openid){
        String url = String.format(USERINFO_URL, accessToken, openid);
        String result = HttpUtil.get(url);
        return JSON.parseObject(result, UserinfoResponse.class);
    }
}
