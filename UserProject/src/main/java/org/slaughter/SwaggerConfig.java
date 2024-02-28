//package org.slaughter;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//
//
////@Configuration
//public class SwaggerConfig  {
//    /*
//     * 这个方法的返回值交给Spring 管理
//     */
//    @Bean
//    public Docket petApi() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo())
//                .select()
//                //哪些包需要生成文档
//                .apis(RequestHandlerSelectors.basePackage("com.example.databaseproject.controller")) //指定提供接口所在的基包
//                .build();
//    }
//    /**
//     * 该套 API 说明，包含作者、简介、版本、host、服务URL
//     * @return
//     */
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//                .title("测试 APIs")
//                .description("测试api接口文档")
//                .termsOfServiceUrl("http://127.0.0.1:8080/")
//                .version("1.0")
//                .build();
//    }
//
////    @Override
////    public void addResourceHandlers(ResourceHandlerRegistry registry) {
////        registry.addResourceHandler("**/swagger-ui.html")
////                .addResourceLocations("classpath:/META-INF/resources/");
////        registry.addResourceHandler("/webjars*")
////                .addResourceLocations("classpath:/META-INF/resources/webjars/");
////    }
//
//}