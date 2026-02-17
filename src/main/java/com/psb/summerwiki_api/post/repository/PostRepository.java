package com.psb.summerwiki_api.post.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.psb.summerwiki_api.post.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("select p from Post p join fetch p.category where p.id = :id")
    Optional<Post> findByIdWithCategory(@Param("id") Long id);

    @Modifying
    @Query("update Post p set p.viewCount = p.viewCount + 1 where p.id = :id")
    void updateViewCount(@Param("id") Long id);
}
