package com.blink.domain.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {

	Optional<UserInfo> findByPhoneNumber(String phoneNumber);
	Optional<UserInfo> findByPhoneNumberAndAuthYN(String phoneNumber, boolean authYN);
	Long countByEmail(String email);
	
	@Query(value = "SELECT * FROM user_info WHERE birthday = :birthday AND name = :name AND phone_number=:phoneNumber AND gender = :gender LIMIT 1", nativeQuery=true)
	Optional<UserInfo> findByMappingUser(@Param("birthday") String birthday, @Param("name") String name, @Param("phoneNumber") String phoneNumber, @Param("gender") String gender);
}
