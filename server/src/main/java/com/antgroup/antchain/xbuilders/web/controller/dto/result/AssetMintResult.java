package com.antgroup.antchain.xbuilders.web.controller.dto.result;

import com.antgroup.antchain.xbuilders.web.controller.dto.entity.AssetMintBO;
import lombok.Data;

import java.util.List;

/**
 * @author ：xuwen.tyx
 * @date ：Created in 2023/3/7 10:36 下午
 * @description ：
 */
@Data
public class AssetMintResult {

    /**
     * 返回码
     */
    private String code;

    /**
     * msg
     */
    private String msg;

    /**
     * 返回信息
     */
    private List<String> txHashes;

    public static AssetMintResult toResult(AssetMintBO assetMintBO) {
        AssetMintResult assetMintResult = new AssetMintResult();
        assetMintResult.setCode(assetMintBO.getCode());
        assetMintResult.setMsg(assetMintBO.getMsg());
        assetMintResult.setTxHashes(assetMintBO.getTxHashes());
        return assetMintResult;
    }
}
