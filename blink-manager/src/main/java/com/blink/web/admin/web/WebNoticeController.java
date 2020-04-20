package com.blink.web.admin.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blink.common.CommonResponse;
import com.blink.common.CommonResultCode;
import com.blink.service.WebNoticeService;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController("adminWebNoticeController")
@RequestMapping("/admin/web/notices")
public class WebNoticeController {

	private final WebNoticeService webNoticeService;

	@ApiOperation(value = "공지사항 등록")
	@PostMapping
	public ResponseEntity<CommonResponse> postNotice(@RequestParam("title") String title,
			@RequestParam("description") String description,
			@RequestParam(name = "file", required = false) MultipartFile[] files) {

		webNoticeService.save(title, description, files);
		return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS));
	}
}
