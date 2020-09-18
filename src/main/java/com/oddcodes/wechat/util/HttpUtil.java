package com.oddcodes.wechat.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * http请求工具类
 *
 * @author dean.lee
 */
public class HttpUtil {

    private static Logger log = LoggerFactory.getLogger(HttpUtil.class);

    private static final String VERSION = "1.0.0";

    private static final String JAVA_VERSION = System.getProperty("java.version");

    private static final String USER_AGENT = "Wechat-SDK/" + VERSION + " (Java/" + JAVA_VERSION + ")";

    /**
     * v3接口get请求
     *
     * @param url   请求地址
     * @param token v3请求token
     * @return 请求的返回值
     */
    public static String v3Get(String url, String token) {
        HttpGet get = new HttpGet(url);
        return request(get, token);
    }

    /**
     * v3接口post请求
     *
     * @param url   请求地址
     * @param body  请求参数体
     * @param token v3请求token
     * @return 请求的返回值
     */
    public static String v3Post(String url, String body, String token) {
        if (log.isDebugEnabled()) {
            log.debug("wechat-sdk >>> 请求参数: {}", body);
        }
        HttpPost post = new HttpPost(url);
        post.setEntity(new StringEntity(body, "utf-8"));
        return request(post, token);
    }

    /**
     * 普通的get请求
     *
     * @param url 请求地址
     * @return 请求的返回值
     */
    public static String get(String url) {
        return v3Get(url, null);
    }

    /**
     * 普通的post请求
     *
     * @param url  请求地址
     * @param body 请求参数体
     * @return 请求的返回值
     */
    public static String post(String url, String body) {
        return v3Post(url,  body, null);
    }

    /**
     * 接口请求
     *
     * @param request 请求
     * @param token   v3请求token
     * @return 请求微信接口的返回结果
     */
    private static String request(HttpUriRequest request, String token) {
        HttpClient client = HttpClientBuilder.create().build();
        request.setHeader("Content-Type", "application/json");
        request.setHeader("Accept", "application/json");
        request.setHeader("User-Agent", USER_AGENT);
        if (token != null) {
            request.setHeader("Authorization", "WECHATPAY2-SHA256-RSA2048 " + token);
        }
        String result = null;
        try {
            HttpResponse execute = client.execute(request);
            HttpEntity entity = execute.getEntity();
            if(entity != null) {
                result = EntityUtils.toString(entity, StandardCharsets.UTF_8);
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
        if (log.isDebugEnabled()) {
            log.debug("wechat-sdk >>> 请求url: {}\n返回结果: {}", request.getURI(), result);
        }
        return result;
    }
}
