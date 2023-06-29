package com.antgroup.antchain.xbuilders.integration.enums;

/**
 * 库存状态
 */
public enum StockStatusEnum {
    /**
     * 可兑换
     */
    AVAILABLE(0),

    /**
     * 已锁定
     */
    LOCKED(1),

    /**
     * 已兑换
     */
    REDEEMED(2),
    ;

    /**
     * 状态码
     */
    private final int statusCode;


    StockStatusEnum(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public static StockStatusEnum getByCode(Short statusCode) {
        for (StockStatusEnum en : values()) {
            if (statusCode != null && en.statusCode == statusCode) {
                return en;
            }
        }

        return null;
    }
}
