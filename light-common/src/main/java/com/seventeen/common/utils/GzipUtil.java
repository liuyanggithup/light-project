package com.seventeen.common.utils;

import com.seventeen.common.exception.ErrorException;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * gzip 文本压缩工具
 *
 * @author seventeen
 */
@Slf4j
public class GzipUtil {
    /**
     * 压缩文本
     *
     * @param str
     * @return 压缩后的内容
     */
    public static String compress(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        ByteArrayOutputStream out = null;
        GZIPOutputStream gzip;
        String compress;
        try {
            out = new ByteArrayOutputStream();
            gzip = new GZIPOutputStream(out);
            gzip.write(str.getBytes());
            gzip.close();
            // 这里增加base64编码
            byte[] compressed = out.toByteArray();
            compress = Base64Util.encodeToString(compressed);
        } catch (IOException e) {
            log.error("", e);
            throw new ErrorException("gzip解压缩异常");
        } finally {
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    log.error("", e);
                }
            }
        }
        return compress;
    }

    /**
     * @param str
     * @return 解压缩
     */
    public static String uncompress(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        ByteArrayOutputStream out = null;
        ByteArrayInputStream in = null;
        GZIPInputStream gzip = null;
        String uncompress = "";
        try {
            out = new ByteArrayOutputStream();
            // 这里增加base64解码
            byte[] compressed = Base64Util.decodeFromString(str);
            in = new ByteArrayInputStream(compressed);
            gzip = new GZIPInputStream(in);
            byte[] buffer = new byte[1024];
            int offset;
            while ((offset = gzip.read(buffer)) != -1) {
                out.write(buffer, 0, offset);
            }
            uncompress = out.toString();
        } catch (IOException e) {
            log.error("", e);
            throw new ErrorException("gzip解压缩异常");
        } finally {
            if (null != gzip) {
                try {
                    gzip.close();
                } catch (IOException e) {
                    log.error("", e);
                }
            }
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    log.error("", e);
                }
            }
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    log.error("", e);
                }
            }
        }
        return uncompress;
    }

}