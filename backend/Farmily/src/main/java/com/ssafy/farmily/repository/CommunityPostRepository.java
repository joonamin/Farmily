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
	@Query(value = """
	SELECT * FROM community_post
	ORDER BY id DESC
	LIMIT :pageSize""", nativeQuery = true)
	List<CommunityPost> locaSliceFirstList(@Param("pageSize") int pageSize);
	// SELECT * FROM community_post WHERE id > 마지막 조회 id ORDER BY id DESC LIMIT 페이지사이즈;
	@Query(value = """
	SELECT * FROM community_post
	WHERE id < :lastSeenId
	ORDER BY id DESC
	LIMIT :pageSize""", nativeQuery = true)
	List<CommunityPost> loadSliceList(@Param("lastSeenId") Long lastSeenId,@Param("pageSize") int pageSize);
}
