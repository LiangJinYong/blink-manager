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
import com.blink.service.ExaminationPresentService;
import com.blink.web.hospital.dto.examinatinPresent.ExaminatinPresentResponseDto;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/hospital/web/examinatinPresent")
public class ExaminationPresentController {

	private final ExaminationPresentService examinatinPresentService;

	@ApiOperation(value = "병원 검진현황")
	@GetMapping("/{hospitalId}")
	public ResponseEntity<CommonResponse> getExaminationPresent(@PathVariable("hospitalId") Long hospitalId,
			@RequestParam("searchText") Optional<String> searchText,
			@RequestParam(name = "period", defaultValue = "ONEYEAR") Optional<SearchPeriod> period,
			Pageable pageable) {
		
		ExaminatinPresentResponseDto result = examinatinPresentService.getExaminationPresent(hospitalId, searchText.orElse("_"),
				period.orElse(SearchPeriod.ONEMONTH), pageable); 
		
		return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS, result));

	}

}
