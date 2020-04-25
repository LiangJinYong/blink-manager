package com.blink.web.admin.web;

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

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/web/dashboard")
public class DashboardController {

	private final DashboardService dashboardService;
	
	@GetMapping
	public ResponseEntity<CommonResponse> getDashboardData(@RequestParam("searchText") Optional<String> searchText,
			@RequestParam(name = "period", defaultValue = "ONEMONTH") Optional<SearchPeriod> period,
			Pageable pageable) {
		
		dashboardService.getDashboardData(searchText.orElse("_"), period.orElse(SearchPeriod.ONEMONTH), pageable);
		return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS));
	}
}
