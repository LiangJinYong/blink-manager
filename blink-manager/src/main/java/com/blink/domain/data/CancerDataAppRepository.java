package com.blink.domain.data;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface CancerDataAppRepository extends JpaRepository<CancerDataApp, Long>{

	@Query(value = "SELECT a.* FROM cancer_data_app a, user_examination_entire_data_of_one u " +
			"WHERE a.user_examination_entire_data_of_one_id = u.id AND u.user_data_id = :userDataId AND a.type = :type", nativeQuery = true)
	Optional<CancerDataApp> getCancerDataApp(@Param("userDataId") Long userDataId, @Param("type") String type);

}
