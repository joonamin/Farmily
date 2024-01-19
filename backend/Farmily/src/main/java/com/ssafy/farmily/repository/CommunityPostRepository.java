package com.ssafy.farmily.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.farmily.entity.CommunityPost;

public interface CommunityPostRepository extends JpaRepository<CommunityPost, Long> {
}
