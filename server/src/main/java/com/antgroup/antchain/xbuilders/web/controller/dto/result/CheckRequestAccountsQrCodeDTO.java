package com.antgroup.antchain.xbuilders.web.controller.dto.result;


import com.antgroup.antchain.openapi.appex.models.ChainAccountEX;
import com.antgroup.antchain.xbuilders.integration.enums.QrCodeStatusEnum;
import lombok.Data;

import java.util.List;

@Data
public class CheckRequestAccountsQrCodeDTO {

    /**
     * 唯一业务号
     */
    private String bizNo;

    /**
     * 小程序码状态
     */
    private QrCodeStatusEnum status;

    /**
     * 账户列表
     */
    private List<ChainAccountEX> accounts;
}
