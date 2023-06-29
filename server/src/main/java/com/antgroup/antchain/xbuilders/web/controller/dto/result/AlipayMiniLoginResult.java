package com.antgroup.antchain.xbuilders.web.controller.dto.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AlipayMiniLoginResult implements Serializable {

    private static final long serialVersionUID = 7972768724879657296L;

    /**
     * 支付宝昵称
     */
    private String alipayNickName;

    /**
     * 支付宝头像
     */
    private String alipayAvatar;

    /**
     * 支付宝uid
     */
    private String alipayUid;

    /**
     * 登录session的id
     */
    private String sessionId;

    /**
     * 会话超时时间
     */
    private Long sessionTime;
}
