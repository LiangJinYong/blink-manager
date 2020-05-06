package com.blink.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.blink.domain.user.UserData;
import com.blink.domain.user.UserDataRepository;
import com.blink.domain.user.UserExaminationMetadata;
import com.blink.domain.user.UserExaminationMetadataDetailRepository;
import com.blink.domain.user.UserExaminationMetadataRepository;
import com.blink.enumeration.Gender;
import com.blink.enumeration.InspectionType;
import com.blink.enumeration.SearchPeriod;
import com.blink.util.CommonUtils;
import com.blink.web.hospital.dto.userExamination.InspectionTypeDto;
import com.blink.web.hospital.dto.userExamination.SingleUserExaminationResponseDto;
import com.blink.web.hospital.dto.userExamination.UserExaminationResponseDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class WebUserExaminationService {

	private final UserDataRepository userDataRepository;
	private final UserExaminationMetadataRepository metadataRepository;
	private final UserExaminationMetadataDetailRepository detailRepository;

	public UserExaminationResponseDto getUserExamination(Long hospitalId, String searchText, SearchPeriod period,
			Pageable pageable) {

		LocalDateTime time = CommonUtils.getSearchPeriod(period);

		Page<SingleUserExaminationResponseDto> list = metadataRepository
				.findBySearchTextAndPeriodWithHospital(hospitalId, searchText, time, pageable);

		List<SingleUserExaminationResponseDto> content = list.getContent();

		for (SingleUserExaminationResponseDto dto : content) {
			List<InspectionTypeDto> inspectionTypeList = detailRepository
					.findInspectionTypeByMetadata(dto.getUserExaminationId());
			dto.setInspectionTypeList(inspectionTypeList);
		}
		
		Integer totalUserExaminationCount = metadataRepository.findUserExaminationCountForHospital(hospitalId);
		Integer firstExaminationCount = detailRepository.findExaminationCountByInspectionForHospital(hospitalId, InspectionType.first);
		Integer secondExaminationCount = detailRepository.findExaminationCountByInspectionForHospital(hospitalId, InspectionType.second);
		Integer thirdExaminationCount = detailRepository.findExaminationCountByInspectionForHospital(hospitalId, InspectionType.cancer);
		Integer nonexistConsetFormCount = metadataRepository.findNonexistConsetFormCountForHospital(hospitalId);

		UserExaminationResponseDto responseDto = new UserExaminationResponseDto(totalUserExaminationCount, firstExaminationCount, secondExaminationCount, thirdExaminationCount, nonexistConsetFormCount, list);

		return responseDto;
	}

	public UserExaminationResponseDto getUserExaminationWithAdmin(String searchText, SearchPeriod period,
			Pageable pageable) {

		LocalDateTime time = CommonUtils.getSearchPeriod(period);

		Page<SingleUserExaminationResponseDto> list = metadataRepository
				.findBySearchTextAndPeriodWithHospitalWithAdmin(searchText, time, pageable);
		List<SingleUserExaminationResponseDto> content = list.getContent();

		for (SingleUserExaminationResponseDto dto : content) {
			List<InspectionTypeDto> inspectionTypeList = detailRepository
					.findInspectionTypeByMetadata(dto.getUserExaminationId());
			dto.setInspectionTypeList(inspectionTypeList);
		}
		
		Integer totalUserExaminationCount = metadataRepository.findUserExaminationCountForAdmin();
		Integer firstExaminationCount = detailRepository.findExaminationCountByInspectionForAdmin(InspectionType.first);
		Integer secondExaminationCount = detailRepository.findExaminationCountByInspectionForAdmin(InspectionType.second);
		Integer thirdExaminationCount = detailRepository.findExaminationCountByInspectionForAdmin(InspectionType.cancer);
		Integer nonexistConsetFormCount = metadataRepository.findNonexistConsetFormCountForAdmin();

		UserExaminationResponseDto responseDto = new UserExaminationResponseDto(totalUserExaminationCount, firstExaminationCount, secondExaminationCount, thirdExaminationCount, nonexistConsetFormCount, list);

		return responseDto;
	}

	public void setConsentFormExistYn(Long userExaminationId, Integer isConsentFormExistYn) {

		UserExaminationMetadata metadata = metadataRepository.findById(userExaminationId)
				.orElseThrow(() -> new IllegalArgumentException("No such metadata"));

		metadata.setConsentFormExistYn(isConsentFormExistYn);
	}

	public boolean checkUserExists(String phone, String ssnPartial) {

		Optional<UserData> userData = userDataRepository.findByPhoneAndSsnPartial(phone, ssnPartial);
		return userData.isPresent();
	}

	public void registerUserExamination(Long hospitalId, String name, LocalDate birthday, Gender gender,
			String ssnPartial, String phone, LocalDate dateExamined, Integer agreeYn, String specialCase, Integer agreeMail, Integer agreeSms, Integer agreeVisit) {

		UserData userData = UserData.builder() //
				.name(name) //
				.birthday(birthday) //
				.gender(gender) //
				.ssnPartial(ssnPartial) //
				.phone(phone) //
				.build();

		userData = userDataRepository.save(userData);

		int examinationYear = dateExamined.getYear();
		UserExaminationMetadata metadata = UserExaminationMetadata.builder() //
		.dateExamined(dateExamined) //
		.agreeYn(agreeYn) //
		.specialCase(specialCase) //
		.hospitalDataId(hospitalId) //
		.examinationYear(examinationYear) //
		.userData(userData) //
		.agreeMail(agreeMail) //
		.agreeSms(agreeSms) //
		.agreeVisit(agreeVisit) //
		.build();
		
		metadataRepository.save(metadata);
	}

	public void updateUserExamination(Long userExaminationId, String name, LocalDate birthday, Gender gender,
			String ssnPartial, String phone, LocalDate dateExamined, Integer agreeYn, String specialCase) {
		
		UserExaminationMetadata metadata = metadataRepository.findById(userExaminationId).orElseThrow(() -> new IllegalArgumentException("No such examination data"));
		
		int examinationYear = dateExamined.getYear();
		metadata.updateForUserExamination(dateExamined, examinationYear, agreeYn, specialCase);
		
		metadata.getUserData().updateForUserExamination(name, birthday, gender, ssnPartial, phone);
	}

}
