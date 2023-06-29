package com.antgroup.antchain.xbuilders.web.controller.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;


@Data
public class DummyRequest {

    @NotBlank
    private String message;
}
