package com.ssafy.farmily.repository;

import java.util.List;

import org.hibernate.dialect.function.ListaggStringAggEmulation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ssafy.farmily.entity.CommunityPost;

public interface CommunityPostRepository extends JpaRepository<CommunityPost, Long> {
	Slice<CommunityPost> findSliceBy(Pageable pageable);
}
