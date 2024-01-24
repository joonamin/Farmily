package com.ssafy.farmily.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.farmily.entity.ImageCard;

public interface ImageCardRepository extends JpaRepository<ImageCard, Long> {
}
