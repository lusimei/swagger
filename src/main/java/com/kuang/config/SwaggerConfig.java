package com.kuang.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    //多人同时开发时，不同的人使用的Docket也可能不同（可以同时存在多个Docket）
    @Bean
    public Docket docket3(){
        return new Docket(DocumentationType.SWAGGER_2).groupName("chris");
    }

    @Bean
    public Docket docket2(){
        return new Docket(DocumentationType.SWAGGER_2).groupName("victor");
    }

    @Bean
    public Docket docket1(){
        return new Docket(DocumentationType.SWAGGER_2).groupName("eric");
    }

    /*
        swagger最新版是3.0，与2.9及以下的用法不同
        http://localhost:8080/swagger-ui.html
    */
    @Bean
    public Docket docket(Environment environment){//参数 java环境
        // 设置什么环境下要显示swagger
        Profiles profiles = Profiles.of("dev", "test");
        // 判断当前是否处于该环境
        // 通过 enable() 接收此参数判断是否要显示
        boolean flag = environment.acceptsProfiles(profiles);
//        System.err.println(flag);
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("chad")
                .enable(flag) //配置是否启用Swagger，如果是false，在浏览器将无法访问
                // 通过.select()方法，去配置扫描接口
                .select()
                //RequestHandlerSelectors配置如何扫描接口
                .apis(RequestHandlerSelectors.basePackage("com.kuang.controller"))
                // 配置如何通过path过滤,即这里只扫描请求以/hello开头的接口
                .paths(PathSelectors.ant("/hello/**"))
                .build();

        /**
         * RequestHandlerSelectors的方法
         * any() // 扫描所有，项目中的所有接口都会被扫描到
         * none() // 不扫描接口
         * // 通过方法上的注解扫描，如withMethodAnnotation(GetMapping.class)只扫描get请求
         * withMethodAnnotation(final Class<? extends Annotation> annotation)
         * // 通过类上的注解扫描，如.withClassAnnotation(Controller.class)只扫描有controller注解的类中的接口
         * withClassAnnotation(final Class<? extends Annotation> annotation)
         * basePackage(final String basePackage) // 根据包路径扫描接口
         */
    }

    private ApiInfo apiInfo(){
        return new ApiInfo(
                "在狂神说的带领下学习Swagger",
                "this is my document",
                "5.0",
                "urn:tos",
                new Contact("非我莫属", "www.baidu.com", "15810981082@163.com"),
                "Swagger2",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList());
    }
}
