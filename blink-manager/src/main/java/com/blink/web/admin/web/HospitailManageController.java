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
import com.blink.service.HospitalService;
import com.blink.web.admin.web.dto.hospital.HospitalResponseDto;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@RestController("adminHospitailManageController")
@RequestMapping("/admin/web/hospitals")
public class HospitailManageController {

	private final HospitalService hospitalService;

	@ApiOperation(value = "병원관리 - 전체 병원정보 가져오기")
	@GetMapping
	public ResponseEntity<CommonResponse> getHospitalListInfo(@RequestParam("searchText") Optional<String> searchText,
			@RequestParam(name = "period", defaultValue = "ONEMONTH") Optional<SearchPeriod> period,
			Pageable pageable) {

		HospitalResponseDto result = hospitalService.getHospitalList(searchText.orElse("_"), period.orElse(SearchPeriod.ONEMONTH), pageable);
		return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS, result));
	}

}
