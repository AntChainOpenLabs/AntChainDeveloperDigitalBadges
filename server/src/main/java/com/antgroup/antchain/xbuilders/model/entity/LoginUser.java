package com.antgroup.antchain.xbuilders.model.entity;

import lombok.Data;

/**
 * 登录用户信息
 */
@Data
public class LoginUser {

    /**
     * 用户id
     */
    private String userId;

    /**
     * 蚂蚁签约账户，邮箱或者手机号
     */
    private String loginName;

    /**
     * 用户DID
     */
    private String userDid;
}
