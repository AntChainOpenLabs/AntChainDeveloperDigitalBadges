package com.antgroup.antchain.xbuilders.web.controller;

import com.antgroup.antchain.xbuilders.dal.entity.ChainConfig;
import com.antgroup.antchain.xbuilders.dal.mapper.ChainConfigMapper;
import com.antgroup.antchain.xbuilders.model.exception.BusinessException;
import com.antgroup.antchain.xbuilders.model.exception.ErrorCodeEnum;
import com.antgroup.antchain.xbuilders.web.controller.dto.request.DummyRequest;
import com.antgroup.antchain.xbuilders.web.controller.dto.result.DummyResponse;
import com.antgroup.antchain.xbuilders.web.controller.dto.result.base.BaseResult;
import com.antgroup.antchain.xbuilders.web.controller.dto.result.system.SystemStatusResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("api/system/")
public class SystemController {

    @Autowired
    private ChainConfigMapper configMapper;

    /**
     * 系统健康情况查询
     *
     * @return
     */
    @PostMapping("status")
    public BaseResult<SystemStatusResult> status() {
        SystemStatusResult result = new SystemStatusResult();

        List<ChainConfig> chainConfigs = configMapper.listAllChainConfigs();
        result.setDatabase(!chainConfigs.isEmpty());

        return BaseResult.success(result);
    }

    @PostMapping("echo")
    public BaseResult<DummyResponse> echo(@Valid DummyRequest request) {
        DummyResponse dummyResponse = new DummyResponse();
        dummyResponse.setMessage(request.getMessage());
        return BaseResult.success(dummyResponse);
    }

    @PostMapping("exception")
    public BaseResult<DummyResponse> exception(DummyRequest request) {
        throw new RuntimeException();
    }

    @PostMapping("error")
    public BaseResult<DummyResponse> error(DummyRequest request) {
        throw new BusinessException(ErrorCodeEnum.MYPOCKET_SERVICE_ERROR);
    }

}
