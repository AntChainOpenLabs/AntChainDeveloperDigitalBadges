package com.antgroup.antchain.xbuilders.web.controller.dto.entity;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;


@Data
public class AssetMintBO {

    public AssetMintBO() {
        this.txHashes = Lists.newArrayList();
    }

    /**
     * 返回码
     */
    private String code;

    /**
     * msg
     */
    private String msg;

    /**
     * 资产上链交易hash
     */
    private List<String> txHashes;
}
