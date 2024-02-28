package org.slaughter.config;

import org.slaughter.filter.LoginFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lhq
 * @version 1.0
 * @description:
 * @date @date 2024年01月31日 17:46
 */
@Configuration
public class LoginConfig {

    //配置filter生效
    @Bean
    public FilterRegistrationBean sessionFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new LoginFilter());
        registration.addUrlPatterns("/*");
        registration.addInitParameter("paramName", "paramValue");
        registration.setName("sessionFilter");
        registration.setOrder(1);
        return registration;
    }
}
