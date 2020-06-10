package com.blink.web.hospital;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blink.common.CommonResponse;
import com.blink.common.CommonResultCode;
import com.blink.service.WebNoticeService;
import com.blink.web.admin.web.dto.WebNoticeResponseDto;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController("hospitalWebNoticeController")
@RequestMapping("/hospital/web/notices")
public class WebNoticeController {

	private final WebNoticeService webNoticeService;
	
	@ApiOperation(notes = "검색 기간: ONEDAY: 당일, ONEWEEK: 1주일, ONEMONTH: 1개월, THREEMONTH: 3개월, SIXMONTH: 6개월, ONEYEAR:1년", value = "공지사항 조회")
	@GetMapping
	public ResponseEntity<CommonResponse> getNoticeList(@RequestParam("searchText") Optional<String> searchText,
			@RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
			@RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
			Pageable pageable) {
		Page<WebNoticeResponseDto> noticeList = webNoticeService.getNoticeList(searchText.orElse("_"),
				startDate, endDate, pageable);
		return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS, noticeList));
	}
}
