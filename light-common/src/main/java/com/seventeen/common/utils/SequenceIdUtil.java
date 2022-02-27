package com.seventeen.common.utils;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author seventeen
 */
public class SequenceIdUtil {

    public static String nextId() {
        return DigestUtil.md5Hex(StringUtil.getUuid() + getRandomNumber() + getRandomNumber());
    }


    private static int getRandomNumber() {
        return ThreadLocalRandom.current().nextInt(10000, 99999);
    }

}
