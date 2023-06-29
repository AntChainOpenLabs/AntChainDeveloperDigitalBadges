package com.antgroup.antchain.xbuilders.web.controller.dto.request;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@ToString
public class CreateQrCodeRequestAccountsRequest {
    /**
     * 链ID
     */
    private String chainId;

    /**
     * 账户类型
     * ETH | MYCHAIN
     */
    @Pattern(regexp = "^(ETH|MYCHAIN)?$", message = "账户类型取值错误")
    @Size(max = 32, message = "最多32字符")
    private String accountType;
}
