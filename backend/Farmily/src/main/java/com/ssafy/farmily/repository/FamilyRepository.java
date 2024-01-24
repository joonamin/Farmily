package com.ssafy.farmily.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.farmily.entity.Family;

@Repository
public interface FamilyRepository extends JpaRepository<Family, Long> {

}
