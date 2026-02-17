package com.psb.summerwiki_api.post.dto;

import java.time.LocalDateTime;

import com.psb.summerwiki_api.post.entity.PostHistory;

import lombok.Getter;

@Getter
public class PostHistoryResponse {
    private final Long historyId;
    private final String title;
    private final LocalDateTime modifiedDate;

    public PostHistoryResponse(PostHistory history) {
        this.historyId = history.getId();
        this.title = history.getTitle();
        this.modifiedDate = history.getCreatedDate(); // 생성일이 곧 수정 시점
    }
}
