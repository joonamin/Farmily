package com.ssafy.farmily.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.farmily.entity.Tree;

@Repository
public interface TreeRepository extends JpaRepository<Tree, Long> {

}
