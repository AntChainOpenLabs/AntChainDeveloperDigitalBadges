package com.antgroup.antchain.xbuilders.web.controller.dto.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 区块链信息结果
 *
 * @author 圣百
 * @date 2021/04/11
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlockchainInfoResult {

    /**
     * 区块链业务id
     */
    private String bizId;

    /**
     * 区块链名称
     */
    private String name;

    /**
     * BaaS系统的区块链名称
     */
    private String chainName;

    /**
     * 机构名称
     */
    private String organization;
}
