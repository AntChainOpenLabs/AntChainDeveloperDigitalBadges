package com.antgroup.antchain.xbuilders.integration.entity;

import com.antgroup.antchain.xbuilders.integration.enums.ContractTypeEnum;
import lombok.Data;

import java.util.List;

@Data
public class BaasRestCallContractRequest {
    /**
     * 链id
     */
    private String chainId;

    /**
     * 合约名称
     */
    private String contractName;

    /**
     * 合约类型
     */
    private ContractTypeEnum contractType;

    /**
     * 合约方法
     */
    private String method;

    /**
     * 合约方法签名，仅EVM合约需要，其他情况留空即可
     */
    private String methodSignature;

    /**
     * 合约参数
     */
    private List<Object> params;
}
