package com.antgroup.antchain.xbuilders.web.controller.dto.request;

import com.antgroup.antchain.xbuilders.model.exception.BusinessException;
import com.antgroup.antchain.xbuilders.model.exception.ErrorCodeEnum;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author ：xuwen.tyx
 * @date ：Created in 2023/3/7 10:36 下午
 * @description ：
 */
@Data
public class AssetMintRequest {

    private static final int MYCHAIN_ADDRESS_LENGTH = 64;

    public AssetMintRequest() {
    }

    /**
     * 涉及的资产信息，得到对应的 ERC721 合约 id
     * sit id: b6d247be-fc4a-4b4a-a9b1-57b6c612aigc
     * contract id: 2323a18c6400b6c608ddce5f9bbff972e41209e1c2a16ad1ed17e1d07e8f8c55
     * contract name: aigc721nft
     */
    @NotBlank
    private String assetId;

    /**
     * 输入的账户地址
     */
    @NotBlank
    private String address;

    /**
     * 输入的metadata uri
     */
    private String uri;

    public void validateAddress() {
        if (this.address.startsWith("0x") || this.address.startsWith("0X")) {
            this.address = this.address.substring(2);
        }
        if (this.address.length() != MYCHAIN_ADDRESS_LENGTH) {
            throw new BusinessException(ErrorCodeEnum.ASSET_MINT_INPUT_ADDRESS_INVALID);
        }
    }

    public void validateUri() {
        if (this.uri == null || this.uri.isEmpty()) {
            throw new BusinessException(ErrorCodeEnum.ASSET_MINT_INPUT_ADDRESS_INVALID);
        }
    }

}
