package com.antgroup.antchain.xbuilders.model.entity;

import com.antgroup.antchain.xbuilders.dal.entity.BaseEntity;
import lombok.Builder;
import lombok.Data;

/**
 * 用户资产
 */
@Data
public class Asset extends BaseEntity {
    /**
     * 资产业务id
     */
    private String id;

    /**
     * 本资产所在资产组id
     */
    private String groupId;

    /**
     * 链id
     */
    private String chainId;

    /**
     * 合约id
     */
    private String contractId;

    /**
     * 资产发放展会名称展会
     */
    private String exhibition;

    /**
     * 合约名称
     */
    private String contractName;

    /**
     * 资产合约类型【0-ERC721 1-ERC1155 2-DRC721】
     */
    private Short contractType;

    /**
     * 资产子ID，仅部分资产合约生效
     */
    private String subId;

    /**
     * 资产类型【0-avatar 1-badge】
     */
    private Short assetType;

    /**
     * 资产名称
     */
    private String name;

    /**
     * 资产已兑换数量
     */
    private Integer redeemedAmount;

    /**
     * 资产发放总量，0为无上限
     */
    private Integer totalAmount;
}
