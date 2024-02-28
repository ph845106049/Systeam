/**
 * Copyright (c) 2020 云好药 All rights reserved.
 *
 * http://www.yunhaoyao.com
 *
 * 版权所有，侵权必究！
 */

package org.slaughter.utils;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * 国际化
 *
 * @author admin
 * @since 1.0.0
 */
public class MessageUtils {
    public static ApplicationContext applicationContext;
    private static MessageSource messageSource;
    static {
        messageSource = (MessageSource) applicationContext.getBean("messageSource");
    }

    public static String getMessage(int code){
        return getMessage(code, new String[0]);
    }

    public static String getMessage(int code, String... params){
        return messageSource.getMessage(code+"", params, LocaleContextHolder.getLocale());
    }
}
