package com.blink.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blink.domain.admin.Admin;
import com.blink.domain.admin.AdminRepository;
import com.blink.domain.hospital.HospitalRepository;
import com.blink.domain.qna.WebQna;
import com.blink.domain.qna.WebQnaRepository;
import com.blink.domain.webfiles.WebFilesRepository;
import com.blink.enumeration.FileUploadUserType;
import com.blink.enumeration.QuestionType;
import com.blink.enumeration.SearchPeriod;
import com.blink.util.CommonUtils;
import com.blink.util.FileUploadUtils;
import com.blink.web.admin.web.dto.WebFileResponseDto;
import com.blink.web.admin.web.dto.WebQnaAdminResponseDto;
import com.blink.web.hospital.dto.WebQnaResponseDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class WebQnaService {

	private final WebQnaRepository webQnaRepository;
	private final WebFilesRepository webFilesRepository;
	private final FileUploadUtils fileUploadUtils;
	private final HospitalRepository hospitalRepository;

	// ------------------ HOSPITAL ------------------
	public void registerQuestion(Long hospitalId, QuestionType questionType, String title, String questionContent, MultipartFile[] files) {
		
		String questionGroupId = fileUploadUtils.upload(files, "webQnaFiles", FileUploadUserType.WEB, hospitalId, null);

		WebQna webQna = WebQna.builder() //
				.hospital(hospitalRepository.findById(hospitalId).get()) //
				.questionType(questionType) //
				.title(title) //
				.questionContent(questionContent) //
				.build();

		if (questionGroupId != null) {
			webQna.setQuestionGroupId(questionGroupId);
		}

		webQnaRepository.save(webQna);
	}

	public Page<WebQnaResponseDto> getHospitalQnaList(Long hospitalId, String searchText, SearchPeriod period, Pageable pageable) {
		
		LocalDateTime time = CommonUtils.getSearchPeriod(period);

		Page<WebQnaResponseDto> list = webQnaRepository.findByTitleAndPeriodWithHospital(searchText, time, hospitalId, pageable);

		List<WebQnaResponseDto> content = list.getContent();

		for (WebQnaResponseDto dto : content) {
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
	public void registerAnswer(Long qnaId, String answerContent, MultipartFile[] files) {
		
		String answerGroupId = fileUploadUtils.upload(files, "webQnaFiles", FileUploadUserType.WEB, 0L, null);
		
		WebQna webQna = webQnaRepository.findById(qnaId).orElseThrow(() -> new IllegalArgumentException("No such qna"));
		
		if (answerGroupId != null) {
			webQna.setAnswerGroupId(answerGroupId);
		}
		
		webQna.completeAnswer(answerContent);
	}

	public WebQnaAdminResponseDto getAdminQnaInfo(String searchText, SearchPeriod period, Pageable pageable) {
		
		LocalDateTime time = CommonUtils.getSearchPeriod(period);
		
		Page<WebQnaResponseDto> list = webQnaRepository.findByTitleAndPeriodWithAdmin(searchText, time, pageable);
		List<WebQnaResponseDto> content = list.getContent();

		for (WebQnaResponseDto dto : content) {
			
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
		
		int waitingCount = webQnaRepository.findByAnswerYn(false);
		int completedCount = webQnaRepository.findByAnswerYn(true);
		
		QuestionType questionTypeMost = webQnaRepository.findByQuestionTypeMost();
		
		WebQnaAdminResponseDto responseDto = new WebQnaAdminResponseDto(list, totalCount, waitingCount, completedCount, questionTypeMost);
		
		return responseDto;
	}
	
	
}
