package com.blink.domain.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blink.enumeration.Gender;

public interface UserDataRepository extends JpaRepository<UserData, Long> {

	List<UserData> findByPhoneAndSsnPartial(String phone, String ssnPartial);

	@Query("SELECT COUNT(d) FROM UserData d WHERE d.phone = :phone AND d.ssnPartial = :ssnPartial")
	Integer findUserDataCountByPhoneAndSsnPartial(@Param("phone") String phone, @Param("ssnPartial") String ssnPartial);
	
	//통계 
	@Query("SELECT COUNT(*) FROM UserData u WHERE u.id IN :userIdList AND u.gender = :gender AND (YEAR(CURRENT_DATE) - YEAR(u.birthday) + 1) BETWEEN :ageFrom AND :ageTo")
	Integer countByGenderAndAgeBetween(@Param("userIdList") List<Long> userIdList, @Param("gender") Gender gender, @Param("ageFrom") Integer ageFrom, @Param("ageTo") Integer ageTo);

}
