package com.antgroup.antchain.xbuilders.integration.impl;


import com.antgroup.antchain.openapi.appex.Client;
import com.antgroup.antchain.openapi.appex.models.*;
import com.antgroup.antchain.xbuilders.integration.MyPocketService;
import com.antgroup.antchain.xbuilders.integration.entity.*;
import com.antgroup.antchain.xbuilders.integration.enums.QrCodeSizeEnum;
import com.antgroup.antchain.xbuilders.model.exception.BusinessException;
import com.antgroup.antchain.xbuilders.model.exception.ErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import javax.annotation.Resource;

@Slf4j
@Service
public class MyPocketServiceImpl implements MyPocketService {

    @Resource
    private Client openApiClient;

    @Value("${antchain.openapi.mypocket-instance-id}")
    private String mypocketInstanceId;

    @Value("${spring.profiles.active}")
    private String profile;

    @Override
    public MyPocketGetChainAccountResponseBO getUserChainAccountByDid(MyPocketChainAccountRequestBO request) {
        QueryMypocketEscrowchainaccountRequest openApiRequest = new QueryMypocketEscrowchainaccountRequest();
        openApiRequest.setDid(request.getDid());
        openApiRequest.setChainId(request.getChainId());
        if (StringUtils.isNotEmpty(mypocketInstanceId)) {
            openApiRequest.setProductInstanceId(mypocketInstanceId);
        }

        try {
            QueryMypocketEscrowchainaccountResponse openApiResponse
                    = openApiClient.queryMypocketEscrowchainaccount(openApiRequest);
            if (!"OK".equalsIgnoreCase(openApiResponse.getResultCode())) {
                log.error("failed to call mypocket, response: {}", openApiResponse);
                throw new BusinessException(ErrorCodeEnum.MYPOCKET_SERVICE_ERROR,
                        "query user chain account fail");
            }

            return MyPocketGetChainAccountResponseBO.fromOpenApiResponse(openApiResponse);
        } catch (Exception e) {
            throw new BusinessException(ErrorCodeEnum.MYPOCKET_SERVICE_ERROR, e);
        }
    }

    @Override
    public MyPocketChainAccountBO createChainAccountForDid(MyPocketChainAccountRequestBO request) {
        CreateMypocketEscrowchainaccountRequest openApiRequest = new CreateMypocketEscrowchainaccountRequest();
        openApiRequest.setDid(request.getDid());
        openApiRequest.setChainId(request.getChainId());
        if (StringUtils.isNotEmpty(mypocketInstanceId)) {
            openApiRequest.setProductInstanceId(mypocketInstanceId);
        }

        try {
            CreateMypocketEscrowchainaccountResponse openApiResponse
                    = openApiClient.createMypocketEscrowchainaccount(openApiRequest);
            if (!"OK".equalsIgnoreCase(openApiResponse.getResultCode())) {
                log.error("failed to call mypocket, response: {}", openApiResponse);
                throw new BusinessException(ErrorCodeEnum.MYPOCKET_SERVICE_ERROR,
                        "create chain account fail");
            }

            return MyPocketChainAccountBO.fromOpenApiResponse(openApiResponse);
        } catch (Exception e) {
            throw new BusinessException(ErrorCodeEnum.MYPOCKET_SERVICE_ERROR, e);
        }
    }

    @Override
    public Boolean verifyAppDidSign(MyPocketSignVerifyRequestBO request) {
        if ("sit".equals(profile)) {
            log.info("active profile {}, ignore verifyAppDidSign", profile);
            return true;
        }
        StartMypocketAppdidsignverifyRequest openApiRequest = new StartMypocketAppdidsignverifyRequest();
        openApiRequest.setDidSign(request.getSign());
        openApiRequest.setPayload(request.getPayload());
        openApiRequest.setDappId(request.getDappId());
        openApiRequest.setTimestamp(request.getTimestamp());
        if (StringUtils.isNotEmpty(mypocketInstanceId)) {
            openApiRequest.setProductInstanceId(mypocketInstanceId);
        }

        try {
            StartMypocketAppdidsignverifyResponse openApiResponse
                    = openApiClient.startMypocketAppdidsignverify(openApiRequest);
            return "OK".equalsIgnoreCase(openApiResponse.getResultCode());
        } catch (Exception e) {
            throw new BusinessException(ErrorCodeEnum.MYPOCKET_SERVICE_ERROR, e);
        }
    }

    @Override
    public MyPocketQueryUserInfoResponseBO getUserInfo(MyPocketQueryUserInfoRequestBO request) {
        QueryMypocketUserinfoRequest openApiRequest = new QueryMypocketUserinfoRequest();
        openApiRequest.setAlipayUid(request.getAlipayUid());
        if (StringUtils.isNotEmpty(mypocketInstanceId)) {
            openApiRequest.setProductInstanceId(mypocketInstanceId);
        }

        try {
            QueryMypocketUserinfoResponse openApiResponse
                    = openApiClient.queryMypocketUserinfo(openApiRequest);
            if (!"OK".equalsIgnoreCase(openApiResponse.getResultCode())) {
                log.error("failed to call mypocket, response: {}", openApiResponse);
                throw new BusinessException(ErrorCodeEnum.MYPOCKET_SERVICE_ERROR,
                        "create chain account fail");
            }

            return MyPocketQueryUserInfoResponseBO.fromOpenApiResponse(openApiResponse);
        } catch (Exception e) {
            throw new BusinessException(ErrorCodeEnum.MYPOCKET_SERVICE_ERROR, e);
        }
    }

    @Nonnull
    @Override
    public MyPocketCreateQrCodeRequestAccountsResponse createQrCodeRequestAccounts(MyPocketCreateQrCodeRequestAccountsRequest request) {
        CreateMypocketQrcoderequestaccountsRequest openApiRequest = new CreateMypocketQrcoderequestaccountsRequest();
        openApiRequest.setOutBizNo(request.getOutBizNo());
        openApiRequest.setChainId(request.getChainId());
        openApiRequest.setAccountType(request.getAccountType());
        openApiRequest.setAppId(request.getDappId());
        openApiRequest.setSize(QrCodeSizeEnum.SMALL.toString());
        if (StringUtils.isNotEmpty(mypocketInstanceId)) {
            openApiRequest.setProductInstanceId(mypocketInstanceId);
        }

        CreateMypocketQrcoderequestaccountsResponse openApiResponse;
        try {
            openApiResponse = openApiClient.createMypocketQrcoderequestaccounts(openApiRequest);
        } catch (Exception e) {
            throw new BusinessException(ErrorCodeEnum.MYPOCKET_SERVICE_ERROR, e);
        }

        log.info("mypocket,createQrCodeRequestAccounts,response,{},{},{}", openApiResponse.getResultCode(), openApiResponse.getBizNo(), openApiResponse.getQrCodeUrl());
        if (!"OK".equalsIgnoreCase(openApiResponse.getResultCode())) {
            log.error("failed to call mypocket, response: {}", openApiResponse);
            throw new BusinessException(ErrorCodeEnum.MYPOCKET_SERVICE_ERROR, openApiResponse.getResultMsg());
        } else if ("NO_PERMISSION".equalsIgnoreCase(openApiResponse.getResultCode())) {
            throw new BusinessException(ErrorCodeEnum.NO_PERMISSION);
        }
        return MyPocketCreateQrCodeRequestAccountsResponse.fromOpenApiResponse(openApiResponse);
    }


    @Nonnull
    @Override
    public MyPocketCheckQrCodeRequestAccountsResponse checkQrCodeRequestAccounts(MyPocketCheckQrCodeRequestAccountsRequest request) {

        CheckMypocketQrcoderequestaccountsRequest openApiRequest = new CheckMypocketQrcoderequestaccountsRequest();
        openApiRequest.setBizNo(request.getBizNo());
        if (StringUtils.isNotEmpty(mypocketInstanceId)) {
            openApiRequest.setProductInstanceId(mypocketInstanceId);
        }

        CheckMypocketQrcoderequestaccountsResponse openApiResponse;
        try {
            openApiResponse = openApiClient.checkMypocketQrcoderequestaccounts(openApiRequest);
        } catch (Exception e) {
            throw new BusinessException(ErrorCodeEnum.MYPOCKET_SERVICE_ERROR, e);
        }

        log.info("mypocket,checkMypocketQrcoderequestaccounts,response,{},{},{}", openApiResponse.getResultCode(), openApiResponse.getBizNo(), openApiResponse.getStatus());
        if ("OK".equalsIgnoreCase(openApiResponse.getResultCode())) {
            return MyPocketCheckQrCodeRequestAccountsResponse.fromOpenApiResponse(openApiResponse);
        } else if ("INVALID_QRCODE".equalsIgnoreCase(openApiResponse.getResultCode())) {
            throw new BusinessException(ErrorCodeEnum.QR_CODE_NOT_EFFECTIVE);
        } else if ("NO_PERMISSION".equalsIgnoreCase(openApiResponse.getResultCode())) {
            throw new BusinessException(ErrorCodeEnum.NO_PERMISSION);
        } else {
            throw new BusinessException(ErrorCodeEnum.MYPOCKET_SERVICE_ERROR, openApiResponse.getResultMsg());
        }
    }
}
