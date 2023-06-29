package com.antgroup.antchain.xbuilders.model.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class LoginQrCodeBO {
    /**
     * 登录二维码信息
     * 例如：alipay://builders-dao-app/yunqi-login-page?login-token=xxxxx
     */
    private String qrCodeContent;

    /**
     * 登录token信息，uuid v4格式
     * 例如：7694052a-22c5-4d89-b537-4a9e928f31fc
     */
    private String loginToken;
}
