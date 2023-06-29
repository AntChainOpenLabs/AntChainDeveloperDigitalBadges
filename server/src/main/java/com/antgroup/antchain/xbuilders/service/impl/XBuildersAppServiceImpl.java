package com.antgroup.antchain.xbuilders.service.impl;

import com.alibaba.fastjson.JSON;
import com.alipay.mychain.sdk.domain.account.Identity;
import com.antgroup.antchain.xbuilders.dal.entity.MintAssetOrder;
import com.antgroup.antchain.xbuilders.dal.mapper.AssetMapper;
import com.antgroup.antchain.xbuilders.dal.mapper.MintXBuildersAssetMapper;
import com.antgroup.antchain.xbuilders.integration.BaasRestService;
import com.antgroup.antchain.xbuilders.integration.MyPocketService;
import com.antgroup.antchain.xbuilders.integration.entity.*;
import com.antgroup.antchain.xbuilders.integration.enums.ContractTypeEnum;
import com.antgroup.antchain.xbuilders.model.entity.Asset;
import com.antgroup.antchain.xbuilders.model.entity.AssetUri;
import com.antgroup.antchain.xbuilders.model.enums.MintAssetOrderStatusEnum;
import com.antgroup.antchain.xbuilders.model.exception.BusinessException;
import com.antgroup.antchain.xbuilders.model.exception.ErrorCodeEnum;
import com.antgroup.antchain.xbuilders.service.OSSService;
import com.antgroup.antchain.xbuilders.service.XBuildersAppService;
import com.antgroup.antchain.xbuilders.util.GenerateIdUtil;
import com.antgroup.antchain.xbuilders.web.controller.dto.entity.AssetMintBO;
import com.antgroup.antchain.xbuilders.web.controller.dto.entity.CreateMetadataBO;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Objects;

import static com.antgroup.antchain.xbuilders.model.exception.ErrorCodeEnum.*;


@Slf4j
@Service
public class XBuildersAppServiceImpl implements XBuildersAppService {

    @Value("${xbuilders.oss.file.uri}")
    private String ossUri;

    @Value("${xbuilders.resources.path}")
    private String avatarResourcePath;

    @Value("${xbuilders.resources.extName}")
    private String avatarExtname;

    @Value("${xbuilders.resources.cap}")
    private long avatarCap;

    private static final String XBUILDERS_ASSET_OSS_STORAGE_PATH = "x-builders/avatars";

    private static final int MYCHAIN_ADDRESS_LENGTH = 64;

    private static final String XBUILDERS_DESCRIPTION = "X-Builders NFT 是 Web3 官网体验者的数字礼物，仅作为AIGC文本创作体验，一键铸造上链，帮助实现数字资产收藏体验。";

    private static final String XBUILDERS_NAME = "X-Builders 数字创作Gifts #";

    @Resource
    private OSSService ossService;

    @Autowired
    private MintXBuildersAssetMapper mintXBuildersAssetMapper;

    @Resource
    private AssetMapper assetMapper;

    @Resource
    private BaasRestService baaSRestService;

    @Resource
    private MyPocketService myPocketService;

    @Value("${xbuilders.mypocket.appid}")
    private String appIdOfMypocket;


    @Autowired
    ResourceLoader resourceLoader;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public CreateMetadataBO generateMetadata(String avatarId) {
        CreateMetadataBO createMetadataBO = new CreateMetadataBO();

        //check if avatar exist
        boolean avatarExists = checkResource(avatarId);
        if (!avatarExists) {
            throw new BusinessException(AVATAR_NOT_FOUND);
        }

        //upload avatar resource to oss
        String taskId = GenerateIdUtil.generateUuid();
        String delimiter = ".";
        createMetadataBO.setTaskId(taskId);
        String assetImageName = String.join(delimiter, taskId, avatarExtname);
        String assetImageUrl = uploadNewAssetToOss(assetImageName, avatarId, true);
        if (assetImageUrl == null) {
            throw new BusinessException(OSS_ADD_IMG_FAILED);
        }
        createMetadataBO.setCode(SUCCESS.getErrorCode());

        //upload metadata.json to oss
        String assetMetadataName = String.join(delimiter, taskId, "json");
        AssetUri assetUri = new AssetUri();
        assetUri.setImage(assetImageUrl);
        assetUri.setDescription(XBUILDERS_DESCRIPTION);
        assetUri.setName(String.join(XBUILDERS_NAME, avatarId));
        String assetMeta = JSON.toJSONString(assetUri);
        log.info("uploading asset meta {}", assetMeta);
        String assetMetaUri = uploadNewAssetToOss(assetMetadataName, assetMeta, false);

        if (assetMetaUri == null) {
            throw new BusinessException(OSS_ADD_METADATA_FAILED);
        }

        createMetadataBO.setUri(assetMetaUri);
        createMetadataBO.setCode(SUCCESS.getErrorCode());
        return createMetadataBO;
    }

    @Override
    public String uploadNewAssetToOss(String fileName, String asset, boolean isPic) {
        InputStream inputStream = null;
        if (isPic) {
            try {
                org.springframework.core.io.Resource resource = getResource(asset);
                inputStream = resource.getInputStream();
            } catch (IOException e) {
                log.error("addNewAssetUriToOss:{}" + e.getMessage());
                return null;
            }
        } else {
            inputStream = new ByteArrayInputStream(asset.getBytes());
        }
        String ossPath = ossUri + ossService.putAsset(XBUILDERS_ASSET_OSS_STORAGE_PATH, fileName, inputStream, isPic);
        return ossPath;
    }


    @Override
    public AssetMintBO assertMint(String assetId, String address, String assetUri) {

        // 获取合约资产信息
        Asset asset = assetMapper.getAsset(assetId);
        if (asset == null) {
            throw new BusinessException(ErrorCodeEnum.ASSET_NOT_FOUND_ERROR);
        }

        // 创建交易订单
        String orderId = GenerateIdUtil.generateId();

        // 完成初始化，数据插入DB
        initMintOrder(orderId, assetId, address, assetUri);

        // 创建交易并发送， 更新订单状态与tx hash 并插入DB
        String txHash = sendIssueRequest(orderId);

        //查询tx receipt， 更新订单状态并插入DB
        boolean isSuccess = updateIssueStatus(orderId);

        AssetMintBO assetMintBO = new AssetMintBO();
        assetMintBO.getTxHashes().add(txHash);
        assetMintBO.setCode(SUCCESS.getErrorCode());
        assetMintBO.setMsg(SUCCESS.getErrorMsg());
        return assetMintBO;
    }

    public MyPocketCreateQrCodeRequestAccountsResponse createQrCodeRequestAccounts(MyPocketCreateQrCodeRequestAccountsRequest requestAccounts) {
        requestAccounts.setDappId(appIdOfMypocket);
        MyPocketCreateQrCodeRequestAccountsResponse createQrCodeResponse = myPocketService.createQrCodeRequestAccounts(requestAccounts);
        return createQrCodeResponse;
    }

    public MyPocketCheckQrCodeRequestAccountsResponse checkQrCodeRequestAccounts(MyPocketCheckQrCodeRequestAccountsRequest checkQrCodeRequest) {
        MyPocketCheckQrCodeRequestAccountsResponse checkQrCodeResponse = myPocketService.checkQrCodeRequestAccounts(checkQrCodeRequest);
        return checkQrCodeResponse;
    }

    /**
     * sync issue asset
     *
     * @param chainId
     * @param contractName
     * @param userChainAccount
     * @param ossUri
     * @return
     */
    protected String issue(String chainId, String contractName, String userChainAccount, String ossUri) {
        log.info("issuing params {} to {}...{},{}", chainId, contractName, userChainAccount, ossUri);
        BaaSRestCallContractBO callContractBO = new BaaSRestCallContractBO();
        callContractBO.setChainId(chainId);
        callContractBO.setContractName(contractName);
        callContractBO.setContractType(ContractTypeEnum.EVM);
        callContractBO.setMethod("safeMint");

        if (ossUri.isEmpty()) {
            callContractBO.setMethodSignature("safeMint(identity)");
            callContractBO.setParams(Lists.newArrayList(new Identity(userChainAccount)));
        } else {
            callContractBO.setMethodSignature("safeMint(identity,string)");
            callContractBO.setParams(Lists.newArrayList(new Identity(userChainAccount), ossUri));
        }

        TransactionReceiptBO receiptBO = baaSRestService.callContract(callContractBO);
        if (!receiptBO.isSuccess()) {
            throw new BusinessException(ErrorCodeEnum.CALL_CONTRACT_FAIL_ERROR,
                    String.format("issue asset to %s of %s failed, tx: %s fail, %d", userChainAccount, contractName,
                            receiptBO.getTxHash(), receiptBO.getCode()));
        }

        log.info("issuing hash {}", receiptBO.getTxHash());
        return receiptBO.getTxHash();
    }


    protected String asyncIssue(String chainId, String contractName, String userChainAccount, String ossUri) {
        log.info("issuing params {} to {}...{},{}", chainId, contractName, userChainAccount, ossUri);
        BaaSRestCallContractBO callContractBO = new BaaSRestCallContractBO();
        callContractBO.setChainId(chainId);
        callContractBO.setContractName(contractName);
        callContractBO.setContractType(ContractTypeEnum.EVM);
        callContractBO.setMethod("safeMint");


        if (ossUri.isEmpty()) {
            callContractBO.setMethodSignature("safeMint(identity)");
            callContractBO.setParams(Lists.newArrayList(new Identity(userChainAccount)));
        } else {
            callContractBO.setMethodSignature("safeMint(identity,string)");
            callContractBO.setParams(Lists.newArrayList(new Identity(userChainAccount), ossUri));
        }

        String txHash = baaSRestService.sendTransaction(callContractBO);
        if (txHash == null || txHash.isEmpty()) {
            throw new BusinessException(ErrorCodeEnum.CALL_CONTRACT_FAIL_ERROR, "unknown baas rest error");
        }
        return txHash;
    }

    protected TransactionReceiptBO queryIssueReceipt(String chainId, String txHash) {
        BaasRestQueryReceiptRequest queryReceiptRequest = new BaasRestQueryReceiptRequest();
        queryReceiptRequest.setChainId(chainId);
        queryReceiptRequest.setTxHash(txHash);
        TransactionReceiptBO receiptBO = baaSRestService.queryReceipt(queryReceiptRequest);

        log.info("issuing hash {}", receiptBO.getTxHash());
        return receiptBO;
    }

    protected String sendIssueRequest(String orderId) {
        MintAssetOrder mintAssetOrder = getOrderByOrderId(orderId);

        // 检查是否已提交
        if (!(mintAssetOrder.getTxHash() == null || mintAssetOrder.getTxHash().isEmpty())) {
            throw new BusinessException(ErrorCodeEnum.ASSET_MINT_ORDER_EXIST, "transaction has been sent");
        }

        // 创建交易并发送， 更新订单状态与tx hash 并插入DB
        Asset asset = assetMapper.getAsset(mintAssetOrder.getAssetId());
        log.info("issuing asset {} to {}...{}", asset.getId(), mintAssetOrder.getToAddress());
        String txHash = asyncIssue(asset.getChainId(), asset.getContractName(), mintAssetOrder.getToAddress(), mintAssetOrder.getMetaUri());
        MintAssetOrderStatusEnum currentStatus = MintAssetOrderStatusEnum.getValue(getOrderByOrderId(orderId).getStatus());
        updateStatusAndHashByOrderId(orderId, txHash, currentStatus, MintAssetOrderStatusEnum.SENT, !txHash.isEmpty());
        return txHash;
    }

    protected boolean updateIssueStatus(String orderId) {
        //更新订单状态与tx hash 并插入DB
        MintAssetOrder mintAssetOrder = getOrderByOrderId(orderId);
        MintAssetOrderStatusEnum currentStatus = MintAssetOrderStatusEnum.getValue(mintAssetOrder.getStatus());
        Asset asset = assetMapper.getAsset(mintAssetOrder.getAssetId());
        TransactionReceiptBO receiptBO = queryIssueReceipt(asset.getChainId(), mintAssetOrder.getTxHash());
        MintAssetOrderStatusEnum newStatus = receiptBO.isSuccess() ? MintAssetOrderStatusEnum.SUCCESS : MintAssetOrderStatusEnum.FAILED;
        updateStatusByOrderId(orderId, currentStatus, newStatus, receiptBO.isSuccess());
        if (!receiptBO.isSuccess()) {
            throw new BusinessException(ErrorCodeEnum.CALL_CONTRACT_FAIL_ERROR,
                    String.format("order %s :issue asset to %s of %s failed, tx: %s fail, %d", orderId, mintAssetOrder.getToAddress(), asset.getContractName(),
                            mintAssetOrder.getTxHash(), receiptBO.getCode()));
        }
        return receiptBO.isSuccess();
    }


    public org.springframework.core.io.Resource getResource(String avatarId) {
        String delimiter = ".";
        String fileName = String.join(delimiter, avatarId, avatarExtname);
        String filePath = avatarResourcePath.concat(fileName);
        org.springframework.core.io.Resource resource = resourceLoader.getResource(filePath);
        return resource;
    }

    public boolean checkResource(String avatarId) {
        String delimiter = ".";
        String fileName = String.join(delimiter, avatarId, avatarExtname);
        String filePath = avatarResourcePath.concat(fileName);
        org.springframework.core.io.Resource resource = resourceLoader.getResource(filePath);
        return resource.exists();
    }

    private void initMintOrder(String orderId, String assetId, String address, String assetUri) {
        // 完成初始化，数据插入DB
        MintAssetOrder mintAssetOrder = new MintAssetOrder();
        mintAssetOrder.setAssetId(assetId);
        mintAssetOrder.setToAddress(address);
        mintAssetOrder.setOrderId((orderId));
        mintAssetOrder.setMetaUri(assetUri);
        mintAssetOrder.setGmtCreate(LocalDateTime.now());
        int row = mintXBuildersAssetMapper.insert(mintAssetOrder);
        if (row < 1) {
            throw new BusinessException(ErrorCodeEnum.ASSET_MINT_APPLY_ERROR, "数据库写入失败");
        }
    }

    private void updateStatusAndHashByOrderId(String orderId, String hash, MintAssetOrderStatusEnum oldStatus, MintAssetOrderStatusEnum newStatus, boolean condition) {
        if (newStatus != MintAssetOrderStatusEnum.nextState(oldStatus, condition)) {
            throw new BusinessException(ErrorCodeEnum.ASSET_MINT_ORDER_STATUS_ERROR, "订单状态异常，数据库写入失败");
        }
        int row = mintXBuildersAssetMapper.updateStatusAndHashByOrderId(orderId, hash, (short) oldStatus.getCode(), (short) newStatus.getCode());
        if (row < 1) {
            throw new BusinessException(ErrorCodeEnum.ASSET_MINT_APPLY_ERROR, "数据库写入失败");
        }
    }

    private void updateStatusByOrderId(String orderId, MintAssetOrderStatusEnum oldStatus, MintAssetOrderStatusEnum newStatus, boolean condition) {
        if (newStatus != MintAssetOrderStatusEnum.nextState(oldStatus, condition)) {
            throw new BusinessException(ErrorCodeEnum.ASSET_MINT_ORDER_STATUS_ERROR, "订单状态异常，数据库写入失败");
        }
        int row = mintXBuildersAssetMapper.updateStatusByOrderId(orderId, (short) oldStatus.getCode(), (short) newStatus.getCode());
        if (row < 1) {
            throw new BusinessException(ErrorCodeEnum.ASSET_MINT_APPLY_ERROR, "数据库写入失败");
        }
    }

    private MintAssetOrder getOrderByOrderId(String orderId) {
        MintAssetOrder mintAssetOrder = mintXBuildersAssetMapper.getOrderByOrderId(orderId);
        if (Objects.isNull(mintAssetOrder)) {
            throw new BusinessException(ErrorCodeEnum.ASSET_MINT_ORDER_NOT_FOUND);
        }
        return mintAssetOrder;
    }


}
