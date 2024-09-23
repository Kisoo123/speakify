package com.project.webSocket.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration

public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8080") // 허용할 출처를 명시
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .exposedHeaders("Authorization", "Authorization-Refresh"); // Expose headers 추가
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 캐시를 비활성화하고 싶은 정적 리소스 경로를 지정
        registry.addResourceHandler("/css/**", "/js/**", "/images/**", "/img/**")
                .addResourceLocations("classpath:/static/css/", "classpath:/static/js/", "classpath:/static/images/")
                .setCachePeriod(0) // 캐시 기간 0으로 설정 -> 캐싱 비활성화
                .resourceChain(true); // 리소스 체인 활성화
    }
}

