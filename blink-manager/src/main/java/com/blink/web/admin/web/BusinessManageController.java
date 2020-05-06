package com.blink.web.admin.web;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blink.common.CommonResponse;
import com.blink.common.CommonResultCode;
import com.blink.service.BusinessManageService;
import com.blink.web.admin.web.dto.business.BusinessResponseDto;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/web/business")
public class BusinessManageController {

	private final BusinessManageService businessManageService;
	
	@ApiOperation(value = "업무관리 데이터 가져오기")
	@GetMapping
	public ResponseEntity<CommonResponse> getDashboardData(@RequestParam("searchText") Optional<String> searchText,
			@RequestParam(name = "searchDate") Optional<String> searchDate, Pageable pageable) {
		
		LocalDate today = LocalDate.now();
		
		BusinessResponseDto result = businessManageService.getBusinessData(searchText.orElse("_"),
				searchDate.orElse(today.toString()), pageable);
		return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS, result));
	}
}
