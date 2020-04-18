package com.blink.web.admin.web;

import java.security.Principal;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blink.common.CommonResponse;
import com.blink.common.CommonResultCode;
import com.blink.enumeration.SearchPeriod;
import com.blink.service.WebNoticeService;
import com.blink.web.admin.web.dto.WebNoticeResponseDto;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/web/notices")
public class WebNoticeController {

	private final WebNoticeService webNoticeService;

	@ApiOperation(notes = "검색 기간: ONEDAY: 당일, ONEWEEK: 1주일, ONEMONTH: 1개월, THREEMONTH: 3개월, SIXMONTH: 6개월, ONEYEAR:1년", value = "공지사항 조회")
	@GetMapping
	public ResponseEntity<CommonResponse> getNoticeList(@RequestParam("title") Optional<String> title,
			@RequestParam(name = "period", defaultValue = "ONEMONTH") Optional<SearchPeriod> period,
			Pageable pageable) {
		Page<WebNoticeResponseDto> noticeList = webNoticeService.getNoticeList(title.orElse("_"),
				period.orElse(SearchPeriod.ONEMONTH), pageable);
		return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS, noticeList));
	}

	@ApiOperation(value = "공지사항 등록")
	@PostMapping
	public ResponseEntity<CommonResponse> postNotice(@RequestParam("title") String title,
			@RequestParam("description") String description,
			@RequestParam(name = "file", required = false) MultipartFile[] files, Principal principal) {

		String username = principal.getName();
		webNoticeService.save(title, description, files, username);
		return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS));
	}
}
