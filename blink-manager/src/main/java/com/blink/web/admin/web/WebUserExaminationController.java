package com.blink.web.admin.web;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blink.common.CommonResponse;
import com.blink.common.CommonResultCode;
import com.blink.enumeration.SearchPeriod;
import com.blink.service.WebUserExaminationService;
import com.blink.web.hospital.dto.userExamination.UserExaminationResponseDto;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController("adminWebUserExaminationController")
@RequestMapping("/admin/web/userExamination")
public class WebUserExaminationController {
	
	private final WebUserExaminationService webUserExaminationService;
	
	@ApiOperation(value = "전체 수검자 데이터")
	@GetMapping
	public ResponseEntity<CommonResponse> getUserExamination(@RequestParam("searchText") Optional<String> searchText,
			@RequestParam(name = "period", defaultValue = "ONEYEAR") Optional<SearchPeriod> period,
			Pageable pageable) {
		
		UserExaminationResponseDto result = webUserExaminationService.getUserExaminationWithAdmin(searchText.orElse("_"),
				period.orElse(SearchPeriod.ONEYEAR), pageable);
		return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS, result));
	}
	
	@ApiOperation(value = "수검자 데이터 동의서 동의여부 변경")
	@PutMapping("/{userExaminationId}")
	public ResponseEntity<CommonResponse> setConsentFormExistYn(@PathVariable("userExaminationId") Long userExaminationId, @RequestParam("isConsentFormExistYn") Integer isConsentFormExistYn) {
		
		webUserExaminationService.setConsentFormExistYn(userExaminationId, isConsentFormExistYn);
		
		return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS));
	}
	
	
}
