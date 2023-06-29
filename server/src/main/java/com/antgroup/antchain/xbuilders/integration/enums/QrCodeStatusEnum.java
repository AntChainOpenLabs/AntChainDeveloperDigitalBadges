package com.antgroup.antchain.xbuilders.integration.enums;

/**
 * 小程序码扫码状态
 */
public enum QrCodeStatusEnum {
    IDLE(0, "未扫码"),
    SCANNED(1, "已扫码"),
    CONFIRMED(2, "已确认"),
    CANCELLED(3, "已取消"),

    UNKNOWN(999, "未知状态"),
    ;

    private int code;

    private String text;

    QrCodeStatusEnum(int code, String text) {
        this.code = code;
        this.text = text;
    }

    public int getCode() {
        return code;
    }

    public String getText() {
        return text;
    }

    public static QrCodeStatusEnum getStatusByCode(int code) {
        for (QrCodeStatusEnum en : values()) {
            if (en.getCode() == code) {
                return en;
            }
        }

        return QrCodeStatusEnum.UNKNOWN;
    }
}
