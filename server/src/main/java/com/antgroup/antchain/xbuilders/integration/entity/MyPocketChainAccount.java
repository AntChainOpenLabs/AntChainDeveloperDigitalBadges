package com.antgroup.antchain.xbuilders.integration.entity;

import com.antgroup.antchain.openapi.appex.models.AccountEntry;
import com.antgroup.antchain.openapi.appex.models.CreateMypocketEscrowchainaccountResponse;
import lombok.Data;

@Data
public class MyPocketChainAccount {
    /**
     * 账户名称
     */
    private String accountName;

    /**
     * 账户id
     */
    private String accountId;

    public static MyPocketChainAccount fromOpenApiResponse(
            CreateMypocketEscrowchainaccountResponse response) {
        MyPocketChainAccount myPocketChainAccount = new MyPocketChainAccount();
        myPocketChainAccount.setAccountName(response.getAccountName());
        myPocketChainAccount.setAccountId(response.getAccountId());
        return myPocketChainAccount;
    }

    public static MyPocketChainAccount fromOpenApiResponse(
            AccountEntry entry) {
        MyPocketChainAccount myPocketChainAccount = new MyPocketChainAccount();
        myPocketChainAccount.setAccountName(entry.getAccountName());
        myPocketChainAccount.setAccountId(entry.getAccountId());
        return myPocketChainAccount;
    }
}
