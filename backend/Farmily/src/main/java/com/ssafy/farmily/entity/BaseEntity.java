package com.ssafy.farmily.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 * 모든 Entity 클래스가 상속받아야 하는 기본 MappedSuperclass
 * @author	구본웅
 */
@MappedSuperclass
public abstract class BaseEntity {
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private LocalDateTime createdAt;

	@Column
	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	private LocalDateTime lastEditedAt;
}
