package com.antgroup.antchain.xbuilders.web.controller.dto.request;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Data
@ToString
public class CheckQrCodeRequestAccountsRequest {
    /**
     * 链ID
     */
    @NotBlank
    private String bizNo;
}
