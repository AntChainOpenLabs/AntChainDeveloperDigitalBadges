package com.antgroup.antchain.xbuilders.model.enums;

/**
 * 资产领取状态Enum
 */
public enum AssetStatusEnum {
    /**
     * 无资格领取
     */
    NOT_APPLICABLE(0),

    /**
     * 有资格未领取
     */
    APPLICABLE(1),


    /**
     * 已领取
     */
    APPLIED(2),

    /**
     * 无账户
     */
    NO_ACCOUNT(3);


    /**
     * 资产领取状态
     */
    private final int code;

    AssetStatusEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static AssetStatusEnum getValue(final Short status) {
        for (AssetStatusEnum en : values()) {
            if (status == en.getCode()) {
                return en;
            }
        }

        return null;
    }
}
