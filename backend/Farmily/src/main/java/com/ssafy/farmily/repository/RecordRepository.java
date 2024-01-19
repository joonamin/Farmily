package com.ssafy.farmily.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.farmily.entity.Record;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {
}
