package com.antgroup.antchain.xbuilders.integration.entity;

import lombok.Data;

@Data
public class BaasRestQueryReceiptRequest {

    /**
     * 链id
     */
    private String chainId;

    /**
     * 交易Hash
     */
    private String txHash;
}
