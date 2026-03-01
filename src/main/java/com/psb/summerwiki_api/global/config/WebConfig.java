package com.psb.summerwiki_api.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // TODO Auto-generated method stub
        registry.addMapping("/api/**") // 모든 API 경로에 대해 CORS 설정
                .allowedOrigins("http://localhost:5173") // 리액트 도메인 허용
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS") // 모든 HTTP 메서드 허용
                .allowedHeaders("*") // 모든 헤더 허용
                .allowCredentials(true) // 쿠키, 세션 허용
                .maxAge(3600); // 1시간 동안 캐싱
        
    }
    
    
}
