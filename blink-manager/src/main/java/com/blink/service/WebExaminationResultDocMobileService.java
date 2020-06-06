package com.blink.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.blink.domain.examinationResultDocMobile.WebExaminationResultDocMobile;
import com.blink.domain.examinationResultDocMobile.WebExaminationResultDocMobileRepository;
import com.blink.domain.hospital.Hospital;
import com.blink.domain.hospital.HospitalRepository;
import com.blink.domain.user.UserExaminationEntireDataOfOne;
import com.blink.domain.user.UserExaminationEntireDataOfOneRepository;
import com.blink.domain.user.UserExaminationMetadata;
import com.blink.domain.user.UserExaminationMetadataDetailRepository;
import com.blink.domain.user.UserExaminationMetadataRepository;
import com.blink.domain.webfiles.WebFilesRepository;
import com.blink.enumeration.GenderType;
import com.blink.enumeration.SearchPeriod;
import com.blink.util.CommonUtils;
import com.blink.web.admin.app.dto.userExamination.MobileResultDocMetadataDetailResponseDto;
import com.blink.web.admin.web.dto.WebFileResponseDto;
import com.blink.web.admin.web.dto.examinationResultDocMobile.SingleWebExaminationResultDocMobileResponseDto;
import com.blink.web.admin.web.dto.examinationResultDocMobile.WebExaminationResultDocMobileResponseDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class WebExaminationResultDocMobileService {

	private final WebExaminationResultDocMobileRepository mobileRepository;
	private final UserExaminationMetadataDetailRepository metadataDetailRepository;
	private final UserExaminationEntireDataOfOneRepository dataOfOneRepository;
	private final WebFilesRepository webFilesRepository;

	public WebExaminationResultDocMobileResponseDto getExaminationResultDocMobileList(String searchText,
			SearchPeriod period, Pageable pageable) {
		LocalDateTime time = CommonUtils.getSearchPeriod(period);
		
		Page<SingleWebExaminationResultDocMobileResponseDto> resultDocMobileList = mobileRepository.findResultDocMobildList(searchText, time, pageable);

		for (SingleWebExaminationResultDocMobileResponseDto dto : resultDocMobileList) {
			String groupId = dto.getGroupId();
			List<WebFileResponseDto> files = webFilesRepository.findByGroupId(groupId);
			dto.setFiles(files);
			
			GenderType gender = dto.getGender();
			
			if (GenderType.UNKNOWN.equals(gender) || GenderType.MALE.equals(gender)) {
				dto.setDisplayGender(0);
			} else {
				dto.setDisplayGender(1);
			}
			
			// nationality
			String ssnPartial = dto.getSsnPartial();
			Integer nationality = 0;
			if (ssnPartial != null && ssnPartial.length() > 0) {
				String lastNumber = ssnPartial.substring(ssnPartial.length()-1);
				
				switch (lastNumber) {
				case "1":
				case "2":
				case "3":
				case "4":
					nationality = 0;
					break;
					
				case "5":
				case "6":
				case "7":
				case "8":
					nationality = 1;
					break;
				}
			}
			
			dto.setNationality(nationality);

			// birthday
			String birthday = "";
			
			if (ssnPartial != null && ssnPartial.length() > 0) {
				String yearPrefix = "";
				String lastNumber = ssnPartial.substring(ssnPartial.length()-1);
				
				switch (lastNumber) {
				case "1":
				case "2":
				case "5":
				case "6":
					yearPrefix = "19";
					break;
					
				case "3":
				case "4":
				case "7":
				case "8":
					yearPrefix = "20";
					break;
				}
				
				String yearSuffix = ssnPartial.substring(0, ssnPartial.length()-1);
				
				birthday = yearPrefix + yearSuffix;
			}
			dto.setBirthday(birthday);
			
			// metadata detail
			WebExaminationResultDocMobile resultDocMobile = mobileRepository.findById(dto.getId()).orElseThrow(() -> new IllegalArgumentException("No such mobile result doc"));
			UserExaminationMetadata userExaminationMetadata = resultDocMobile.getUserExaminationMetadata();
			if (userExaminationMetadata != null) {
				List<MobileResultDocMetadataDetailResponseDto> metadataDetailList = metadataDetailRepository.findMobileMetadataDetail(userExaminationMetadata);
				dto.setMetadataDetailList(metadataDetailList);
				
				Long dataOfOneId = userExaminationMetadata.getUserExaminationEntireDataOfOne().getId();
				
				UserExaminationEntireDataOfOne dataOfOne = dataOfOneRepository.findById(dataOfOneId).orElseThrow(() -> new IllegalArgumentException("No such data of one"));
				dto.setUserExaminationEntireDataOfOne(dataOfOne);
				
				Long hospitalDataId = userExaminationMetadata.getHospitalDataId();
				dto.setHospitalDataId(hospitalDataId);
				
				String userAddress = userExaminationMetadata.getAddress();
				dto.setUserAddress(userAddress);
			}
		}
		
		Integer totalCount = mobileRepository.findTotalCount();
		Integer newlyRegisterCount = mobileRepository.findCountByStatus(0);
		Integer waitingCount = mobileRepository.findCountByStatus(1);
		Integer completedCount = mobileRepository.findCountByStatus(2);
		
		WebExaminationResultDocMobileResponseDto result = new WebExaminationResultDocMobileResponseDto(totalCount, newlyRegisterCount, waitingCount, completedCount, resultDocMobileList);
		return result;
	}

}
