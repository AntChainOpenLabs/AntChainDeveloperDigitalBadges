package com.antgroup.antchain.xbuilders.dal.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

@ToString(callSuper = true)
@Getter
@Setter
public abstract class BaseEntity implements Serializable {

    /**
     * 是否可用标志【 0-删除 1-正常 】
     */
    private Boolean delStatus;

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    private LocalDateTime gmtModified;
}
