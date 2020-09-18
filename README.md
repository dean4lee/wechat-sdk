### 微信sdk

微信支付采用apiv3版本

#### 快速开始
```java
import com.oddcodes.wechat.config.OffiaccountConfig;
import com.oddcodes.wechat.config.PayConfig;
import com.oddcodes.wechat.factory.OffiaccountFactory;
import com.oddcodes.wechat.factory.PayFactory;
import com.oddcodes.wechat.model.notify.ISVPayNotify;
import com.oddcodes.wechat.model.param.InvokeJSAPIPayParam;
import com.oddcodes.wechat.model.response.JSAPIResponse;
import com.oddcodes.wechat.model.response.OAuthTokenResponse;

public class Main {

    public static void main(String[] args) {
        // 公众号操作
        OffiaccountFactory offiaccountFactory = new OffiaccountFactory(getOffiaccountConfig());
        OAuthTokenResponse code = offiaccountFactory.OAuth().getToken("code");
        System.out.println(code);

        // 微信服务商JSAPI支付
        PayFactory payFactory = new PayFactory(getPayConfig());
        // JSAPI下单
        JSAPIResponse jsapiResponse = payFactory.ISVJSAPI().create("sub_mchid", "测试支付", "111000", 1, "openid");
        System.out.println(jsapiResponse);
        // 获取调起JSAPI支付的参数
        InvokeJSAPIPayParam invokeJSAPIPayParam = payFactory.PayUtil().invokeJSAPIPay(jsapiResponse.getPrepay_id());
        // 解析支付异步通知参数
        ISVPayNotify isvPayNotify = payFactory.PayUtil().decryptISVPayNotify("", "", "");
        System.out.println(isvPayNotify);

        // JSAPI下单设置参数
        JSAPIResponse jsapiResponse1 = payFactory.ISVJSAPI().set("参数名", "参数值")
                .setAttach("附加参数")
                .setNotifyUrl("异步通知地址") // 异步通知地址优先使用此参数，如果未设置默认使用PayConfig中的NotifyUrl
                .create("", "", "", 1, "");
        // ...
    }

    public static OffiaccountConfig getOffiaccountConfig(){
        return new OffiaccountConfig()
                .setAppid("appid")
                .setAppSecret("secret");
    }

    public static PayConfig getPayConfig(){
        return new PayConfig()
                .setAppid("appid")
                .setMchid("mchid")
                .setPrivateKey("privateKey")
                .setSerialNo("serialNo")
                .setV3Key("v3Key")
                .setNotifyUrl("支付异步通知地址");
    }
}
```

#### 实现的功能，后续会根据业务补充

| 场景 | 类型 | 方法 | 描述 |
| :-----: | :----: | :----: |:----: |
| 微信公众号（OffiaccountFactory） | Base | getToken | 获取AccessToken |
| 微信公众号（OffiaccountFactory） | Base | getCallbackip | 获取微信服务器IP地址 |
| 微信公众号（OffiaccountFactory） | OAuth | getToken | 获取网页授权access_token |
| 微信公众号（OffiaccountFactory） | OAuth | refreshToken | 刷新access_token |
| 微信公众号（OffiaccountFactory） | OAuth | userinfo | 拉取用户信息(需scope为 snsapi_userinfo) |
| 微信公众号（OffiaccountFactory） | JsSdk | getTicket | 获取jsapi_ticket |
| 微信公众号（OffiaccountFactory） | JsSdk | invokeJSSDK | 获取调起jssdk的参数 |
| 微信公众号（OffiaccountFactory） | TemplateMessage | send | 发送公众号模板消息 |
| 微信支付（PayFactory）  | PayUtil | getV3Token | 获取v3接口的token |
| 微信支付（PayFactory）  | PayUtil | invokeJSAPIPay | 获取调起JSAPI支付的参数 |
| 微信支付（PayFactory）  | PayUtil | decryptISVPayNotify | 解密服务商支付异步通知参数 |
| 微信支付（PayFactory）  | PayUtil | decryptISVRefundNotify | 解密服务商退款异步通知参数 |
| 微信支付（PayFactory）  | ISVJSAPI | create | 服务商JSAPI/小程序下单 |
| 微信支付（PayFactory）  | ISVNative | create | 服务商Native下单 |
| 微信支付（PayFactory）  | ISVH5 | create | 服务商H5下单 |
| 微信支付（PayFactory）  | ISVRefund | refund | 服务商退款申请 |



