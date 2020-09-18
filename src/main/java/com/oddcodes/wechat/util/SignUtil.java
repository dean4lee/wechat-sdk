package com.oddcodes.wechat.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

/**
 * 生成签名的工具包
 *
 * @author dean.lee
 */
public class SignUtil {

    private static Logger log = LoggerFactory.getLogger(SignUtil.class);

    /**
     * SHA256withRSA签名
     *
     * @param privateKeyStr 私钥key
     * @param signStr       签名串
     * @return Base64加密的SHA256withRSA签名
     */
    public static String sha256WithRsaSign(String privateKeyStr, String signStr) {
        byte[] decode = Base64.getDecoder().decode(privateKeyStr);
        try {
            PrivateKey privateKey = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decode));
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(privateKey);
            signature.update(signStr.getBytes(StandardCharsets.UTF_8));
            byte[] signByte = signature.sign();
            String sign = Base64.getEncoder().encodeToString(signByte);
            if (log.isDebugEnabled()) {
                log.debug("wechat-sdk >>> sha256withRsa Sign: {}", sign);
            }
            return sign;
        } catch (Exception e) {
            log.error("SHA256withRSA 签名出错", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * sha1签名
     *
     * @param signStr 签名串
     * @return sha1签名
     */
    public static String sha1Sign(String signStr) {
        MessageDigest mDigest;
        try {
            mDigest = MessageDigest.getInstance("SHA1");
        } catch (NoSuchAlgorithmException e) {
            log.error("SHA1 签名出错", e);
            throw new RuntimeException(e);
        }
        byte[] result = mDigest.digest(signStr.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (byte b : result) {
            sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        }
        String sign = sb.toString();

        if (log.isDebugEnabled()) {
            log.debug("wechat-sdk >>> sha1sign: {}", sign);
        }
        return sign;
    }
}
