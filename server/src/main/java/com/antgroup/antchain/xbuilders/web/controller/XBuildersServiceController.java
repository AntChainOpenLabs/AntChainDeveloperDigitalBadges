package com.antgroup.antchain.xbuilders.web.controller;

import com.antgroup.antchain.xbuilders.integration.entity.MyPocketCheckQrCodeRequestAccountsRequest;
import com.antgroup.antchain.xbuilders.integration.entity.MyPocketCheckQrCodeRequestAccountsResponse;
import com.antgroup.antchain.xbuilders.integration.entity.MyPocketCreateQrCodeRequestAccountsRequest;
import com.antgroup.antchain.xbuilders.integration.entity.MyPocketCreateQrCodeRequestAccountsResponse;
import com.antgroup.antchain.xbuilders.service.XBuildersAppService;
import com.antgroup.antchain.xbuilders.web.controller.dto.entity.AssetMintBO;
import com.antgroup.antchain.xbuilders.web.controller.dto.entity.CreateMetadataBO;
import com.antgroup.antchain.xbuilders.web.controller.dto.request.AssetMintRequest;
import com.antgroup.antchain.xbuilders.web.controller.dto.request.CheckQrCodeRequestAccountsRequest;
import com.antgroup.antchain.xbuilders.web.controller.dto.request.CreateMetadataRequest;
import com.antgroup.antchain.xbuilders.web.controller.dto.request.CreateQrCodeRequestAccountsRequest;
import com.antgroup.antchain.xbuilders.web.controller.dto.result.AssetMintResult;
import com.antgroup.antchain.xbuilders.web.controller.dto.result.CheckRequestAccountsQrCodeDTO;
import com.antgroup.antchain.xbuilders.web.controller.dto.result.CreateMetadataResult;
import com.antgroup.antchain.xbuilders.web.controller.dto.result.CreateRequestAccountsQrCodeDTO;
import com.antgroup.antchain.xbuilders.web.controller.dto.result.base.BaseResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;


@Slf4j
@Validated
@RestController
@RequestMapping("api/xbuilders/service")
public class XBuildersServiceController {
    @Resource
    private XBuildersAppService xBuildersAppService;


    /**
     * 生态应用-生成MetaData
     */
    @PostMapping("/generateMetadata")
    public BaseResult<CreateMetadataResult> uploadAvatar(
            @RequestBody @Valid CreateMetadataRequest request
    ) {
        CreateMetadataBO createMetadataBO = xBuildersAppService.generateMetadata(request.getAvatarId());
        return BaseResult.success(CreateMetadataResult.toResult(createMetadataBO));
    }


    /**
     * 生态应用-自定义资产铸造接口
     */
    @PostMapping("/mintAsset")
    public BaseResult<AssetMintResult> assetMint(
            @RequestBody @Valid AssetMintRequest request
    ) {
        request.validateAddress();
        request.validateUri();
        AssetMintBO assetMintBO = xBuildersAppService.assertMint(request.getAssetId(), request.getAddress(), request.getUri());
        return BaseResult.success(AssetMintResult.toResult(assetMintBO));
    }


    /**
     * 生态应用-快速铸造接口
     */
    @PostMapping("/mintSimpleAsset")
    public BaseResult<AssetMintResult> simpleAssetMint(
            @RequestBody @Valid AssetMintRequest request
    ) {
        AssetMintBO assetMintBO = xBuildersAppService.assertMint(request.getAssetId(), request.getAddress(), "");
        return BaseResult.success(AssetMintResult.toResult(assetMintBO));
    }

    /**
     * 创建`获取账户地址`小程序码
     */
    @PostMapping("/createRequestAccountsQrCode")
    public BaseResult<CreateRequestAccountsQrCodeDTO> createRequestAccountsQrCode(
            @RequestBody @Valid CreateQrCodeRequestAccountsRequest request
    ) {
        String outBizNo = RandomStringUtils.randomAlphanumeric(16);
        MyPocketCreateQrCodeRequestAccountsRequest requestAccounts = new MyPocketCreateQrCodeRequestAccountsRequest();
        requestAccounts.setOutBizNo(outBizNo);
        requestAccounts.setAccountType(request.getAccountType());
        requestAccounts.setChainId(request.getChainId());

        MyPocketCreateQrCodeRequestAccountsResponse createQrCodeResponse = xBuildersAppService.createQrCodeRequestAccounts(requestAccounts);
        CreateRequestAccountsQrCodeDTO qrCodeResult = new CreateRequestAccountsQrCodeDTO();
        qrCodeResult.setBizNo(createQrCodeResponse.getBizNo());
        qrCodeResult.setQrCodeUrl(createQrCodeResponse.getQrCodeUrl());
        return BaseResult.success(qrCodeResult);
    }

    /**
     * 查询`获取账户地址`小程序码的结果;
     */
    @PostMapping("/checkRequestAccountsQrCode")
    public BaseResult<CheckRequestAccountsQrCodeDTO> checkRequestAccountsQrCode(
            @RequestBody @Valid CheckQrCodeRequestAccountsRequest request
    ) {
        MyPocketCheckQrCodeRequestAccountsRequest checkQrCodeRequest = new MyPocketCheckQrCodeRequestAccountsRequest();
        checkQrCodeRequest.setBizNo(request.getBizNo());

        MyPocketCheckQrCodeRequestAccountsResponse checkQrCodeResponse = xBuildersAppService.checkQrCodeRequestAccounts(checkQrCodeRequest);
        CheckRequestAccountsQrCodeDTO qrCodeResult = new CheckRequestAccountsQrCodeDTO();
        qrCodeResult.setBizNo(checkQrCodeResponse.getBizNo());
        qrCodeResult.setStatus(checkQrCodeResponse.getStatus());
        qrCodeResult.setAccounts(checkQrCodeResponse.getAccounts());
        return BaseResult.success(qrCodeResult);
    }


}
