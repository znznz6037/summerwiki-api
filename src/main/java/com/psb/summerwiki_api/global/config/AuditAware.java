package com.psb.summerwiki_api.global.config;

import java.util.Optional;

public interface AuditAware<T> {
    Optional<T> getCurrentUser();
}