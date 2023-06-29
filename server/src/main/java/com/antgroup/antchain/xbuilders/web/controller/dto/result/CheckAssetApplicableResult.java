package com.antgroup.antchain.xbuilders.web.controller.dto.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckAssetApplicableResult implements Serializable {

    private static final long serialVersionUID = 6030488535821286000L;

    /**
     * 资产状态：0 - 无资格领取, 1 - 有资格未领取, 2 - 有资格已领取, 3 - 未绑定账户
     */
    private Integer status;
}
