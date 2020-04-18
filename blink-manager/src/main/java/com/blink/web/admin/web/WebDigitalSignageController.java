package com.blink.web.admin.web;

import java.security.Principal;
import java.util.Optional;

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
import com.blink.service.WebDigitalSignageService;
import com.blink.web.admin.web.dto.WebDigitalSignageAdminResponseDto;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController("adminWebDigitalSignageController")
@RequestMapping("/admin/web/digitalSignages")
public class WebDigitalSignageController {

	private final WebDigitalSignageService webDigitalSignageService;
	
	@ApiOperation(value = "사이니지 - 질문 답변")
	@PostMapping("/answer")
	public ResponseEntity<CommonResponse> registerAnswer(@RequestParam("digitalSignageId") Long digitalSignageId,
			@RequestParam("answerContent") String answerContent,
			@RequestParam(name = "file", required = false) MultipartFile[] files, Principal principal) {

		String username = principal.getName();
		webDigitalSignageService.registerAnswer(digitalSignageId, answerContent, files, username);
		return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS));
	}
	
	@ApiOperation(value = "사아니지 - 전체 조회")
	@GetMapping
	public ResponseEntity<CommonResponse> getQnaList(@RequestParam("title") Optional<String> title,
			@RequestParam(name = "period", defaultValue = "ONEMONTH") Optional<SearchPeriod> period, Pageable pageable,
			Principal principal) {

		String username = principal.getName();
		WebDigitalSignageAdminResponseDto webDigitalSignageAdmin = webDigitalSignageService.getAdminDigitalSignageInfo(title.orElse("_"),
				period.orElse(SearchPeriod.ONEMONTH), pageable, username);

		return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS, webDigitalSignageAdmin));
	}
}
