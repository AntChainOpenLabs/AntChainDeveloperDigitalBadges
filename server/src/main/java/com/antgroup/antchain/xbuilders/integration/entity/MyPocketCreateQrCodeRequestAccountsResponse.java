package com.antgroup.antchain.xbuilders.integration.entity;

import com.antgroup.antchain.openapi.appex.models.CreateMypocketQrcoderequestaccountsResponse;
import lombok.Data;

@Data
public class MyPocketCreateQrCodeRequestAccountsResponse {

    /**
     * 业务码
     */
    private String bizNo;

    /**
     * 外部业务号
     */
    private String outBizNo;

    /**
     * 二维码地址
     */
    private String qrCodeUrl;

    public static MyPocketCreateQrCodeRequestAccountsResponse fromOpenApiResponse(CreateMypocketQrcoderequestaccountsResponse openApiResponse) {
        MyPocketCreateQrCodeRequestAccountsResponse result = new MyPocketCreateQrCodeRequestAccountsResponse();
        result.setBizNo(openApiResponse.getBizNo());
        result.setOutBizNo(openApiResponse.getOutBizNo());
        result.setQrCodeUrl(openApiResponse.getQrCodeUrl());
        return result;
    }
}
