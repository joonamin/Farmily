package com.ssafy.farmily.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ssafy.farmily.entity.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
	@Query("""
		SELECT count(i)
		  FROM sprint s
		  	JOIN s.records r ON r.type = com.ssafy.farmily.RecordType.EVENT
		  	JOIN r.imageCards c
		  	JOIN c.image i
		""")
	int countAllImagesInSprint(Long sprintId);

	@Query("""
		SELECT i
		  FROM sprint s
		  	JOIN s.records r ON r.type = com.ssafy.farmily.RecordType.EVENT
		  	JOIN r.imageCards c
		  	JOIN c.image i
		 ORDER BY i.id DESC
		""")
	List<Image> findAllImagesInSprintOrderByIdDesc(Long sprintId);

	@Query("""
		SELECT i
		  FROM sprint s
		  	JOIN s.records r ON r.type = com.ssafy.farmily.RecordType.EVENT
		  	JOIN r.imageCards c
		  	JOIN c.image i
		 WHERE i.id in :imageIds
		 ORDER BY i.id DESC
		""")
	List<Image> findAllImagesInSprintAndIdInOrderByIdDesc(Long sprintId, Collection<Long> imageIds);
}
