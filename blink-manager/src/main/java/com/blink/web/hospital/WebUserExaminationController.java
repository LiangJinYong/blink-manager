package com.blink.web.hospital;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blink.common.CommonResponse;
import com.blink.common.CommonResultCode;
import com.blink.enumeration.SearchPeriod;
import com.blink.service.WebUserExaminationService;
import com.blink.web.hospital.dto.userExamination.UserExaminationResponseDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController("hospitalWebUserExaminationController")
@RequestMapping("/hospital/web/userExamination")
public class WebUserExaminationController {

	private final WebUserExaminationService webUserExaminationService;
	
	@GetMapping("/{hospitalId}")
	public ResponseEntity<CommonResponse> getUserExamination(@PathVariable("hospitalId") Long hospitalId,
			@RequestParam("searchText") Optional<String> searchText,
			@RequestParam(name = "period", defaultValue = "ONEMONTH") Optional<SearchPeriod> period,
			Pageable pageable) {
		
		UserExaminationResponseDto result = webUserExaminationService.getUserExamination(hospitalId, searchText.orElse("_"),
				period.orElse(SearchPeriod.ONEMONTH), pageable);
		return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS, result));
	}
}
