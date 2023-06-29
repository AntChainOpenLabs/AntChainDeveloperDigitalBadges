package com.antgroup.antchain.xbuilders.base;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.antgroup.antchain.xbuilders.web.controller.dto.result.base.BaseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql(scripts = "classpath:db/drop_all.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = {"classpath:db/drop_all.sql", "classpath:schema.sql", "classpath:data.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public abstract class ControllerTestBase extends TestBase {

    @Autowired
    protected MockMvc mvc;

    protected MockHttpServletRequestBuilder buildRequest(MockHttpServletRequestBuilder builder) {
        return builder.contentType(MediaType.APPLICATION_JSON_UTF8);
    }

    protected <T> BaseResult<T> sendRequest(
            TypeReference<BaseResult<T>> respType,
            MockHttpServletRequestBuilder builder) throws Exception {

        String response = mvc.perform(
                        this.buildRequest(builder)
                ).andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();

        return JSONObject.parseObject(response, respType);
    }
}
