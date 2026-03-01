package com.psb.summerwiki_api.config;

import java.util.Arrays;

import org.apache.tomcat.util.file.ConfigurationSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.psb.summerwiki_api.config.auth.handler.OAuth2SuccessHandler;
import com.psb.summerwiki_api.config.auth.jwt.JwtAuthenticationFilter;
import com.psb.summerwiki_api.config.auth.service.CustomOAuth2UserService;
import com.psb.summerwiki_api.user.Role;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, OAuth2SuccessHandler oAuth2SuccessHandler, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        http
            // 1. CSRF 비활성화 및 CORS 설정 적용
            .csrf(csrf -> csrf.disable()) // 초기 개발 시 CSRF 비활성화
            .cors(cors -> cors.configurationSource(corsConfiguration()))
            
            // 세션을 사용하지 않도록 설정 (JWT 기반 인증이므로)
            .sessionManagement(session -> session.sessionCreationPolicy(org.springframework.security.config.http.SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)

            // 2. URL별 권한 관리
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/oauth2/**", "/", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll() // 정적 리소스 허용
                .requestMatchers("/api/**").hasRole(Role.USER.name()) // API는 유저 권한 필요
                .anyRequest().authenticated() // 그 외 모든 요청은 인증 필요
            )

            // 3. 로그아웃 설정
            .logout(logout -> logout
                .logoutSuccessUrl("/") // 로그아웃 성공 시 홈으로
            )

            // 4. OAuth2 로그인 설정
            .oauth2Login(oauth2 -> oauth2
                .userInfoEndpoint(userInfo -> userInfo
                    .userService(customOAuth2UserService)
                )
                .successHandler(oAuth2SuccessHandler)
            );

        return http.build();
    }

    //WebConfig에서 CORS 설정을 했지만, Spring Security에서도 CORS 설정이 필요
    @Bean
    public CorsConfigurationSource corsConfiguration() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173")); // 리액트 도메인 허용
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // 모든 HTTP 메서드 허용
        configuration.setAllowedHeaders(Arrays.asList("*")); // 모든 헤더 허용
        configuration.setAllowCredentials(true); // 쿠키, 세션 허용
        //configuration.setExposedHeaders(Arrays.asList("Authorization")); // 브라우저가 읽을 수 있도록 헤더 노출
        //configuration.setMaxAge(3600L); // 1시간 동안 캐싱

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // 모든 경로에 대해 CORS 설정

        return source;
    }
}