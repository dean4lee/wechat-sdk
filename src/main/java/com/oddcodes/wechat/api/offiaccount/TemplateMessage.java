package com.oddcodes.wechat.api.offiaccount;

import com.alibaba.fastjson.JSON;
import com.oddcodes.wechat.model.response.TemplateMessageSendResponse;
import com.oddcodes.wechat.util.HttpUtil;

/**
 * 模板消息
 * 官方文档：https://developers.weixin.qq.com/doc/offiaccount/Message_Management/Template_Message_Interface.html
 *
 * @author dean.lee
 */
public class TemplateMessage {

    static final String SEND_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=%s";

    /**
     * 发送模板消息
     *
     * @param accessToken {@link Base#getToken()}
     * @param body 请求的body
     * @return TemplateMessageSendResponse
     */
    public TemplateMessageSendResponse send(String accessToken, String body){
        String url = String.format(SEND_URL, accessToken);
        String result = HttpUtil.post(url, body);
        return JSON.parseObject(result, TemplateMessageSendResponse.class);
    }
}
