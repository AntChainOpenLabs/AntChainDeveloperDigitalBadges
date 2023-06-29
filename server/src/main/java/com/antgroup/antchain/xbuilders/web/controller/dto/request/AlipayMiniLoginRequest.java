package com.antgroup.antchain.xbuilders.web.controller.dto.request;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@ToString
public class AlipayMiniLoginRequest implements Serializable {
    private static final long serialVersionUID = -7851185747133344318L;

    /**
     * 支付宝小程序auth code
     */
    @NotBlank
    private String authcode;
}
