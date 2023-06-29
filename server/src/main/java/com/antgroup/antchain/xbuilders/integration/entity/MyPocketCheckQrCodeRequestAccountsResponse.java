package com.antgroup.antchain.xbuilders.integration.entity;


import com.antgroup.antchain.openapi.appex.models.ChainAccountEX;
import com.antgroup.antchain.openapi.appex.models.CheckMypocketQrcoderequestaccountsResponse;
import com.antgroup.antchain.xbuilders.integration.enums.QrCodeStatusEnum;
import lombok.Data;

import java.util.List;

@Data
public class MyPocketCheckQrCodeRequestAccountsResponse {

    /**
     * 业务码
     */
    private String bizNo;


    public String outBizNo;

    public QrCodeStatusEnum status;

    public List<ChainAccountEX> accounts;

    public static MyPocketCheckQrCodeRequestAccountsResponse fromOpenApiResponse(CheckMypocketQrcoderequestaccountsResponse openApiResponse) {
        MyPocketCheckQrCodeRequestAccountsResponse result = new MyPocketCheckQrCodeRequestAccountsResponse();
        result.setBizNo(openApiResponse.getBizNo());
        result.setStatus(QrCodeStatusEnum.valueOf(openApiResponse.getStatus()));
        result.setOutBizNo(openApiResponse.getOutBizNo());
        result.setAccounts(openApiResponse.getAccounts());
        return result;
    }
}
