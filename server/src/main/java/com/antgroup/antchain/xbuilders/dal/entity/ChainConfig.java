package com.antgroup.antchain.xbuilders.dal.entity;

import lombok.Data;
import lombok.ToString;

/**
 * 链配置
 */
@ToString(callSuper = true)
@Data
public class ChainConfig extends BaseEntity {
    /**
     * 链id，baas平台bizid
     */
    private String chainId;

    /**
     * 链名称
     */
    private String chainName;

    /**
     * 链ak
     */
    private String accessKey;

    /**
     * 链sk
     */
    private String accessSecret;

    /**
     * 链账号名称
     */
    private String adminAccountName;

    /**
     * 链账号 kmsid
     */
    private String adminAccountKmsId;

    /**
     * 租户
     */
    private String tenant;


    /**
     * 区块链浏览器地址
     */
    private String chainBrowserUrl;

}
