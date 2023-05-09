package com.hangout.hangout.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer {

    @Bean
    public Docket restAPI() {
        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo())
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.hangout"))
            .paths(PathSelectors.any())
            .build();
    }

    /**
     * cors error를 방지하기 위한 설정으로, WebMvcConfigurer의 addCorsMapping을 override
     *
     * <p>{@code allowedOrigins} : 자원 공유를 허락할 Origin 지정으로, 외부에서 들어오는 허용 url 설정
     * <p>{@code allowedMethods} : 허용 HTTP method
     * <p>{@code allowCredentials} : 자격증명 허용
     * <p>{@code maxAge} : 허용 시간
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("/*")
            .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
            .allowCredentials(true)
            .allowedHeaders("*")
            .maxAge(3600);
        WebMvcConfigurer.super.addCorsMappings(registry);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("hangout Spring Boot REST API")
            .version("v1.0.0")
            .description("hangout팀 api 문서입니다.")
            .build();
    }
}