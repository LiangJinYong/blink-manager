package com.blink.domain.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDataRepository extends JpaRepository<UserData, Long> {

	Optional<UserData> findByPhone(String phone);

	Optional<UserData> findByPhoneAndSsnPartial(String phone, String ssnPartial);
}
