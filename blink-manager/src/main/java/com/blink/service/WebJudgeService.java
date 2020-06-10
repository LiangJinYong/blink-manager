package com.blink.service;

import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.blink.domain.judge.WebJudge;
import com.blink.domain.judge.WebJudgeRepository;
import com.blink.enumeration.JudgeStatus;
import com.blink.web.admin.web.dto.SingleWebJudgeRecordDto;
import com.blink.web.admin.web.dto.WebJudgeDetailResponseDto;
import com.blink.web.admin.web.dto.WebJudgeResponseDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class WebJudgeService {

	private final WebJudgeRepository webJudgeRepository;

	public WebJudgeResponseDto getJudgeInfo(String searchText, Date startDate, Date endDate, Pageable pageable) {

		Page<SingleWebJudgeRecordDto> webJudgeList = webJudgeRepository.findBySearchTextAndPeriod(searchText, startDate,
				endDate, pageable);

		Long totalJudgeCount = webJudgeList.getTotalElements();
		long waitingJudgeCount = webJudgeRepository.findCountByJudgeStatus(JudgeStatus.WAITING);
		long approvedJudgeCount = webJudgeRepository.findCountByJudgeStatus(JudgeStatus.APPROVED);
		long deniedJudgeCount = webJudgeRepository.findCountByJudgeStatus(JudgeStatus.DENIED);

		WebJudgeResponseDto responseDto = new WebJudgeResponseDto(totalJudgeCount, waitingJudgeCount,
				approvedJudgeCount, deniedJudgeCount, webJudgeList);

		return responseDto;
	}

	public Optional<WebJudgeDetailResponseDto> getJudgeDetail(Long webJudgeId) {
		Optional<WebJudgeDetailResponseDto> responseDto = webJudgeRepository.findJudgeDetailById(webJudgeId);
		return responseDto;
	}

	public void denyUser(Long webJudgeId, String rejectMsg) {
		WebJudge webJudge = webJudgeRepository.findById(webJudgeId)
				.orElseThrow(() -> new IllegalArgumentException("No such judge data"));
		webJudge.changeJudgeStatus(JudgeStatus.DENIED, rejectMsg);
	}

	public void approveUser(Long webJudgeId) {
		WebJudge webJudge = webJudgeRepository.findById(webJudgeId)
				.orElseThrow(() -> new IllegalArgumentException("No such judge data"));

		webJudge.changeJudgeStatus(JudgeStatus.APPROVED, null);
	}

	public void accomplishUser(Long webJudgeId) {
		WebJudge webJudge = webJudgeRepository.findById(webJudgeId)
				.orElseThrow(() -> new IllegalArgumentException("No such judge data"));

		webJudge.changeJudgeStatus(JudgeStatus.ACCOMPLISHED, null);

		webJudge.getHospital().getAdmin().setAccountStatus(true);
	}

}
