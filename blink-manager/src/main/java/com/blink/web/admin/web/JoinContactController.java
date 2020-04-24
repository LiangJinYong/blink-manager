package com.blink.web.admin.web;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blink.common.CommonResponse;
import com.blink.common.CommonResultCode;
import com.blink.enumeration.SearchPeriod;
import com.blink.enumeration.VisitAim;
import com.blink.service.JoinContactService;
import com.blink.web.admin.web.dto.joinConcact.JoinContactResponseDto;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/web/joinContacts")
public class JoinContactController {

	private final JoinContactService joinContactService;

	@ApiOperation(value = "제휴문의 - 리스트 가져오기")
	@GetMapping
	public ResponseEntity<CommonResponse> getJoinContacts(@RequestParam("searchText") Optional<String> searchText,
			@RequestParam(name = "period", defaultValue = "ONEMONTH") Optional<SearchPeriod> period,
			Pageable pageable) {
		JoinContactResponseDto result = joinContactService.getJoinContacts(searchText.orElse("_"),
				period.orElse(SearchPeriod.ONEMONTH), pageable);
		return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS, result));
	}

	@ApiOperation(value = "제휴문의 - 답변(처음 답변 혹은 답변수정 공통 사용)", notes = "방문시간 형식 yyyy-MM-ddTHH:mm:ss")	
	@PostMapping("/answer")
	public ResponseEntity<CommonResponse> saveAnswer(@RequestParam("joinContactId") Long joinContactId,
			@RequestParam("answerContent") String answerContent, @RequestParam("visitReserveYn") boolean visitReserveYn,
			@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
			@RequestParam("visitDate") LocalDateTime visitDate,
			@RequestParam("visitAim") VisitAim visitAim) {

		joinContactService.saveAnswer(joinContactId, answerContent, visitReserveYn, visitDate, visitAim);
		return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS));
	}
}
