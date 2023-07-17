package com.hangout.hangout.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@OpenAPIDefinition(
    info = @Info(title = "여행 동행 서비스 API 명세서",
        description = "여행 동행 서비스 API 명세서",
        version = "v1"))
@RequiredArgsConstructor
@Configuration
public class SwaggerConfig implements WebMvcConfigurer {

    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
            .group("여행 동행 서비스 API v1")
            .packagesToScan("com.hangout.hangout.domain")
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
            .allowedOrigins("/*","http://localhost:3000/")
            .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
            .allowCredentials(true)
            .allowedHeaders("*")
            .maxAge(3600);
        WebMvcConfigurer.super.addCorsMappings(registry);
    }

}