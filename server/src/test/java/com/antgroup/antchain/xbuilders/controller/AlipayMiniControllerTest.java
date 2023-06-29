package com.antgroup.antchain.xbuilders.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.antgroup.antchain.xbuilders.base.ControllerTestBase;
import com.antgroup.antchain.xbuilders.web.controller.dto.request.AlipayMiniLoginRequest;
import com.antgroup.antchain.xbuilders.web.controller.dto.result.AlipayMiniLoginResult;
import com.antgroup.antchain.xbuilders.web.controller.dto.result.base.BaseResult;
import org.junit.Assert;
import org.junit.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class AlipayMiniControllerTest extends ControllerTestBase {

    /**
     * 小程序登录 - 成功
     */
    @Test
    public void testAlipayMiniLogin() throws Exception {

        AlipayMiniLoginRequest loginRequest = new AlipayMiniLoginRequest();
        loginRequest.setAuthcode("mock-auth-code");

        BaseResult<AlipayMiniLoginResult> response = sendRequest(
                new TypeReference<BaseResult<AlipayMiniLoginResult>>() {
                },
                post("/api/developerLab/community/alipayMini/login")
                        .content(JSONObject.toJSONString(loginRequest))

        );

        Assert.assertTrue(response.getErrorMsg(), response.isSuccess());
    }

}
