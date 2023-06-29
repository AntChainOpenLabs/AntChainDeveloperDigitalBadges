package com.antgroup.antchain.xbuilders.web.controller.dto.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CreateMetadataBO {

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

    /**
     * 返回信息
     */
    private String uri;

}
