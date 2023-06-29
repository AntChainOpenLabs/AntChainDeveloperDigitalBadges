package com.antgroup.antchain.xbuilders.integration;

import com.antfinancial.mychain.baas.tool.restclient.RestClient;
import com.antgroup.antchain.xbuilders.integration.entity.BaaSRestCallContractBO;
import com.antgroup.antchain.xbuilders.integration.entity.BaasRestQueryReceiptRequest;
import com.antgroup.antchain.xbuilders.integration.entity.TransactionReceiptBO;

import javax.annotation.Nonnull;

public interface BaasRestService {
    @Nonnull
    RestClient getRestClient(String bizid);

    abstract TransactionReceiptBO callContract(BaaSRestCallContractBO callContractBO);

    abstract String sendTransaction(BaaSRestCallContractBO callContractBO);

    abstract TransactionReceiptBO queryReceipt(BaasRestQueryReceiptRequest baasRestQueryReceiptRequestO);

}
