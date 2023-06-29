package com.antgroup.antchain.xbuilders.dal.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * X-Builders Mint Order
 */
@Data
public class MintAssetOrder {

    /**
     * 主键，自增id
     */
    private Long id;

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;

    /**
     * meta信息
     */
    private String metaUri;

    /**
     * to 地址
     */
    private String toAddress;

    /**
     * 交易 hash
     */
    private String txHash;

    /**
     * 外键，资产Id
     */
    private String assetId;

    /**
     * orderId
     */
    private String orderId;

    /**
     * 交易 状态
     */
    private short status;
}
