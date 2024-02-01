package com.ssafy.farmily.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.ssafy.farmily.dto.LogoutRequestDto;
import com.ssafy.farmily.entity.Token;

public interface TokenBlackListRepository extends CrudRepository<Token, Long> {
	Optional<Token> findByToken(String token);
}
