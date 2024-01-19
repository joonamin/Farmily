package com.ssafy.farmily.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.farmily.entity.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
}
