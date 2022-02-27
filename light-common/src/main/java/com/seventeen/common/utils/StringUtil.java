package com.seventeen.common.utils;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串相关工具类
 *
 * @author seventeen
 * @date 2018/10/30
 */
public class StringUtil extends org.apache.commons.lang3.StringUtils {

    private static final char UPPER_A = 'A';
    private static final char LOWER_A = 'a';
    private static final char UPPER_Z = 'Z';
    private static final char LOWER_Z = 'z';

    private static final byte[] DIGITS = {
            '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b',
            'c', 'd', 'e', 'f', 'g', 'h',
            'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F',
            'G', 'H', 'I', 'J', 'K', 'L',
            'M', 'N', 'O', 'P', 'Q', 'R',
            'S', 'T', 'U', 'V', 'W', 'X',
            'Y', 'Z'
    };

    private static final Pattern RE_CHINESE = Pattern.compile("[\u4E00-\u9FFF]+");

    /**
     * 随机字符串因子
     */
    private static final String INT_STR = "0123456789";
    private static final String STR_STR = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String ALL_STR = INT_STR + STR_STR;

    /**
     * 首字母变小写
     *
     * @param str 字符串
     * @return {String}
     */
    public static String firstCharToLower(String str) {
        char firstChar = str.charAt(0);
        if (firstChar >= UPPER_A && firstChar <= UPPER_Z) {
            char[] arr = str.toCharArray();
            arr[0] += (LOWER_A - UPPER_A);
            return new String(arr);
        }
        return str;
    }

    /**
     * 首字母变大写
     *
     * @param str 字符串
     * @return {String}
     */
    public static String firstCharToUpper(String str) {
        char firstChar = str.charAt(0);
        if (firstChar >= LOWER_A && firstChar <= LOWER_Z) {
            char[] arr = str.toCharArray();
            arr[0] -= (LOWER_A - UPPER_A);
            return new String(arr);
        }
        return str;
    }

    /**
     * 提取中文
     *
     * @param content content
     * @return {String}
     */
    public static String getChinese(CharSequence content) {
        final Matcher matcher = RE_CHINESE.matcher(content);
        if (matcher.find()) {
            return matcher.group(0);
        }
        return null;
    }

    /**
     * 生成uuid，采用 jdk 9 的形式，优化性能
     *
     * @return UUID
     */
    public static String getUuid() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        long lsb = random.nextLong();
        long msb = random.nextLong();
        byte[] buf = new byte[32];
        formatUnsignedLong(lsb, buf, 20, 12);
        formatUnsignedLong(lsb >>> 48, buf, 16, 4);
        formatUnsignedLong(msb, buf, 12, 4);
        formatUnsignedLong(msb >>> 16, buf, 8, 4);
        formatUnsignedLong(msb >>> 32, buf, 0, 8);
        return new String(buf, StandardCharsets.UTF_8);
    }

    private static void formatUnsignedLong(long val, byte[] buf, int offset, int len) {
        int charPos = offset + len;
        int radix = 1 << 4;
        int mask = radix - 1;
        do {
            buf[--charPos] = DIGITS[((int) val) & mask];
            val >>>= 4;
        } while (charPos > offset);
    }


    /**
     * 随机数生成
     *
     * @param count 字符长度
     * @return 随机数
     */
    public static String random(int count) {
        return random(count, RandomType.ALL);
    }

    /**
     * 随机数生成
     *
     * @param count      字符长度
     * @param randomType 随机数类别
     * @return 随机数
     */
    public static String random(int count, RandomType randomType) {
        if (count == 0) {
            return "";
        }
        if (count <= 0) {
            throw new IllegalArgumentException("Requested random string length " + count + " is less than 0.");
        }
        final ThreadLocalRandom random = ThreadLocalRandom.current();
        char[] buffer = new char[count];
        for (int i = 0; i < count; i++) {
            if (RandomType.INT == randomType) {
                buffer[i] = INT_STR.charAt(random.nextInt(INT_STR.length()));
            } else if (RandomType.STRING == randomType) {
                buffer[i] = STR_STR.charAt(random.nextInt(STR_STR.length()));
            } else {
                buffer[i] = ALL_STR.charAt(random.nextInt(ALL_STR.length()));
            }
        }
        return new String(buffer);
    }

    public enum RandomType {
        /**
         * INT STRING ALL
         */
        INT, STRING, ALL
    }
}
