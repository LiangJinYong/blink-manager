package com.blink.web.admin.web;

import java.time.LocalDate;
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
import com.blink.enumeration.ReceiveType;
import com.blink.enumeration.SearchPeriod;
import com.blink.service.WebConsentFormService;
import com.blink.web.admin.web.dto.consentForm.ConsentFormResponseDto;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController("adminWebConsentFormController")
@RequestMapping("/admin/web/consentForms")
public class WebConsentFormController {

	private final WebConsentFormService webConsentFormService;

	@ApiOperation(value = "동의서 등록")
	@PostMapping
	public ResponseEntity<CommonResponse> registerConsentForm(
			@DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam("receiveDate") LocalDate receiveDate,
			@RequestParam("receiveType") ReceiveType receiveType,
			@RequestParam(name = "receiveTypeText", required = false) String receiveTypeText,
			@RequestParam("consentYear") String consentYear, @RequestParam("consentMonth") String consentMonth,
			@RequestParam("count") Long count, @RequestParam("file") MultipartFile[] files) {

		webConsentFormService.registerConsentForm(receiveDate, receiveType, receiveTypeText, consentYear, consentMonth,
				count, files);
		return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS));
	}

	@ApiOperation(value = "동의서 리스트 가져오기")
	@GetMapping
	public ResponseEntity<CommonResponse> getConsentForms(@RequestParam("searchText") Optional<String> searchText,
			@RequestParam(name = "period", defaultValue = "ONEMONTH") Optional<SearchPeriod> period,
			Pageable pageable) {

		ConsentFormResponseDto result = webConsentFormService.getConsentForms(searchText.orElse("_"),
				period.orElse(SearchPeriod.ONEMONTH), pageable);

		return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS, result));
	}

	@ApiOperation(value = "해당 병원의 동의서 리스트 가져오기")
	@GetMapping("/{hospitalId}")
	ResponseEntity<CommonResponse> getConsentFormsForHospital(@PathVariable("hospitalId") Long hospitalId,
			@RequestParam("searchText") Optional<String> searchText,
			@RequestParam(name = "period", defaultValue = "ONEMONTH") Optional<SearchPeriod> period,
			Pageable pageable) {

		ConsentFormResponseDto result = webConsentFormService.getConsentFormsForHospital(hospitalId,
				searchText.orElse("_"), period.orElse(SearchPeriod.ONEMONTH), pageable);

		return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS, result));
	}
}
