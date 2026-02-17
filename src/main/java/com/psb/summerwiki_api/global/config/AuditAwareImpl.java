package com.psb.summerwiki_api.global.config;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuditAwareImpl implements AuditAware<String> {

    @Override
    public Optional<String> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = "";
        if(authentication != null) {
            userId = authentication.getName();
        }
        return Optional.of(userId);
    }
    
}
