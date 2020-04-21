package com.blink.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blink.domain.hospital.HospitalRepository;
import com.blink.domain.signage.WebDigitalSignage;
import com.blink.domain.signage.WebDigitalSignageRepository;
import com.blink.domain.webfiles.WebFilesRepository;
import com.blink.enumeration.FileUploadUserType;
import com.blink.enumeration.SearchPeriod;
import com.blink.enumeration.SignageType;
import com.blink.util.CommonUtils;
import com.blink.util.FileUploadUtils;
import com.blink.web.admin.web.dto.WebDigitalSignageAdminResponseDto;
import com.blink.web.admin.web.dto.WebFileResponseDto;
import com.blink.web.hospital.dto.WebDigitalSignageResponseDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class WebDigitalSignageService {
	
	private final WebDigitalSignageRepository webDigitalSignageRepository;
	private final WebFilesRepository webFilesRepository;
	private final FileUploadUtils fileUploadUtils;
	private final HospitalRepository hospitalRepository;

	// ------------------ HOSPITAL ------------------
	public void registerQuestion(Long hospitalId, SignageType signageType, int signageNoticeStyle, String title, String questionContent,
			MultipartFile[] files) {
		
		String questionGroupId = fileUploadUtils.upload(files, "webDigitalSignageFiles", FileUploadUserType.WEB, hospitalId, null);
		
		WebDigitalSignage webDigitalSignage = WebDigitalSignage.builder() //
			.hospital(hospitalRepository.findById(hospitalId).get()) //
			.signageType(signageType) //
			.signageNoticeStyle(signageNoticeStyle) //
			.title(title) //
			.questionContent(questionContent) //
			.build();
		
		if (questionGroupId != null) {
			webDigitalSignage.setQuestionGroupId(questionGroupId);
		}
		
		webDigitalSignageRepository.save(webDigitalSignage);
	}

	public Page<WebDigitalSignageResponseDto> getHospitalDigitalSignageList(Long hospitalId, String title, SearchPeriod period,
			Pageable pageable) {
		
		LocalDateTime time = CommonUtils.getSearchPeriod(period);
		
		Page<WebDigitalSignageResponseDto> list = webDigitalSignageRepository.findByTitleAndPeriodWithHospital(title, time, hospitalId, pageable);
		
		List<WebDigitalSignageResponseDto> content = list.getContent();
		
		for(WebDigitalSignageResponseDto dto: content) {
			String questionGroupId = dto.getQuestionGroupId();
			if (questionGroupId != null) {
				List<WebFileResponseDto> questionFiles = webFilesRepository.findByGroupId(questionGroupId);
				dto.setQuestionFiles(questionFiles);
			}

			String answerGroupId = dto.getAnswerGroupId();
			if (answerGroupId != null) {
				List<WebFileResponseDto> answerFiles = webFilesRepository.findByGroupId(answerGroupId);
				dto.setAnswerFiles(answerFiles);
			}
		}
		
		return list;
	}
	
	// ------------------ ADMIN ------------------
	public void registerAnswer(Long digitalSignageId, String answerContent, MultipartFile[] files) {
		
		String answerGroupId = fileUploadUtils.upload(files, "webDigitalSignageFiles", FileUploadUserType.WEB, 0L, null);
		
		WebDigitalSignage digitalSignage = webDigitalSignageRepository.findById(digitalSignageId).orElseThrow(() -> new IllegalArgumentException("No such digital signage"));
		
		if (answerGroupId != null) {
			digitalSignage.setAnswerGroupId(answerGroupId);
		}
		
		digitalSignage.completeAnswer(answerContent);
	}

	public WebDigitalSignageAdminResponseDto getAdminDigitalSignageInfo(String title, SearchPeriod period,
			Pageable pageable) {
		
		LocalDateTime time = CommonUtils.getSearchPeriod(period);
		
		Page<WebDigitalSignageResponseDto> list = webDigitalSignageRepository.findByTitleAndPeriodWithAdmin(title, time, pageable);
		List<WebDigitalSignageResponseDto> content = list.getContent();
		
		for(WebDigitalSignageResponseDto dto : content) {
			String questionGroupId = dto.getQuestionGroupId();
			if (questionGroupId != null) {
				List<WebFileResponseDto> questionFiles = webFilesRepository.findByGroupId(questionGroupId);
				dto.setQuestionFiles(questionFiles);
			}

			String answerGroupId = dto.getAnswerGroupId();
			if (answerGroupId != null) {
				List<WebFileResponseDto> answerFiles = webFilesRepository.findByGroupId(answerGroupId);
				dto.setAnswerFiles(answerFiles);
			}
		}
		
		long totalCount = list.getTotalElements();
		
		int waitingCount = webDigitalSignageRepository.findByAnswerYn(false);
		int completedCount = webDigitalSignageRepository.findByAnswerYn(true);
		
		WebDigitalSignageAdminResponseDto responseDto = new WebDigitalSignageAdminResponseDto(list, totalCount, waitingCount, completedCount);
		return responseDto;
	}

}
