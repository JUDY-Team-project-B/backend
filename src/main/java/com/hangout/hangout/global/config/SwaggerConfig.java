package com.hangout.hangout.global.config;

import static com.hangout.hangout.global.common.domain.entity.Constants.AUTH_HEADER;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SecurityScheme(
    type = SecuritySchemeType.APIKEY, in = SecuritySchemeIn.HEADER,
    name = AUTH_HEADER, description = "Auth Token")
@OpenAPIDefinition(
    info = @Info(title = "여행 동행 서비스 API 명세서",
        description = "여행 동행 서비스 API 명세서",
        version = "v1"), security = {@SecurityRequirement(name = AUTH_HEADER)})
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

}