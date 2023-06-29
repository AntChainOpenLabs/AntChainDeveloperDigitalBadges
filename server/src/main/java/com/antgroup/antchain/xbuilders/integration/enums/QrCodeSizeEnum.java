package com.antgroup.antchain.xbuilders.integration.enums;

/**
 * 二维码尺寸
 *
 * @author 炎雨
 */
public enum QrCodeSizeEnum {

    /**
     * 8厘米
     */
    SMALL("s"),

    /**
     * 12厘米
     */
    MEDIUM("m"),

    /**
     * 30厘米
     */
    LARGE("l"),
    ;

    private final String size;

    QrCodeSizeEnum(String size) {
        this.size = size;
    }

    public String getSize() {
        return size;
    }
}
