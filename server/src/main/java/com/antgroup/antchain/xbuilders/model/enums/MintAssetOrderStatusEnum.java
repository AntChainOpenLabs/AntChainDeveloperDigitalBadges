package com.antgroup.antchain.xbuilders.model.enums;

/**
 * 资产领取状态Enum
 */
public enum MintAssetOrderStatusEnum {
    /**
     * 初始化
     */
    INIT(0),

    /**
     * 已发送
     */
    SENT(1),

    /**
     * 成功
     */
    SUCCESS(2),

    /**
     * 失败
     */
    FAILED(3),

    /**
     * 未知
     */
    UNKNOWN(999);


    private final int code;

    MintAssetOrderStatusEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static MintAssetOrderStatusEnum getValue(final Short status) {
        for (MintAssetOrderStatusEnum en : values()) {
            if (status == en.getCode()) {
                return en;
            }
        }
        return null;
    }

    public static MintAssetOrderStatusEnum nextState(MintAssetOrderStatusEnum status, boolean isSuccess) {

        switch (status) {
            case INIT:
                return SENT;
            case SENT:
                return isSuccess ? SUCCESS : FAILED;
            default:
                return UNKNOWN;
        }
    }
}
