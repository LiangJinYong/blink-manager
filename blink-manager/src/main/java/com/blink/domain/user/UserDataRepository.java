package com.blink.domain.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blink.enumeration.Gender;

public interface UserDataRepository extends JpaRepository<UserData, Long> {

	Optional<UserData> findByPhone(String phone);

	Optional<UserData> findByPhoneAndSsnPartial(String phone, String ssnPartial);

	//통계 
	@Query("SELECT COUNT(*) FROM UserData u WHERE u.id IN :userIdList AND u.gender = :gender AND (YEAR(CURRENT_DATE) - YEAR(u.birthday) + 1) BETWEEN :ageFrom AND :ageTo")
	Integer countByGenderAndAgeBetween(@Param("userIdList") List<Long> userIdList, @Param("gender") Gender gender, @Param("ageFrom") Integer ageFrom, @Param("ageTo") Integer ageTo);
}
