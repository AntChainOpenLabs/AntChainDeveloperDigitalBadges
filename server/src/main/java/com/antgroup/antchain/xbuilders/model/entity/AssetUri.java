package com.antgroup.antchain.xbuilders.model.entity;

import lombok.Data;


@Data
public class AssetUri {
    /**
     * 资产名称
     */
    private String name;

    /**
     * 资产描述
     */
    private String description;

    /**
     * 资产图像链接
     */
    private String image;
}
