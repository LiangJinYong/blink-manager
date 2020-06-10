package com.blink.web.admin.web;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blink.common.CommonResponse;
import com.blink.common.CommonResultCode;
import com.blink.service.AgreeUserService;
import com.blink.web.admin.web.dto.agreeUserList.AgreeUserResponseDto;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController("adminAgreeUserController")
@RequestMapping("/admin/web/agreeUserLists")
public class AgreeUserController {

	private final AgreeUserService agreeUserService;

	@ApiOperation("동의자 리스트 전체 조회")
	@GetMapping
	public ResponseEntity<CommonResponse> getAgreeUserInfo(@RequestParam("searchText") Optional<String> searchText,
			@RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
			@RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate, Pageable pageable) {
		
		AgreeUserResponseDto responseDto = agreeUserService.getAdminAgreeUserInfo(searchText.orElse("_"),
				startDate, endDate, pageable);
		
		return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS, responseDto));
	}
}
