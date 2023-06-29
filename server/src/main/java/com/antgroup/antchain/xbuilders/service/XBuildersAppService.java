package com.antgroup.antchain.xbuilders.service;

import com.antgroup.antchain.xbuilders.integration.entity.MyPocketCheckQrCodeRequestAccountsRequest;
import com.antgroup.antchain.xbuilders.integration.entity.MyPocketCheckQrCodeRequestAccountsResponse;
import com.antgroup.antchain.xbuilders.integration.entity.MyPocketCreateQrCodeRequestAccountsRequest;
import com.antgroup.antchain.xbuilders.integration.entity.MyPocketCreateQrCodeRequestAccountsResponse;
import com.antgroup.antchain.xbuilders.web.controller.dto.entity.AssetMintBO;
import com.antgroup.antchain.xbuilders.web.controller.dto.entity.CreateMetadataBO;

/**
 * 开发者生态应用样例服务
 */

public interface XBuildersAppService {


    /**
     * 生成MetaData并获取uri
     *
     * @param avatarId
     * @return 返回服务处理结果
     */
    CreateMetadataBO generateMetadata(String avatarId);

    /**
     * 上传资源到oss
     *
     * @param taskId
     * @param asset
     * @param isPic
     * @return
     */
    String uploadNewAssetToOss(String taskId, String asset, boolean isPic);

    /**
     * 资产铸造
     *
     * @param assetId  资产地址
     * @param address  目标账户地址
     * @param assetUri 资产metadata uri
     * @return 返回服务处理结果
     */
    AssetMintBO assertMint(String assetId, String address, String assetUri);

    /**
     * @param createQrCodeRequestAccountsRequest
     * @return
     */
    MyPocketCreateQrCodeRequestAccountsResponse createQrCodeRequestAccounts(MyPocketCreateQrCodeRequestAccountsRequest createQrCodeRequestAccountsRequest);

    /**
     * @param checkQrCodeRequestAccountsRequest
     * @return
     */
    MyPocketCheckQrCodeRequestAccountsResponse checkQrCodeRequestAccounts(MyPocketCheckQrCodeRequestAccountsRequest checkQrCodeRequestAccountsRequest);

}
