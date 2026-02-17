package com.psb.summerwiki_api.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.psb.summerwiki_api.post.entity.PostHistory;

public interface PostHistoryRepository extends JpaRepository<PostHistory, Long> {
    
}