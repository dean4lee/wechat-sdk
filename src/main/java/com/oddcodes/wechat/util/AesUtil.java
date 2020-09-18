package com.oddcodes.wechat.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Aes解密算法，解密微信支付异步回调的数据
 *
 * @author dean.lee
 */
public class AesUtil {

    private Logger log = LoggerFactory.getLogger(AesUtil.class);

    static final int KEY_LENGTH_BYTE = 32;
    static final int TAG_LENGTH_BIT = 128;
    private final byte[] aesKey;

    public AesUtil(String v3Key) {
        byte[] key = v3Key.getBytes();
        if (key.length != KEY_LENGTH_BYTE) {
            throw new IllegalArgumentException("无效的ApiV3Key，长度必须为32个字节");
        }
        this.aesKey = key;
    }

    public String decryptToString(String associatedData, String nonce, String ciphertext) {
        try {
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");

            SecretKeySpec key = new SecretKeySpec(aesKey, "AES");
            GCMParameterSpec spec = new GCMParameterSpec(TAG_LENGTH_BIT, nonce.getBytes());

            cipher.init(Cipher.DECRYPT_MODE, key, spec);
            cipher.updateAAD(associatedData.getBytes());

            String decryptString = new String(cipher.doFinal(Base64.getDecoder().decode(ciphertext)), StandardCharsets.UTF_8);
            if (log.isDebugEnabled()) {
                log.debug("wechat-sdk >>> AES解密数据: {}", decryptString);
            }
            return decryptString;
        } catch (Exception e) {
            log.error("AES解密出错", e);
            throw new IllegalStateException(e);
        }
    }
}
