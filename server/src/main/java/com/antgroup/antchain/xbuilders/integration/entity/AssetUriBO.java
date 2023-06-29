package com.antgroup.antchain.xbuilders.integration.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
public class AssetUriBO {
    /**
     * 资产名称
     */
    @JSONField(name = "name")
    private String name;

    /**
     * 资产描述
     */
    @JSONField(name = "description")
    private String description;

    /**
     * 资产图像链接
     */
    @JSONField(name = "image")
    private String image;

    /**
     * 资产图像数据，与链接互斥，数据必须未base64编码的svg图片
     */
    @JSONField(name = "image_data")
    private String imageData;

    /**
     * 资产外部资源链接
     */
    @JSONField(name = "external_url")
    private String externalUrl;

    /**
     * 资产背景图片，6位hex
     */
    @JSONField(name = "background_color")
    private String backgroundColor;

    /**
     * 资产动态资源链接，音视频资源
     */
    @JSONField(name = "animation_url")
    private String animationUrl;

    /**
     * 资产属性值
     */
    @JSONField(name = "attributes")
    private List<AssetAttributeBO> attributes;

    @Getter
    @Setter
    @ToString(callSuper = true)
    public static class AssetAttributeBO implements Serializable {
        /**
         * 资产属性类型
         */
        @JSONField(name = "trait_type")
        private String traitType;

        /**
         * 资产属性值
         */
        @JSONField(name = "value")
        private String value;

        /**
         * 资产属性展示方式
         */
        @JSONField(name = "display_type")
        private String displayType;
    }
}
