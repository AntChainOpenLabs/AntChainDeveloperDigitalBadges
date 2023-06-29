package com.antgroup.antchain.xbuilders.integration.entity;

import com.antgroup.antchain.openapi.appex.models.QueryMypocketEscrowchainaccountResponse;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class MyPocketGetChainAccountResponse {
    /**
     * 用户在指定链上的所有链上账户
     */
    private List<MyPocketChainAccount> chainAccounts;

    public static MyPocketGetChainAccountResponse fromOpenApiResponse(
            QueryMypocketEscrowchainaccountResponse response) {
        MyPocketGetChainAccountResponse bo = new MyPocketGetChainAccountResponse();
        bo.setChainAccounts(response.getChainAccounts() == null
                ? new ArrayList<>()
                : response.getChainAccounts()
                .stream()
                .map(MyPocketChainAccount::fromOpenApiResponse)
                .collect(Collectors.toList()));
        return bo;
    }
}
