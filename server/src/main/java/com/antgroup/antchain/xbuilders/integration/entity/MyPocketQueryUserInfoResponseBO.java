package com.antgroup.antchain.xbuilders.integration.entity;

import com.antgroup.antchain.openapi.appex.models.QueryMypocketUserinfoResponse;
import lombok.Data;

@Data
public class MyPocketQueryUserInfoResponseBO {
    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 头像url
     */
    private String avatar;

    public static MyPocketQueryUserInfoResponseBO fromOpenApiResponse(QueryMypocketUserinfoResponse openApiResponse) {
        MyPocketQueryUserInfoResponseBO responseBO = new MyPocketQueryUserInfoResponseBO();
        responseBO.setNickName(openApiResponse.getNickName());
        responseBO.setAvatar(openApiResponse.getAvatar());

        return responseBO;
    }
}
