package com.blink.web.hospital;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blink.common.CommonResponse;
import com.blink.common.CommonResultCode;
import com.blink.service.WebConsentFormService;
import com.blink.web.hospital.dto.consentForm.ConsentFormResponseDto;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController("hospitalWebConsentFormController")
@RequestMapping("/hospital/web/consentForms")
public class WebConsentFormController {

	private final WebConsentFormService webConsentFormService;

	@ApiOperation(value = "동의서 등록")
	@PostMapping("/{hospitalId}")
	public ResponseEntity<CommonResponse> registerConsentForm(@PathVariable("hospitalId") Long hospitalId,
			@RequestParam("consentYear") String consentYear, @RequestParam("consentMonth") String consentMonth,
			@RequestParam("count") Long count, @RequestParam("file") MultipartFile[] files) {

		webConsentFormService.registerConsentFormForHospital(hospitalId, consentYear, consentMonth, count, files);
		return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS));
	}

	@ApiOperation(value = "해당 병원의 동의서 리스트 가져오기")
	@GetMapping("/{hospitalId}")
	ResponseEntity<CommonResponse> getConsentFormsForHospital(@PathVariable("hospitalId") Long hospitalId,
			@RequestParam("searchText") Optional<String> searchText,
			@RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
			@RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
			Pageable pageable) {

		ConsentFormResponseDto result = webConsentFormService.getConsentFormsForHospitalSelf(hospitalId,
				searchText.orElse("_"), startDate, endDate, pageable);

		return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS, result));
	}
}
