package com.antgroup.antchain.xbuilders.web.controller.dto.entity;

import com.antgroup.antchain.xbuilders.integration.entity.AssetStockBO;
import lombok.Data;

import java.io.Serializable;

@Data
public class AssetStockDTO implements Serializable {
    private static final long serialVersionUID = -8399392730325791958L;

    /**
     * 资产图片链接
     */
    private String image;

    /**
     * 资产id
     */
    private String assetId;

    /**
     * 序号
     */
    private Long index;

    public static AssetStockDTO fromBO2DTO(AssetStockBO assetStockBO) {
        if (assetStockBO == null) {
            return new AssetStockDTO();
        }

        AssetStockDTO dto = new AssetStockDTO();
        dto.setImage(assetStockBO.getResourceUrl());
        dto.setAssetId(assetStockBO.getAssetId());
        dto.setIndex(assetStockBO.getAssetIndex());
        return dto;
    }
}
