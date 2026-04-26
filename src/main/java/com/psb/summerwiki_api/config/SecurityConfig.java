package com.psb.summerwiki_api.config;

import java.util.Arrays;

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

<<<<<<< HEAD
import jakarta.servlet.http.HttpServletResponse;
=======
>>>>>>> origin/main
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, OAuth2SuccessHandler oAuth2SuccessHandler, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(corsConfiguration()))
            .sessionManagement(session -> session.sessionCreationPolicy(org.springframework.security.config.http.SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
<<<<<<< HEAD
            .exceptionHandling(exceptions -> exceptions
                .authenticationEntryPoint((request, response, authException) -> {
                    // 인증 실패 시 401 Unauthorized 응답해서 React에서 토큰 만료로 인식하도록 처리
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setContentType("application/json;charset=UTF-8");
                    response.getWriter().write("{\"error\": \"Unauthorized\", \"message\": \"" + authException.getMessage() + "\"}");
                })
            )
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/login", "/api/auth/refresh", "/api/auth/logout", "/oauth2/**", "/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/assets/**").permitAll() // 정적 리소스 허용
=======
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/login", "/oauth2/**", "/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/assets/**").permitAll() // 정적 리소스 허용
>>>>>>> origin/main
                .requestMatchers("/api/**").hasRole(Role.USER.name())
                .anyRequest().authenticated()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/")
            )
            .oauth2Login(oauth2 -> oauth2
                .userInfoEndpoint(userInfo -> userInfo
                    .userService(customOAuth2UserService)
                )
                .successHandler(oAuth2SuccessHandler)
            );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfiguration() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173")); // 배포 시 운영 URL 추가 필요
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
<<<<<<< HEAD
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L); //Preflight 요청 캐싱 시간
=======
        configuration.setAllowCredentials(false);
        configuration.setMaxAge(3600L);
>>>>>>> origin/main

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}