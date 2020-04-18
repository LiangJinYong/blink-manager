package com.blink.web.hospital;

import java.security.Principal;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blink.common.CommonResponse;
import com.blink.common.CommonResultCode;
import com.blink.enumeration.SearchPeriod;
import com.blink.enumeration.SignageType;
import com.blink.service.WebDigitalSignageService;
import com.blink.web.hospital.dto.WebDigitalSignageResponseDto;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController("hospitalWebDigitalSignageController")
@RequestMapping("/hospital/web/digitalSignages")
public class WebDigitalSignageController {

	private final WebDigitalSignageService webDigitalSignageService;

	@ApiOperation(value = "사아니지 - 질문 등록")
	@PostMapping("/question")
	public ResponseEntity<CommonResponse> registerQuestion(@RequestParam("signageType") SignageType signageType,
			@RequestParam(name = "signageNoticeStyle", required = false) int signageNoticeStyle,
			@RequestParam("title") String title, @RequestParam("questionContent") String questionContent,
			@RequestParam(name = "file", required = false) MultipartFile[] files, Principal principal) {
		String username = principal.getName();
		webDigitalSignageService.registerQuestion(signageType, signageNoticeStyle, title, questionContent, files,
				username);
		return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS));
	}
	
	@ApiOperation(value = "사아니지 - 해당 병원 질문 조회")
	@GetMapping
	public ResponseEntity<CommonResponse> getQnaList(@RequestParam("title") Optional<String> title,
			@RequestParam(name = "period", defaultValue = "ONEMONTH") Optional<SearchPeriod> period, Pageable pageable,
			Principal principal) {
		String username = principal.getName();
		Page<WebDigitalSignageResponseDto> webDigitalSignageList = webDigitalSignageService.getHospitalDigitalSignageList(title.orElse("_"),
				period.orElse(SearchPeriod.ONEMONTH), pageable, username);

		return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS, webDigitalSignageList));
	}
}