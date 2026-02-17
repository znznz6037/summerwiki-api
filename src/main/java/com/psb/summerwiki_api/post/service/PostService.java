package com.psb.summerwiki_api.post.service;

import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.stereotype.Service;

import com.psb.summerwiki_api.category.entity.Category;
import com.psb.summerwiki_api.category.repository.CategoryRepository;
import com.psb.summerwiki_api.post.dto.PostHistoryResponse;
import com.psb.summerwiki_api.post.dto.PostRequest;
import com.psb.summerwiki_api.post.dto.PostResponse;
import com.psb.summerwiki_api.post.entity.Post;
import com.psb.summerwiki_api.post.entity.PostHistory;
import com.psb.summerwiki_api.post.repository.PostHistoryRepository;
import com.psb.summerwiki_api.post.repository.PostRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final PostHistoryRepository postHistoryRepository;
    private final CategoryRepository categoryRepository;

    public long create(PostRequest postRequest) {
        Category category = categoryRepository.findById(postRequest.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid category ID"));

        Post post = Post.builder()
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .category(category)
                .build();

        PostHistory history = PostHistory.builder()
                .post(post)
                .title(post.getTitle())
                .content(post.getContent())
                .build();
        postHistoryRepository.save(history);
        
        return postRepository.save(post).getId();
    }

    @ReadOnlyProperty
    public PostResponse getPost(Long postId) {
        postRepository.updateViewCount(postId); // 조회수 증가

        Post post = postRepository.findByIdWithCategory(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        return new PostResponse(post);
    }

    @ReadOnlyProperty
    public PostHistoryResponse getPostHistory(Long postId) {
        PostHistory postHistory = postHistoryRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post history not found"));
        return new PostHistoryResponse(postHistory);
    }

    public void updatePost(Long postId, PostRequest postRequest) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        PostHistory history = PostHistory.builder()
                .post(post)
                .title(post.getTitle())
                .content(post.getContent())
                .build();
        postHistoryRepository.save(history); // 변경 이력 저장

        Category category = categoryRepository.findById(postRequest.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid category ID"));

        post.update(postRequest.getTitle(), postRequest.getContent(), category);
    }
}
