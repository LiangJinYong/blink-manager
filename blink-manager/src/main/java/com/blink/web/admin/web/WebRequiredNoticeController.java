package com.blink.web.admin.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blink.common.CommonResponse;
import com.blink.common.CommonResultCode;
import com.blink.service.WebRequiredNoticeService;
import com.blink.web.admin.web.dto.WebRequiredNoticeSaveRequestDto;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController("adminWebRequiredNoticeController")
@RequestMapping("/admin/web/requiredNotices")
public class WebRequiredNoticeController {

	private final WebRequiredNoticeService webRequiredNoticeService;

	@ApiOperation(value = "필수고지사항 수정하기")
	@PostMapping
	public ResponseEntity<CommonResponse> modifyRequiredNotice(@RequestBody WebRequiredNoticeSaveRequestDto dto) {
		
		webRequiredNoticeService.insertRequiredNotice(dto);
		return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS));
	}
}
