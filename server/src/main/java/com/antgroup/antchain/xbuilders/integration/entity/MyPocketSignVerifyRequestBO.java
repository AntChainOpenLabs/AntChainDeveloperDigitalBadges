package com.antgroup.antchain.xbuilders.integration.entity;

import lombok.Data;

@Data
public class MyPocketSignVerifyRequestBO {
    /**
     * 签名payload，必须为原数据的sha256 hash
     */
    private String payload;

    /**
     * 时间戳，epoch milli
     */
    private Long timestamp;

    /**
     * 签名
     */
    private String sign;

    /**
     * 验签的dappId
     */
    private String dappId;
}
