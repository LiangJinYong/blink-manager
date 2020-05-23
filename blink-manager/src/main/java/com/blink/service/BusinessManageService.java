package com.blink.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.blink.domain.examinationResultDoc.WebExaminationResultDocRepository;
import com.blink.domain.hospital.Hospital;
import com.blink.domain.hospital.HospitalRepository;
import com.blink.domain.sendMailResultWeb.SendMailResultWebRepository;
import com.blink.domain.user.UserExaminationMetadataRepository;
import com.blink.domain.webfiles.WebFilesRepository;
import com.blink.web.admin.web.dto.WebFileResponseDto;
import com.blink.web.admin.web.dto.business.BusinessResponseDto;
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
	private final SendMailResultWebRepository sendMailResultWebRepository;
	private final WebExaminationResultDocRepository webExaminationResultDocRepository;
	private final WebFilesRepository webFilesRepository;

	public BusinessResponseDto getBusinessData(String searchText, String searchDate, Pageable pageable) {
		
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime date = LocalDate.parse(searchDate, formatter).atStartOfDay();
		
		Page<Long> hospitalIds = metadataRepository.findBusinessHospitalIds(searchText, date, pageable);
		
		List<Long> content = hospitalIds.getContent();
		
		LocalDate searchLocalDate = LocalDate.parse(searchDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		
		List<SingleBusinessResponseDto> businessList = new ArrayList<SingleBusinessResponseDto>();
		for(Long hospitalId: content) {
			Integer agreeYCount = metadataRepository.findAgreeYnCount(date, hospitalId, 0);
			Integer agreeNCount = metadataRepository.findAgreeYnCount(date, hospitalId, 1);
			Hospital hospital = hospitalRepository.findById(hospitalId).orElseThrow(() -> new IllegalArgumentException("No such hospital"));
			String hospitalName = hospital.getDisplayName();
			Integer agreeSendYn = hospital.getAgreeSendYn();
			String username = hospital.getName();
			Integer sentCount = sendMailResultWebRepository.findSentCountBySentDateAndHospital(searchLocalDate, hospital);
			
			SingleBusinessResponseDto business = new SingleBusinessResponseDto(hospitalId, searchDate, hospitalName, agreeYCount, agreeNCount, agreeSendYn, sentCount, username);
			businessList.add(business);
		}
		
		Page<SingleBusinessResponseDto> businessPage = new PageImpl<>(businessList, pageable, content.size());
		
		Integer totalAgreeYCount = metadataRepository.findTotalAgreeYnCount(date, 0);
		Integer totalAgreeNCount = metadataRepository.findTotalAgreeYnCount(date, 1);
		Integer totalSentCount = sendMailResultWebRepository.findTotalSentCount(searchLocalDate);
		BusinessResponseDto result = new BusinessResponseDto(totalAgreeYCount, totalAgreeNCount, totalSentCount, businessPage);
		return result;
	}

	public HospitalBusinessResponseDto getDashboardDataForHospital(Long hospitalId, String searchText, String searchDate,
			Pageable pageable) {
		
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime date = LocalDate.parse(searchDate, formatter).atStartOfDay();
		
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
