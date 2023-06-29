package com.antgroup.antchain.xbuilders.model.entity;

import com.antgroup.antchain.xbuilders.model.enums.LoginTokenStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 登录会话信息
 */
@Data
@AllArgsConstructor
public class ScanLoginTokenStatusBO {
    /**
     * token id
     */
    private String token;

    /**
     * token状态
     */
    private LoginTokenStatusEnum tokenStatus;

    /**
     * token时效
     */
    private Long tokenTime;
}
