package com.ssafy.farmily.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.farmily.entity.Sprint;

@Repository
public interface SprintRepository extends JpaRepository<Sprint, Long> {
}
