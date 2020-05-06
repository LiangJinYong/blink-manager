package com.blink.domain.json;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blink.domain.user.UserData;
import com.blink.enumeration.InspectionSubType;
import com.blink.enumeration.InspectionType;

public interface JsonIndividualApiRepository extends JpaRepository<JsonIndividualApi, Long> {

	Optional<JsonIndividualApi> findByUserDataAndInspectionTypeAndInspectionSubType(UserData userData,
			InspectionType inspectionType, InspectionSubType inspectionSubType);

}
