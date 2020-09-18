package com.oddcodes.wechat.api.offiaccount;

import com.alibaba.fastjson.JSON;
import com.oddcodes.wechat.config.OffiaccountConfig;
import com.oddcodes.wechat.model.param.InvokeJSSDKParam;
import com.oddcodes.wechat.model.response.TicketResponse;
import com.oddcodes.wechat.util.HttpUtil;
import com.oddcodes.wechat.util.SignUtil;
import com.oddcodes.wechat.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * jssdk
 * 官方文档：https://developers.weixin.qq.com/doc/offiaccount/OA_Web_Apps/JS-SDK.html#62
 *
 * @author dean.lee
 */
public class JsSdk {

    private Logger log = LoggerFactory.getLogger(JsSdk.class);

    private OffiaccountConfig config;

    static final String TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=%s&type=jsapi";

    public JsSdk(OffiaccountConfig config) {
        this.config = config;
    }

    /**
     * 获取jsapi_ticket
     *
     * @param accessToken {@link Base#getToken()}
     * @return TicketResponse
     */
    public TicketResponse getTicket(String accessToken) {
        String url = String.format(TICKET_URL, accessToken);
        String result = HttpUtil.get(url);
        return JSON.parseObject(result, TicketResponse.class);
    }

    /**
     * 获取调起jssdk的参数
     *
     * @param jsapiTicket 获取方式{@link JsSdk#getTicket(String)}
     * @param url         调用JS-SDK的网页url
     * @return InvokeJSSDKParam
     */
    public InvokeJSSDKParam invokeJSSDK(String jsapiTicket, String url) {
        String nonceStr = StringUtil.uuid();
        String timestamp = StringUtil.currentTime();

        String signStr = this.signStr(jsapiTicket, nonceStr, timestamp, url);
        String sign = SignUtil.sha1Sign(signStr);

        InvokeJSSDKParam param = new InvokeJSSDKParam()
                .setAppId(config.getAppid())
                .setNonceStr(nonceStr)
                .setTimestamp(timestamp)
                .setSignature(sign);

        if (log.isDebugEnabled()) {
            log.debug("wechat-sdk >>> JSSDK调起参数: {}", JSON.toJSONString(param));
        }

        return param;
    }

    /**
     * JS-SDK签名串
     *
     * @param jsapiTicket 获取方式{@link JsSdk#getTicket(String)}
     * @param nonceStr    随机字符串
     * @param timestamp   时间戳
     * @param url         调用JS-SDK的网页url
     * @return 签名串
     */
    public String signStr(String jsapiTicket, String nonceStr, String timestamp, String url) {
        return "jsapi_ticket=" + jsapiTicket + "&noncestr="
                + nonceStr + "&timestamp=" + timestamp + "&url=" + url;
    }
}
