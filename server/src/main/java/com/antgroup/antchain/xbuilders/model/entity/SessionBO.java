package com.antgroup.antchain.xbuilders.model.entity;

import lombok.Data;

/**
 * 登录会话信息
 */
@Data
public class SessionBO {
    /**
     * session id
     */
    private String sessionId;

    /**
     * session时效
     */
    private Long sessionTime;
}
