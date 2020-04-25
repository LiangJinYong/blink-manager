package com.blink.web.hospital;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blink.common.CommonResponse;
import com.blink.common.CommonResultCode;
import com.blink.enumeration.SearchPeriod;
import com.blink.service.SendMailResultWebService;
import com.blink.web.hospital.dto.sendMailResultWeb.SendMailResultWebResponseDto;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/hospital/web/sendMailResultWeb")
public class SendMailResultWebController {

	private final SendMailResultWebService sendMailResultWebService;

	@ApiOperation(value = "발송 확인")
	@GetMapping("/{hospitalId}")
	public ResponseEntity<CommonResponse> getSendMailResultWeb(@PathVariable("hospitalId") Long hospitalId,
			@RequestParam("searchText") Optional<String> searchText,
			@RequestParam(name = "period", defaultValue = "ONEYEAR") Optional<SearchPeriod> period,
			Pageable pageable) {

		Page<SendMailResultWebResponseDto> result = sendMailResultWebService.getSendMailResultWeb(searchText.orElse("_"),
				period.orElse(SearchPeriod.ONEYEAR), pageable, hospitalId);
		
		return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS, result));
	}
}
