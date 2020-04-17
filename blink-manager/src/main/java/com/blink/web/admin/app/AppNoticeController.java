package com.blink.web.admin.app;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blink.common.CommonResponse;
import com.blink.common.CommonResultCode;
import com.blink.domain.app.notice.AppNotice;
import com.blink.service.app.AppNoticeService;
import com.blink.web.admin.app.dto.AppNoticeSaveRequestDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/app/notices")
public class AppNoticeController {

	private final AppNoticeService appNoticeService;

	@GetMapping
	public ResponseEntity<CommonResponse> getNoticeList(@RequestParam("title") Optional<String> title,
			@RequestParam("period") Optional<Integer> period, Pageable pageable) {
		Page<AppNotice> noticeList = appNoticeService.getNoticeList(title.orElse("_"), period.orElse(1), pageable);

		return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS, noticeList));
	}
	
	@PostMapping
	public ResponseEntity<CommonResponse> postNotice(@RequestBody AppNoticeSaveRequestDto requestDto) {
		
		appNoticeService.save(requestDto);
		return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS));
	}
}
