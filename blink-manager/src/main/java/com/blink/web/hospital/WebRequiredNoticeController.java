package com.blink.web.hospital;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blink.common.CommonResponse;
import com.blink.common.CommonResultCode;
import com.blink.domain.requiredNotice.WebRequiredNotice;
import com.blink.service.WebRequiredNoticeService;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController("hospitalWebRequiredNoticeController")
@RequestMapping("/hospital/web/requiredNotices")
public class WebRequiredNoticeController {

	private final WebRequiredNoticeService webRequiredNoticeService;
	
	@ApiOperation(value = "필수고지사항 조회")
	@GetMapping
	public ResponseEntity<CommonResponse> getRecentRequiredNotice() {
		
		Optional<WebRequiredNotice> webRequiredNotice =  webRequiredNoticeService.getRecentRequiredNotice();
		if (webRequiredNotice.isPresent()) {
			return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS, webRequiredNotice.get()));
		} else {
			return ResponseEntity.ok(new CommonResponse(CommonResultCode.NO_REQUIRED_NOTICE_FOUND));
		}
	}
}
