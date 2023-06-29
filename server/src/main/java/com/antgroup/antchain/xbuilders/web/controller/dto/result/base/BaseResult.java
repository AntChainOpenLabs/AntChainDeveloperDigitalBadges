package com.antgroup.antchain.xbuilders.web.controller.dto.result.base;

import com.antgroup.antchain.xbuilders.model.exception.ErrorCodeEnum;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * 基础响应
 *
 * @author 圣百
 * @date 2020/12/16
 */
@Data
@ToString
public class BaseResult<T> implements Serializable {

    private static final long serialVersionUID = 7445733837307526247L;

    /**
     * 是否请求成功
     */
    private boolean success;

    /**
     * 错误码
     */
    private String errorCode;

    /**
     * 错误消息
     */
    private String errorMsg;

    /**
     * 返回结果
     */
    private T data;

    /**
     * 响应成功结果
     *
     * @param data 成功数据
     * @return
     */
    public static <T> BaseResult<T> success(T data) {
        BaseResult result = new BaseResult();
        result.setSuccess(true);
        result.setErrorCode(ErrorCodeEnum.SUCCESS.getErrorCode());
        result.setErrorMsg(ErrorCodeEnum.SUCCESS.getErrorMsg());
        result.setData(data);
        return result;
    }

    /**
     * 响应成功结果
     *
     * @return
     */
    public static <T> BaseResult<T> success() {
        BaseResult result = new BaseResult();
        result.setSuccess(true);
        result.setErrorCode(ErrorCodeEnum.SUCCESS.getErrorCode());
        result.setErrorMsg(ErrorCodeEnum.SUCCESS.getErrorMsg());
        return result;
    }

    /**
     * 响应失败结果
     *
     * @param errorCode 错误码
     * @param errorMsg  错误消息
     * @return
     */
    public static <T> BaseResult<T> fail(String errorCode, String errorMsg) {
        BaseResult result = new BaseResult();
        result.setSuccess(false);
        result.setErrorCode(errorCode);
        result.setErrorMsg(errorMsg);
        return result;
    }

}
