package com.blink.web.admin.app;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blink.common.CommonResponse;
import com.blink.common.CommonResultCode;
import com.blink.domain.app.notice.AppNotice;
import com.blink.enumeration.SearchPeriod;
import com.blink.service.app.AppNoticeService;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/app/notices")
public class AppNoticeController {

	private final AppNoticeService appNoticeService;

	@ApiOperation(value="공지사항 - 전체 조회")
	@GetMapping
	public ResponseEntity<CommonResponse> getNoticeList(@RequestParam("title") Optional<String> title,
			@RequestParam(name = "period", defaultValue = "ONEMONTH") Optional<SearchPeriod> period, Pageable pageable) {
		Page<AppNotice> noticeList = appNoticeService.getNoticeList(title.orElse("_"), period.orElse(SearchPeriod.ONEMONTH), pageable);

		return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS, noticeList));
	}
	
	@ApiOperation(value="공지사항 - 등록")
	@PostMapping
	public ResponseEntity<CommonResponse> postNotice(@RequestParam("title") String title,
			@RequestParam("description") String description) {
		
		appNoticeService.save(title, description);
		return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS));
	}
}
