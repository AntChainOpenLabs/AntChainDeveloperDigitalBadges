package com.antgroup.antchain.xbuilders.model.enums;

public enum LoginTokenStatusEnum {
    IDLE(0, "未扫码"),
    SCANNED(1, "已扫码"),
    CONFIRMED(2, "已确认"),
    CANCELLED(3, "已取消"),

    UNKNOWN(999, "未知状态"),
    ;

    private int code;

    private String text;

    LoginTokenStatusEnum(int code, String text) {
        this.code = code;
        this.text = text;
    }

    public int getCode() {
        return code;
    }

    public String getText() {
        return text;
    }

    public static LoginTokenStatusEnum getStatusByCode(int code) {
        for (LoginTokenStatusEnum en : values()) {
            if (en.getCode() == code) {
                return en;
            }
        }

        return LoginTokenStatusEnum.UNKNOWN;
    }
}
