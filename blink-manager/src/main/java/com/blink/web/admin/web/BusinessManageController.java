package com.blink.web.admin.web;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blink.common.CommonResponse;
import com.blink.common.CommonResultCode;
import com.blink.service.BusinessManageService;
import com.blink.web.admin.web.dto.business.HospitalBusinessResponseDto;
import com.blink.web.admin.web.dto.business.SingleBusinessResponseDto;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/web/business")
public class BusinessManageController {

	private final BusinessManageService businessManageService;
	
	@ApiOperation(value = "업무관리 데이터 가져오기")
	@GetMapping
	public ResponseEntity<CommonResponse> getBusinessData(@RequestParam("searchText") Optional<String> searchText,
			@RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
			@RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate, Pageable pageable) {
		
		Page<SingleBusinessResponseDto> result = businessManageService.getBusinessData(searchText.orElse("_"),
				startDate, endDate, pageable);
		return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS, result));
	}
	
	@ApiOperation(value = "해당병원의 업무관리 데이터 가져오기")
	@GetMapping("/{hospitalId}")
	public ResponseEntity<CommonResponse> getBusinessDataForHospital(@PathVariable("hospitalId") Long hospitalId, @RequestParam("searchText") Optional<String> searchText,
			@RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
			@RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate, Pageable pageable) {
		
		HospitalBusinessResponseDto result = businessManageService.getBusinessDataForHospital(hospitalId, searchText.orElse("_"),
				startDate, endDate, pageable);
		return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS, result));
	}
}
