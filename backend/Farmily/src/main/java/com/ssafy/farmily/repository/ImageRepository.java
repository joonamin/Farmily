package com.ssafy.farmily.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ssafy.farmily.dto.ImageCardImageDto;
import com.ssafy.farmily.entity.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
	@Query("""
		SELECT count(i)
		  FROM Sprint s
		  	JOIN s.records r ON r.type = com.ssafy.farmily.type.RecordType.EVENT
		  	JOIN r.imageCards c
		  	JOIN c.image i
		 WHERE s.id = :sprintId
		""")
	int countAllImagesInSprint(Long sprintId);

	@Query("""
		SELECT new com.ssafy.farmily.dto.ImageCardImageDto(i.location, i.originalFileName, r.id)
		  FROM Sprint s
		  	JOIN s.records r ON r.type = com.ssafy.farmily.type.RecordType.EVENT
		  	JOIN r.imageCards c
		  	JOIN c.image i
		 WHERE s.id = :sprintId
		 ORDER BY i.id DESC
		""")
	List<ImageCardImageDto> findAllImageCardImageDtosInSprintOrderByIdDesc(Long sprintId);

	@Query("""
  		SELECT new com.ssafy.farmily.dto.ImageCardImageDto(t.location, t.originalFileName, t.recordId)
  		  FROM (
			SELECT
				i.location AS location,
				i.originalFileName AS originalFileName,
				r.id AS recordId,
				i.id AS imageId
			  FROM Sprint s
				JOIN s.records r ON r.type = com.ssafy.farmily.type.RecordType.EVENT
				JOIN r.imageCards c
				JOIN c.image i
			 WHERE s.id = :sprintId
			 ORDER BY RAND()
			 LIMIT :countMax
  		  ) t
  		  ORDER BY t.imageId DESC
		""")
	List<ImageCardImageDto> findAllImageCardImageDtosInSprintAndIdInOrderByIdDesc(Long sprintId, int countMax);
}
