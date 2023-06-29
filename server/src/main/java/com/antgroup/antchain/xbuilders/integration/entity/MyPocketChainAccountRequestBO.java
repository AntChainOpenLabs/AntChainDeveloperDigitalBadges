package com.antgroup.antchain.xbuilders.integration.entity;

import lombok.Data;

@Data
public class MyPocketChainAccountRequestBO {
    /**
     * 用户did
     */
    private String did;

    /**
     * 链id
     */
    private String chainId;
}
