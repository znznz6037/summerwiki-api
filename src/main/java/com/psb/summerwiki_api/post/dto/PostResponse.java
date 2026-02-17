package com.psb.summerwiki_api.post.dto;

import java.time.LocalDateTime;

import com.psb.summerwiki_api.post.entity.Post;

import lombok.Getter;

@Getter
public class PostResponse {
    private final Long id;
    private final String title;
    private final String content;
    private final Long viewCount;
    private final Long categoryId;
    private final String categoryName; // 카테고리 이름도 같이 보여주면 좋겠죠?
    private final LocalDateTime lastModifiedDate;

    public PostResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.viewCount = post.getViewCount();
        this.categoryId = post.getCategory().getId();
        this.categoryName = post.getCategory().getName();
        this.lastModifiedDate = post.getLastModifiedDate();
    }
}
