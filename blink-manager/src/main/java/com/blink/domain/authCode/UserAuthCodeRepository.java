package com.blink.domain.authCode;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserAuthCodeRepository extends JpaRepository<UserAuthCode, Long> {

	Optional<UserAuthCode> findByPhoneNumber(String employeeTel);

	@Query("SELECT uac FROM UserAuthCode uac WHERE uac.phoneNumber = :employeeTel AND uac.authCode = :authCode AND uac.expireDate > :now")
	Optional<UserAuthCode> findValidAuthCode(@Param("employeeTel") String employeeTel, @Param("authCode") Integer authCode, @Param("now") LocalDateTime now);

	@Query("SELECT uac FROM UserAuthCode uac WHERE uac.phoneNumber = :employeeTel AND uac.authYn = 1")
	Optional<UserAuthCode> findByEmployeeTelAndAuthYn(@Param("employeeTel") String employeeTel);
}
