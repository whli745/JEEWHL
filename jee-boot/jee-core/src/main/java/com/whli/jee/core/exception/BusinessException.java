package com.whli.jee.core.exception;

/**
 * Created by whli on 2018/1/12.
 */
public class BusinessException extends RuntimeException{
    /**
     * 错误编码
     */
    private String errorCode;

    /**
     * 构造一个基本异常.
     *
     * @param message
     *            信息描述
     */
    public BusinessException(String message)
    {
        super(message);
    }

    /**
     * 构造一个基本异常.
     *
     * @param message
     *            信息描述
     * @param cause
     *            根异常类（可以存入任何异常）
     */
    public BusinessException(String message, Throwable cause)
    {
        super(message, cause);
    }

    /**
     * 构造一个基本异常.
     *
     * @param errorCode
     *            错误编码
     * @param message
     *            信息描述
     */
    public BusinessException(String errorCode, String message)
    {
        this(errorCode, message,null);
    }

    /**
     * 构造一个基本异常.
     *
     * @param errorCode
     *            错误编码
     * @param message
     *            信息描述
     */
    public BusinessException(String errorCode, String message, Throwable cause)
    {
        this(message,cause);
        this.setErrorCode(errorCode);

    }



    public String getErrorCode()
    {
        return errorCode;
    }

    public void setErrorCode(String errorCode)
    {
        this.errorCode = errorCode;
    }
}
