package com.blink.web.hospital;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blink.common.CommonResponse;
import com.blink.common.CommonResultCode;
import com.blink.domain.hospitalStatistics.HospitalStatistics;
import com.blink.service.DashboardService;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/hospital/web/dashboard")
public class HospitalDashboardController {

	private final DashboardService dashboardService;
	
	@ApiOperation(value = "대시보드 데이터 가져오기")
	@GetMapping("/{hospitalId}")
	public ResponseEntity<CommonResponse> getDashboardData(@PathVariable("hospitalId") Long hospitalId) {
		
		HospitalStatistics statistics = dashboardService.getDashboardDataWithHospital(hospitalId);
		return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS, statistics));
	}
}
