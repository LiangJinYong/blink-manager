package com.blink.domain.json;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blink.domain.user.UserData;
import com.blink.enumeration.InspectionSubType;
import com.blink.enumeration.InspectionType;

public interface JsonIndividualApiRepository extends JpaRepository<JsonIndividualApi, Long> {

	List<JsonIndividualApi> findByUserDataAndInspectionTypeAndInspectionSubType(UserData userData,
			InspectionType inspectionType, InspectionSubType inspectionSubType);

}
