package com.seventeen.common.utils;

import com.seventeen.common.exception.ErrorException;
import com.seventeen.common.exception.WarnException;
import org.springframework.util.DigestUtils;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * md5 sha 加密
 *
 * @author seventeen
 */
public class DigestUtil {
    private static final String HEX_VALUE = "0123456789abcdef";
    private static final char[] HEX_CODE = HEX_VALUE.toCharArray();

    public static byte[] md5(final String data) {
        return DigestUtils.md5Digest(data.getBytes(StandardCharsets.UTF_8));
    }

    public static String md5Hex(final String data) {
        return DigestUtils.md5DigestAsHex(data.getBytes(StandardCharsets.UTF_8));
    }

    public static byte[] sha1(String data) {
        return DigestUtil.sha1(data.getBytes(StandardCharsets.UTF_8));
    }

    public static byte[] sha1(final byte[] bytes) {
        return DigestUtil.digest("SHA-1", bytes);
    }

    public static String sha1Hex(String data) {
        return DigestUtil.encodeHex(sha1(data.getBytes(StandardCharsets.UTF_8)));
    }

    public static byte[] sha224(String data) {
        return DigestUtil.sha224(data.getBytes(StandardCharsets.UTF_8));
    }

    public static byte[] sha224(final byte[] bytes) {
        return DigestUtil.digest("SHA-224", bytes);
    }

    public static String sha224Hex(String data) {
        return DigestUtil.encodeHex(sha224(data.getBytes(StandardCharsets.UTF_8)));
    }

    public static byte[] sha256(String data) {
        return DigestUtil.sha256(data.getBytes(StandardCharsets.UTF_8));
    }

    public static byte[] sha256(final byte[] bytes) {
        return DigestUtil.digest("SHA-256", bytes);
    }

    public static String sha256Hex(String data) {
        return DigestUtil.encodeHex(sha256(data.getBytes(StandardCharsets.UTF_8)));
    }

    public static String sha256Hex(final byte[] bytes) {
        return DigestUtil.encodeHex(sha256(bytes));
    }

    private static byte[] digest(String algorithm, byte[] bytes) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            return md.digest(bytes);
        } catch (NoSuchAlgorithmException e) {
            throw new WarnException("加密异常");
        }
    }


    public static byte[] hmacMd5(String data, String key) {
        return DigestUtil.hmacMd5(data.getBytes(StandardCharsets.UTF_8), key);
    }

    public static byte[] hmacMd5(final byte[] bytes, String key) {
        return DigestUtil.digestHmac("HmacMD5", bytes, key);
    }

    public static String hmacMd5Hex(String data, String key) {
        return DigestUtil.encodeHex(hmacMd5(data.getBytes(StandardCharsets.UTF_8), key));
    }

    public static String hmacMd5Hex(final byte[] bytes, String key) {
        return DigestUtil.encodeHex(hmacMd5(bytes, key));
    }


    public static byte[] hmacSha1(String data, String key) {
        return DigestUtil.hmacSha1(data.getBytes(StandardCharsets.UTF_8), key);
    }

    public static byte[] hmacSha1(final byte[] bytes, String key) {
        return DigestUtil.digestHmac("HmacSHA1", bytes, key);
    }

    public static String hmacSha1Hex(String data, String key) {
        return DigestUtil.encodeHex(hmacSha1(data.getBytes(StandardCharsets.UTF_8), key));
    }

    public static String hmacSha1Hex(final byte[] bytes, String key) {
        return DigestUtil.encodeHex(hmacSha1(bytes, key));
    }


    public static byte[] hmacSha224(String data, String key) {
        return DigestUtil.hmacSha224(data.getBytes(StandardCharsets.UTF_8), key);
    }


    public static byte[] hmacSha224(final byte[] bytes, String key) {
        return DigestUtil.digestHmac("HmacSHA224", bytes, key);
    }


    public static String hmacSha224Hex(String data, String key) {
        return DigestUtil.encodeHex(hmacSha224(data.getBytes(StandardCharsets.UTF_8), key));
    }


    public static String hmacSha224Hex(final byte[] bytes, String key) {
        return DigestUtil.encodeHex(hmacSha224(bytes, key));
    }

    public static byte[] hmacSha256(String data, String key) {
        return DigestUtil.hmacSha256(data.getBytes(StandardCharsets.UTF_8), key);
    }

    public static byte[] hmacSha256(final byte[] bytes, String key) {
        return DigestUtil.digestHmac("HmacSHA256", bytes, key);
    }

    public static String hmacSha256Hex(String data, String key) {
        return DigestUtil.encodeHex(hmacSha256(data.getBytes(StandardCharsets.UTF_8), key));
    }

    public static String hmacSha256Hex(final byte[] bytes, String key) {
        return DigestUtil.encodeHex(hmacSha256(bytes, key));
    }

    /**
     * digest Hmac
     *
     * @param algorithm 算法
     * @param bytes     Data to digest
     * @return digest as a byte array
     */
    private static byte[] digestHmac(String algorithm, final byte[] bytes, String key) {
        SecretKey secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), algorithm);
        try {
            Mac mac = Mac.getInstance(secretKey.getAlgorithm());
            mac.init(secretKey);
            return mac.doFinal(bytes);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new ErrorException("Hmac 加密失败");
        }
    }

    /**
     * encode Hex
     *
     * @param bytes Data to Hex
     * @return bytes as a hex string
     */
    public static String encodeHex(byte[] bytes) {
        StringBuilder r = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            r.append(HEX_CODE[(b >> 4) & 0xF]);
            r.append(HEX_CODE[(b & 0xF)]);
        }
        return r.toString();
    }

    /**
     * decode Hex
     *
     * @param hexStr Hex string
     * @return decode hex to bytes
     */
    @SuppressWarnings("all")
    public static byte[] decodeHex(final String hexStr) {
        int len = hexStr.length();
        if ((len & 0x01) != 0) {
            throw new IllegalArgumentException("hexBinary needs to be even-length: " + hexStr);
        }
        String hexText = hexStr.toLowerCase();
        byte[] out = new byte[len >> 1];
        for (int i = 0; i < len; i += 2) {
            int hn = HEX_VALUE.indexOf(hexText.charAt(i));
            int ln = HEX_VALUE.indexOf(hexText.charAt(i + 1));
            if (hn == -1 || ln == -1) {
                throw new IllegalArgumentException("contains illegal character for hexBinary: " + hexStr);
            }
            out[i / 2] = (byte) ((hn << 4) | ln);
        }
        return out;
    }

}