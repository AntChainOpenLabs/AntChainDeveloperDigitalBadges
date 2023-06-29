package com.antgroup.antchain.xbuilders.integration.entity;

import com.antgroup.antchain.openapi.appex.models.AccountEntry;
import com.antgroup.antchain.openapi.appex.models.CreateMypocketEscrowchainaccountResponse;
import lombok.Data;

@Data
public class MyPocketChainAccountBO {
    /**
     * 账户名称
     */
    private String accountName;

    /**
     * 账户id
     */
    private String accountId;

    public static MyPocketChainAccountBO fromOpenApiResponse(
            CreateMypocketEscrowchainaccountResponse response) {
        MyPocketChainAccountBO myPocketChainAccountBO = new MyPocketChainAccountBO();
        myPocketChainAccountBO.setAccountName(response.getAccountName());
        myPocketChainAccountBO.setAccountId(response.getAccountId());
        return myPocketChainAccountBO;
    }

    public static MyPocketChainAccountBO fromOpenApiResponse(
            AccountEntry entry) {
        MyPocketChainAccountBO myPocketChainAccountBO = new MyPocketChainAccountBO();
        myPocketChainAccountBO.setAccountName(entry.getAccountName());
        myPocketChainAccountBO.setAccountId(entry.getAccountId());
        return myPocketChainAccountBO;
    }
}
