package com.antgroup.antchain.xbuilders.web.controller.dto.result;

import lombok.Data;

@Data
public class CreateRequestAccountsQrCodeDTO {

    /**
     * 唯一业务号
     */
    private String bizNo;

    /**
     * 小程序码地址
     */
    private String qrCodeUrl;

}
