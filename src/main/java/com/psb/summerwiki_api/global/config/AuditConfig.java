package com.psb.summerwiki_api.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuditConfig {
    @Bean
    public AuditAware<String> auditAware() {
        return new AuditAwareImpl();
    }
}
