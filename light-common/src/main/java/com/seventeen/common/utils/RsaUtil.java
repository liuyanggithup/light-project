package com.seventeen.common.utils;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @author seventeen
 * @date 2019-07-25
 */
public class RsaUtil {

    private static final String RSA = "RSA";

    /**
     * RSA加密
     *
     * @param msg       需要加密的内容
     * @param publicKey 公钥
     * @return 加密结果
     */
    public static String encrypt(String msg, String publicKey) throws Exception {
        byte[] decoded = Base64.getDecoder().decode(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory
                .getInstance(RSA).generatePublic(new X509EncodedKeySpec(decoded));
        //RSA加密
        Cipher cipher = Cipher.getInstance(RSA);
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        byte[] original = cipher.doFinal(msg.getBytes(StandardCharsets.UTF_8));

        return Base64.getEncoder().encodeToString(original);
    }

    /**
     * RSA解密
     *
     * @param msg        密文
     * @param privateKey 私钥
     */
    public static String decrypt(String msg, String privateKey) throws Exception {
        // 64位解码加密后的字符串
        byte[] inputByte = Base64.getDecoder().decode(msg);
        byte[] decoded = Base64.getDecoder().decode(privateKey);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory
                .getInstance(RSA).generatePrivate(new PKCS8EncodedKeySpec(decoded));
        //RSA解密
        Cipher cipher = Cipher.getInstance(RSA);
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        byte[] original = cipher.doFinal(inputByte);

        return new String(original, StandardCharsets.UTF_8);
    }

}
