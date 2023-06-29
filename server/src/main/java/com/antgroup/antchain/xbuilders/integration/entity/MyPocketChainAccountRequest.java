package com.antgroup.antchain.xbuilders.integration.entity;

import lombok.Data;

@Data
public class MyPocketChainAccountRequest {
    /**
     * 用户did
     */
    private String did;

    /**
     * 链id
     */
    private String chainId;
}
