package com.psb.summerwiki_api.post.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.psb.summerwiki_api.global.common.ApiResponse;
import com.psb.summerwiki_api.post.dto.PostHistoryResponse;
import com.psb.summerwiki_api.post.dto.PostRequest;
import com.psb.summerwiki_api.post.dto.PostResponse;
import com.psb.summerwiki_api.post.service.PostService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    
    private final PostService postService;

    @PostMapping
    public ApiResponse<Long> createPost(@RequestBody PostRequest postRequest) {
        Long postId = postService.create(postRequest);
        return ApiResponse.success(postId);
    }

    @GetMapping("/{id}")
    public ApiResponse<PostResponse> getPost(@PathVariable("id") Long id) {
        return ApiResponse.success(postService.getPost(id));
    }

    @GetMapping("/{id}/history")
    public ApiResponse<PostHistoryResponse> getPostHistory(@PathVariable("id") Long id) {
        return ApiResponse.success(postService.getPostHistory(id));
    }
    
    @PutMapping("/{id}")
    public ApiResponse<Void> update(@PathVariable("id") Long id, @RequestBody PostRequest postRequest) {
        postService.updatePost(id, postRequest);
        return ApiResponse.success(null);
    }
}
