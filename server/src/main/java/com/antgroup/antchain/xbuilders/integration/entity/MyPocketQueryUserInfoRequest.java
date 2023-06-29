package com.antgroup.antchain.xbuilders.integration.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class MyPocketQueryUserInfoRequest {
    /**
     * 支付宝uid
     */
    @NotNull
    private String alipayUid;
}