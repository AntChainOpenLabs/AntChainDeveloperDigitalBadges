package com.antgroup.antchain.xbuilders.controller;

import com.alibaba.fastjson.TypeReference;
import com.antgroup.antchain.xbuilders.base.ControllerTestBase;
import com.antgroup.antchain.xbuilders.model.exception.ErrorCodeEnum;
import com.antgroup.antchain.xbuilders.web.controller.dto.request.DummyRequest;
import com.antgroup.antchain.xbuilders.web.controller.dto.result.DummyResponse;
import com.antgroup.antchain.xbuilders.web.controller.dto.result.base.BaseResult;
import com.antgroup.antchain.xbuilders.web.controller.dto.result.system.SystemStatusResult;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SystemControllerTest extends ControllerTestBase {

    /**
     * 系统状态 成功
     */
    @Test
    public void testSystemStatus() throws Exception {

        BaseResult<SystemStatusResult> response = sendRequest(
                new TypeReference<BaseResult<SystemStatusResult>>() {
                },
                post("/api/system/status")
        );

        Assert.assertTrue(response.getErrorMsg(), response.isSuccess());
    }

    @Test
    public void test404() throws Exception {

        String content = mvc.perform(post("/api/Not_exists").contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();
    }

    /**
     * 参数错误
     *
     * @throws Exception
     */
    @Test
    public void testMissingParameter() throws Exception {

        DummyRequest request = new DummyRequest();
        request.setMessage(null);

        BaseResult<DummyResponse> response = sendRequest(
                new TypeReference<BaseResult<DummyResponse>>() {
                },
                post("/api/system/echo")
        );

        Assert.assertFalse(response.isSuccess());
        Assert.assertEquals(ErrorCodeEnum.PARAMS_FAIL.getErrorCode(), response.getErrorCode());

    }

    /**
     * 系统异常
     *
     * @throws Exception
     */
    @Test
    public void testUnhandledException() throws Exception {

        DummyRequest request = new DummyRequest();
        request.setMessage(null);

        BaseResult<DummyResponse> response = sendRequest(
                new TypeReference<BaseResult<DummyResponse>>() {
                },
                post("/api/system/exception")
        );

        Assert.assertFalse(response.isSuccess());
        Assert.assertEquals(ErrorCodeEnum.SYS_FAIL.getErrorCode(), response.getErrorCode());
    }

    /**
     * 业务异常
     *
     * @throws Exception
     */
    @Test
    public void testBusinessException() throws Exception {

        DummyRequest request = new DummyRequest();
        request.setMessage(null);

        BaseResult<DummyResponse> response = sendRequest(
                new TypeReference<BaseResult<DummyResponse>>() {
                },
                post("/api/system/error")
        );

        Assert.assertFalse(response.isSuccess());
        Assert.assertEquals(ErrorCodeEnum.MYPOCKET_SERVICE_ERROR.getErrorCode(), response.getErrorCode());
    }

}
