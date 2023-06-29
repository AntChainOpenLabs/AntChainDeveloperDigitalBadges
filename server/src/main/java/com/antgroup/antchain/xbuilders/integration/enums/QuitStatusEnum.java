package com.antgroup.antchain.xbuilders.integration.enums;

/**
 * 服务退出状态
 */
public enum QuitStatusEnum {

    /**
     * 初始化，等待确认
     */
    INIT,

    /**
     * 待执行，等待任务触发
     */
    PENDING,

    /**
     * 执行中
     */
    RUNNING,

    /**
     * 已取消
     */
    CANCEL,

    /**
     * 执行失败
     */
    FAIL,

    /**
     * 执行完成
     */
    SUCCESS,

    ;

}
