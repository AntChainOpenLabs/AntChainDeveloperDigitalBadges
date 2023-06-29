package com.antgroup.antchain.xbuilders.web.controller.dto.entity;

import com.antgroup.antchain.xbuilders.model.enums.AssetStatusEnum;
import lombok.Data;

@Data
public class AssetBO {
    /**
     * 资产图片链接
     */
    private String image;

    /**
     * 资产id
     */
    private String assetId;

    /**
     * 资产名称
     */
    private String assetName;

    /**
     * 序号
     */
    private Integer index;

    /**
     * 资产状态：0 - 无资格领取, 1 - 有资格未领取, 2 - 有资格已领取
     */
    private AssetStatusEnum status;

    /**
     * 资产合约地址
     */
    private String contractAddress;

    /**
     * 资产链名称
     */
    private String chainName;

    /**
     * 资产兑换时间，没兑换为空
     */
    private Long mintedTime;

    /**
     * 链id
     */
    private String chainId;
}
