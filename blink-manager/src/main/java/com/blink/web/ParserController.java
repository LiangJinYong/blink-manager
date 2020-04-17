package com.blink.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blink.common.CommonResponse;
import com.blink.common.CommonResultCode;
import com.blink.service.ParserService;
import com.blink.web.admin.web.dto.UserParserRequestDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/parser")
public class ParserController {

	private final ParserService parserService;
	
	@PostMapping("/registUser")
	public ResponseEntity<CommonResponse> registUser(@RequestBody List<UserParserRequestDto> userList) {
		try {
			
			parserService.save(userList);
			return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS));
		} catch (Exception e) {
			return ResponseEntity.ok(new CommonResponse(CommonResultCode.INTERNAL_ERROR));
		}
	}
}
