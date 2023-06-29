package com.antgroup.antchain.xbuilders.model.exception;

/**
 * 业务异常
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = -5607294841575577766L;

    private String errorLevel;

    private String errorCode;

    private String errorMsg;

    public BusinessException(ErrorCodeEnum errorCode) {
        this(errorCode, errorCode.getErrorMsg());
    }

    public BusinessException(ErrorCodeEnum errorCode, String errorMsg) {
        super(errorCode + " : " + errorMsg);
        this.errorLevel = errorCode.getErrorLevel();
        this.errorCode = errorCode.getErrorCode();
        this.errorMsg = errorMsg;
    }

    public BusinessException(ErrorCodeEnum errorCode, Throwable cause) {
        this(errorCode, errorCode.getErrorMsg(), cause);
    }

    public BusinessException(ErrorCodeEnum errorCode, String errorMsg, Throwable cause) {
        super(errorCode + " : " + errorMsg, cause);
        this.errorLevel = errorCode.getErrorLevel();
        this.errorCode = errorCode.getErrorCode();
        this.errorMsg = errorMsg;
    }

    public String getErrorLevel() {
        return errorLevel;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
