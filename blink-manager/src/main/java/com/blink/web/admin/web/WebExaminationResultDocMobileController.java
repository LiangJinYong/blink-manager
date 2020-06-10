package com.blink.web.admin.web;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blink.common.CommonResponse;
import com.blink.common.CommonResultCode;
import com.blink.service.WebExaminationResultDocMobileService;
import com.blink.web.admin.web.dto.examinationResultDocMobile.WebExaminationResultDocMobileResponseDto;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController("WebExaminationResultDocMobileController")
@RequestMapping("/admin/web/examinationResultDocs")
public class WebExaminationResultDocMobileController {

	private final WebExaminationResultDocMobileService mobileService;

	@ApiOperation(value = "모바일 검진결과지 조회")
	@GetMapping
	public ResponseEntity<CommonResponse> getExaminationResultDocMobileList(
			@RequestParam("searchText") Optional<String> searchText,
			@RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
			@RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
			Pageable pageable) {
		WebExaminationResultDocMobileResponseDto result = mobileService.getExaminationResultDocMobileList(
				searchText.orElse("_"), startDate, endDate, pageable);
		return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS, result));
	}
}
