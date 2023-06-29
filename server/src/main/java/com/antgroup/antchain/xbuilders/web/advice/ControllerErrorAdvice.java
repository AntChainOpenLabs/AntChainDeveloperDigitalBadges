package com.antgroup.antchain.xbuilders.web.advice;

import com.antgroup.antchain.xbuilders.model.exception.BusinessException;
import com.antgroup.antchain.xbuilders.model.exception.ErrorCodeEnum;
import com.antgroup.antchain.xbuilders.web.controller.dto.result.base.BaseResult;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.ServletException;

@Slf4j
@RestControllerAdvice("com.antgroup.antchain.xbuilders.web.controller")
public class ControllerErrorAdvice {
    private static final Logger logger = LoggerFactory.getLogger("REQ-EXCEPTION-STACK-LOG");


    /**
     * 参数异常处理
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(BindException.class)
    public BaseResult parameterExceptionHandler(BindException ex) {
        // 获取异常信息
        BindingResult bindingResult = ex.getBindingResult();
        String msg = bindingResult.getAllErrors().iterator().next().getDefaultMessage();
        return BaseResult.fail(ErrorCodeEnum.PARAMS_FAIL.getErrorCode(), msg);
    }

    /**
     * 业务异常处理
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    public BaseResult<Void> businessException(BusinessException ex) {
        if (!"P5".equals(ex.getErrorLevel())) {
            logger.error("controller execute error", ex);
        }
        return BaseResult.fail(ex.getErrorCode(), ex.getErrorMsg());
    }

    /**
     * 请求体读取异常处理
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public BaseResult<Void> readBodyException(HttpMessageNotReadableException ex) {
        return BaseResult.fail(ErrorCodeEnum.PARAMS_FAIL.getErrorCode(), ErrorCodeEnum.PARAMS_FAIL.getErrorMsg());
    }

    /**
     * 全局异常捕捉处理
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    public BaseResult<Void> errorHandler(Exception ex) throws ServletException {
        logger.error("controller execute error", ex);
        if (ex instanceof ServletException) {
            // 框架相关异常不处理
            throw (ServletException) ex;
        }
        return BaseResult.fail(ErrorCodeEnum.SYS_FAIL.getErrorCode(), ErrorCodeEnum.SYS_FAIL.getErrorMsg());
    }

}
