package com.antgroup.antchain.xbuilders.integration.entity;

import com.antgroup.antchain.openapi.appex.models.QueryMypocketUserinfoResponse;
import lombok.Data;

@Data
public class MyPocketQueryUserInfoResponse {
    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 头像url
     */
    private String avatar;

    public static MyPocketQueryUserInfoResponse fromOpenApiResponse(QueryMypocketUserinfoResponse openApiResponse) {
        MyPocketQueryUserInfoResponse responseBO = new MyPocketQueryUserInfoResponse();
        responseBO.setNickName(openApiResponse.getNickName());
        responseBO.setAvatar(openApiResponse.getAvatar());

        return responseBO;
    }
}
