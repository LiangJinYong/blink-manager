package com.blink.web.hospital;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
			@RequestParam(name = "period", defaultValue = "ONEYEAR") Optional<SearchPeriod> period, Pageable pageable) {

		Page<SendMailResultWebResponseDto> result = sendMailResultWebService.getSendMailResultWeb(
				searchText.orElse("_"), period.orElse(SearchPeriod.ONEYEAR), pageable, hospitalId);

		return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS, result));
	}

	@ApiOperation(value = "발송 관리 - 등록화면 진입")
	@GetMapping("/addUI/{hospitalId}")
	public ResponseEntity<CommonResponse> addUI(@PathVariable("hospitalId") Long hospitalId) {

		List<Map<Long, String>> result = sendMailResultWebService.getPdfInfoList(hospitalId);
		return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS, result));
	}

	@ApiOperation(value = "발송 관리 - 등록")
	@PostMapping("/{hospitalId}")
	public ResponseEntity<CommonResponse> registerSendMailResultWeb(@PathVariable("hospitalId") Long hospitalId,
			@RequestParam("sentCount") Integer sentCount,
			@DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam("sentDate") LocalDate sentDate,
			@DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam("uploadDate") LocalDate uploadDate, @RequestParam("file") MultipartFile file) {

		sendMailResultWebService.registerSendMailResultWeb(hospitalId, sentCount, sentDate, uploadDate, file);
		return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS));
	}

	@ApiOperation(value = "발송 관리 - 등록")
	@DeleteMapping("/{sendMailResultWebId}")
	public ResponseEntity<CommonResponse> deleteSendMailResultWeb(
			@PathVariable("sendMailResultWebId") Long sendMailResultWebId) {
		sendMailResultWebService.deleteSendMailResultWeb(sendMailResultWebId);
		return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS));
	}
}
