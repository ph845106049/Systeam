package org.slaughter.utils;

import lombok.Getter;

/**
 * 业务异常error
 *
 * @author shipengpipi@126.com
 */
@Getter
public class BusinessRuntimeException extends BaseError {

    private static final long serialVersionUID = 4523595871109786471L;

    /** code代码 */
    private Integer code;
    /** code消息 */
    private String msg;

    /**
     * @param e
     * @param code
     * @param msg
     */
    public BusinessRuntimeException(Exception e, Integer code, String msg) {
        super(e);
        this.code = code;
        this.msg = msg;
    }

    /**
     * 初始化
     *
     * @param e
     * @param errorCode
     */
    public BusinessRuntimeException(Exception e, Code errorCode) {
        super(e);
        if (errorCode != null) {
            this.code = errorCode.getCode();
            this.msg = errorCode.getMsg();
        }
    }

    /**
     * 通过code新建
     *
     * @param code
     */
    public BusinessRuntimeException(Integer code) {
        this.code = code;
    }

    /**
     * 初始化
     *
     * @param code
     * @param msg
     */
    public BusinessRuntimeException(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 初始化
     *
     * @param codeEnum
     */
    public BusinessRuntimeException(Code codeEnum) {
        this.code = codeEnum.getCode();
        this.msg = codeEnum.getMsg();
    }

}
