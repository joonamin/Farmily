package com.ssafy.farmily.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.farmily.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
