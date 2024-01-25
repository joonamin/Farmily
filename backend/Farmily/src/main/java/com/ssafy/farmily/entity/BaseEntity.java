package com.ssafy.farmily.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 모든 Entity 클래스가 상속받아야 하는 기본 MappedSuperclass
 * @author	구본웅
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@SuperBuilder
@Getter
@RequiredArgsConstructor
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
