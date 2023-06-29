package com.antgroup.antchain.xbuilders.integration.entity;

import lombok.Data;

@Data
public class MyPocketCreateQrCodeRequestAccountsRequest {

    /**
     * 外部业务号
     */
    private String outBizNo;

    /**
     * 链ID
     */
    private String chainId;

    /**
     * 账户类型 ETH | MYCHAIN
     */
    private String accountType;

    /**
     * 应用ID
     */
    private String dappId;

}
