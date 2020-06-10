package com.blink.web.hospital;

import java.util.Map;
import java.util.Optional;

import org.joda.time.LocalDate;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blink.common.CommonResponse;
import com.blink.common.CommonResultCode;
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
	public ResponseEntity<CommonResponse> getDashboardData(@PathVariable("hospitalId") Long hospitalId,
			@RequestParam("year") Optional<Integer> year, @RequestParam("month") Optional<Integer> month,
			Pageable pageable) {

		LocalDate today = LocalDate.now();
		Map<String, Object> result = dashboardService.getDashboardDataWithHospital(hospitalId,
				year.orElse(today.getYear()), month.orElse(today.getMonthOfYear()), pageable);
		return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS, result));
	}

	@ApiOperation(value = "고객센터, 디지털 사이니지에 대한 답변을 읽지않는 기록이 있는지 표시")
	@GetMapping("/markUnread/{hospitalId}")
	public ResponseEntity<CommonResponse> markUnread(@PathVariable("hospitalId") Long hospitalId) {

		Map<String, Boolean> result = dashboardService.getUnreadData(hospitalId);
		return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS, result));
	}
}
