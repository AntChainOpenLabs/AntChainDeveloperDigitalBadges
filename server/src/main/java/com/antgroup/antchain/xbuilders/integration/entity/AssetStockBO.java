package com.antgroup.antchain.xbuilders.integration.entity;


import com.antgroup.antchain.xbuilders.integration.enums.StockStatusEnum;
import com.antgroup.antchain.xbuilders.web.controller.dto.entity.AssetStock;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 资产库存
 */
@Data
public class AssetStockBO {
    /**
     * 资产业务id
     */
    private String assetId;

    /**
     * 资产序号
     */
    private Long assetIndex;

    /**
     * 资产链接
     */
    private String resourceUrl;

    /**
     * 资产描述
     */
    private String description;

    /**
     * 资产状态【 0-未兑换 1 - 已锁定 2-已兑换 】
     */
    private StockStatusEnum status;

    /**
     * 锁超时时间
     */
    private LocalDateTime lockExpireTime;

    public static AssetStockBO fromDO(AssetStock assetStock) {
        AssetStockBO bo = new AssetStockBO();
        bo.setAssetId(assetStock.getAssetId());
        bo.setAssetIndex(assetStock.getAssetIndex());
        bo.setResourceUrl(assetStock.getResourceUrl());
        bo.setDescription(assetStock.getDescription());
        bo.setStatus(StockStatusEnum.getByCode(assetStock.getStatus()));
        bo.setLockExpireTime(assetStock.getLockExpireTime());

        return bo;
    }
}
