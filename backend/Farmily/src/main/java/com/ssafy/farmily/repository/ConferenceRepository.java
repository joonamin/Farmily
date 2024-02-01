package com.ssafy.farmily.repository;

import org.springframework.data.repository.CrudRepository;

import com.ssafy.farmily.entity.Conference;

public interface ConferenceRepository extends CrudRepository<Conference, Long> {
}
