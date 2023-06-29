package com.antgroup.antchain.xbuilders.web.controller.dto.result;

import com.antgroup.antchain.xbuilders.web.controller.dto.entity.CreateMetadataBO;
import lombok.Data;

/**
 * @Author 彦筠
 * @Date 2023/5/24 11:05
 * @Version 2.0.6
 */
@Data
public class CreateMetadataResult {


    /**
     * 返回码
     */
    private String code;

    /**
     * msg
     */
    private String msg;

    /**
     * 返回任务 id
     */
    private String taskId;


    private String uri;

    public static CreateMetadataResult toResult(CreateMetadataBO createMetadataBO) {
        CreateMetadataResult createMetadataResult = new CreateMetadataResult();
        createMetadataResult.setCode(createMetadataBO.getCode());
        createMetadataResult.setMsg(createMetadataBO.getMsg());
        createMetadataResult.setTaskId(createMetadataBO.getTaskId());
        createMetadataResult.setUri(createMetadataBO.getUri());
        return createMetadataResult;
    }
}
