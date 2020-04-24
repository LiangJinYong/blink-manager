package com.blink.domain.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserExaminationEntireDataOfOneRepository extends JpaRepository<UserExaminationEntireDataOfOne, Long>{

	Optional<UserExaminationEntireDataOfOne> findByUserDataId(Long userDataId);

	@Query("select o from UserExaminationEntireDataOfOne o where o.userData.id in :userDataIds order by o.examinationYear desc, o.id desc")
	List<UserExaminationEntireDataOfOne> findByUserDataIds(List<Long> userDataIds);


	@Query("select max(o.examinationYear) from UserExaminationEntireDataOfOne o where o.userData.id in :userDataIds")
	Integer findLastExaminationYear(List<Long> userDataIds);
}
