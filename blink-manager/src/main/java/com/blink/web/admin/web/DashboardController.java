package com.blink.web.admin.web;

import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blink.common.CommonResponse;
import com.blink.common.CommonResultCode;
import com.blink.enumeration.SearchPeriod;
import com.blink.service.DashboardService;
import com.blink.web.admin.web.dto.dashboard.DashboardResponseDto;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/web/dashboard")
public class DashboardController {

	private final DashboardService dashboardService;
	
	@ApiOperation(value = "대시보드 데이터 가져오기")
	@GetMapping
	public ResponseEntity<CommonResponse> getDashboardData(@RequestParam("searchText") Optional<String> searchText,
			@RequestParam(name = "period", defaultValue = "ONEYEAR") Optional<SearchPeriod> period, Pageable pageable) {
		
		DashboardResponseDto result = dashboardService.getDashboardData(searchText.orElse("_"),
				period.orElse(SearchPeriod.ONEYEAR), pageable);
		return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS, result));
	}
	
	@ApiOperation(value = "고객센터, 디지털 사이니지, 제휴문의에 답변하는 않는 기록이 있는지 표시")
	@GetMapping("/markUnprocessed")
	public ResponseEntity<CommonResponse> markUnprocessed() {
		
		Map<String, Boolean> result = dashboardService.getUnprocessedData();
		return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS, result));
	}
	
}
