package com.antgroup.antchain.xbuilders.web.controller.dto.entity;

import com.antgroup.antchain.xbuilders.dal.entity.BaseEntity;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 资产库存
 */
@Data
public class AssetStock extends BaseEntity {
    /**
     * 资产业务id
     */
    private String assetId;

    /**
     * 资产序号
     */
    private Long assetIndex;

    /**
     * 资源自定义标签
     */
    private String tag;

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
    private Short status;

    /**
     * 锁定账户
     */
    private String lockedBy;

    /**
     * 锁超时时间
     */
    private LocalDateTime lockExpireTime;
}
