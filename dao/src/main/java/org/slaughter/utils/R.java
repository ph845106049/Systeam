/*
 *
 *      Copyright (c) 2018-2025, uyaogui All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the uyaogui.com developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: uyaogui
 *
 */

package org.slaughter.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 响应信息主体
 *
 * @param <T>
 * @author uyaogui
 */
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(value = "响应信息主体")
public class R<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final int FAIL_CODE = 1;

    public static final int WARN_CODE = 2;

    @Getter
    @Setter
    @ApiModelProperty(value = "返回标记：成功标记=0，失败标记=1(红色), 2(黄色)")
    private int code;

    @Getter
    @Setter
    private int bizErrorCode;

    @Getter
    @Setter
    @ApiModelProperty(value = "返回信息")
    private String msg;

    @Getter
    @Setter
    @ApiModelProperty(value = "数据")
    private T data;

    public static <T> R<T> ok() {
        return restResult(null, CommonConstants.SUCCESS, null);
    }

    /**
     * 成功-无返回值
     *
     * @param clazz clazz
     * @return {@link R}<{@link T}>
     */
    public static <T> R<T> ok(Class<T> clazz) {
        return restResult(null, CommonConstants.SUCCESS, null);
    }

    public static <T> R<T> ok(T data) {
        return restResult(data, CommonConstants.SUCCESS, null);
    }

    public static <T> R<T> ok(T data, String msg) {
        return restResult(data, CommonConstants.SUCCESS, msg);
    }

    public static <T> R<T> error() {
        return restResult(null, CommonConstants.FAIL, null);
    }

    public static <T> R<T> error(String msg) {
        return restResult(null, CommonConstants.FAIL, msg);
    }

    public static <T> R<T> error(T data) {
        return restResult(data, CommonConstants.FAIL, null);
    }

    public static <T> R<T> error(T data, String msg) {
        return restResult(data, CommonConstants.FAIL, msg);
    }

    /**
     * 错误
     *
     * @param code 密码
     * @param msg  消息
     * @return {@link R}<{@link T}>
     */
    public static <T> R<T> error(int code, String msg) {
        return restResult(null, code, 0, msg);
    }

    public static <T> R<T> error(int code) {
        return restResult(null, code, MessageUtils.getMessage(code));
    }

    public static <T> R<T> error(int code, int bizErrorCode, String msg) {
        return restResult(null, code, bizErrorCode, msg);
    }

    public static <T> R<T> error(int code, int bizErrorCode, String msg, Class<T> clazz) {
        return restResult(null, code, bizErrorCode, msg);
    }

    public static <T> R<T> error(Code code, Class<T> clazz) {
        return restResult(null, 2, code.getCode(), code.getMsg());
    }

    static <T> R<T> restResult(T data, int code, String msg) {
        R<T> apiResult = new R<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }

    /**
     * 休息结果
     *
     * @param data         数据
     * @param code         密码
     * @param bizErrorCode we错误代码
     * @param msg          消息
     * @return {@link R}<{@link T}>
     */
    static <T> R<T> restResult(T data, int code, int bizErrorCode, String msg) {
        R<T> apiResult = new R<>();
        apiResult.setCode(code);
        apiResult.setBizErrorCode(bizErrorCode);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }

    public boolean success() {
        return code == 0 ? true : false;
    }

    /**
     * 强制性获取
     *
     * @return {@link T}
     */
    public T forceGet() {
        if (this.success()) {
            return this.getData();
        }
        throw new BusinessRuntimeException(this.getCode(), this.getMsg());
    }
}
