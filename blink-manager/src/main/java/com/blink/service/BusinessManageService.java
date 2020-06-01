package com.blink.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.blink.domain.examinationResultDoc.WebExaminationResultDocRepository;
import com.blink.domain.hospital.Hospital;
import com.blink.domain.hospital.HospitalRepository;
import com.blink.domain.sendMailResultWeb.SendMailResultWebRepository;
import com.blink.domain.user.UserExaminationMetadataRepository;
import com.blink.domain.webfiles.WebFilesRepository;
import com.blink.web.admin.web.dto.WebFileResponseDto;
import com.blink.web.admin.web.dto.business.HospitalBusinessResponseDto;
import com.blink.web.admin.web.dto.business.SingleBusinessResponseDto;
import com.blink.web.admin.web.dto.business.SingleHospitalBusinessResponseDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class BusinessManageService {

	private final UserExaminationMetadataRepository metadataRepository;
	private final HospitalRepository hospitalRepository;
	private final WebExaminationResultDocRepository webExaminationResultDocRepository;
	private final WebFilesRepository webFilesRepository;

	public Page<SingleBusinessResponseDto> getBusinessData(String searchText, String searchDate, Pageable pageable) {
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime startDate = LocalDate.parse(searchDate, formatter).atStartOfDay().minusDays(2);
		
		Page<SingleBusinessResponseDto> resultDocList = webExaminationResultDocRepository.findBySearchTextAndPeriodForBusiness(searchText, startDate, pageable);
		
		for(SingleBusinessResponseDto dto : resultDocList) {
			String groupId = dto.getGroupId();
			List<WebFileResponseDto> files = webFilesRepository.findByGroupId(groupId);
			dto.setFiles(files);
		}
		
		return resultDocList;
	}

	public HospitalBusinessResponseDto getDashboardDataForHospital(Long hospitalId, String searchText, String searchDate,
			Pageable pageable) {
		
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime date = LocalDate.parse(searchDate, formatter).atStartOfDay();
		date = date.minusDays(3);
		
		Hospital hospital = hospitalRepository.findById(hospitalId).orElseThrow(() -> new IllegalArgumentException("No such hospital"));
		Page<SingleHospitalBusinessResponseDto> singleHospitalBusinessPage = webExaminationResultDocRepository.findByCreatedAtAndHospital(date, hospital, pageable);
		
		List<SingleHospitalBusinessResponseDto> content = singleHospitalBusinessPage.getContent();
		
		Integer agreeYCount = metadataRepository.findAgreeYnCount(date, hospitalId, 0);
		Integer agreeNCount = metadataRepository.findAgreeYnCount(date, hospitalId, 1);
		
		for(SingleHospitalBusinessResponseDto responseDto : content) {
			String groupId = responseDto.getGroupId();
			if (groupId != null) {
				List<WebFileResponseDto> files = webFilesRepository.findByGroupId(groupId);
				responseDto.setFiles(files);
			}
			
			responseDto.setCreatedAt(searchDate);
			responseDto.setAgreeYCount(agreeYCount);
			responseDto.setAgreeNCount(agreeNCount);
		}
		
		HospitalBusinessResponseDto result = new HospitalBusinessResponseDto(agreeYCount, agreeNCount, singleHospitalBusinessPage);
		return result;
	}
}
