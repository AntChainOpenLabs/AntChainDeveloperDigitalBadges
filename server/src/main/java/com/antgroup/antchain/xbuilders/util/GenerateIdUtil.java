package com.antgroup.antchain.xbuilders.util;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.UUID;

/**
 * 随机id生成器
 */
public class GenerateIdUtil {

    private static final String TIME_FMT = "yyyyMMddHHmmssSSS";

    private static final int DEFAULT_RANDOM_COUNT = 8;

    private static final int AK_RANDOM_LENGTH = 8;

    /**
     * 生成默认8位随机数结尾的时间序列随机id
     *
     * @return
     */
    public static String generateId() {
        return generateId(DEFAULT_RANDOM_COUNT);
    }

    /**
     * 生成时间序列随机id
     *
     * @param randomCount
     * @return
     */
    public static String generateId(int randomCount) {
        StringBuilder id = new StringBuilder();
        id.append(DateUtil.formatNow(TIME_FMT));
        id.append(RandomStringUtils.randomNumeric(randomCount));
        return id.toString();
    }

    /**
     * 生成uuid
     *
     * @return
     */
    public static String generateUuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }


}
