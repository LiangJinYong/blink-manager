package com.blink.domain.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTokenRepository extends JpaRepository<UserToken, String> {

	Optional<UserToken> findByUserInfoId(long userId);
	
	void deleteByUserInfoId(long userId);
	
}
