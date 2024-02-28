package org.slaughter.utils;

import lombok.Getter;

/**
 * 代码定义
 *
 * @author shipengpipi@126.com
 * @date 2021/07/02 17:45:14
 */
@Getter
public class Code {

    /** 错误code */
    private Integer code;
    /** 错误消息 */
    private String msg;

    /**
     * 新建
     *
     * @param code 代码
     * @param msg  味精
     */
    public Code(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 代码
     * 初始化
     *
     * @param code 代码
     */
    public Code(Integer code) {
        this(code, null);
    }
    
    /** 设定错误消息 */
    public void setMsg(String msg) {
        this.msg = msg;
    }
}
