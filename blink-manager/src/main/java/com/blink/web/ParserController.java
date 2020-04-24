package com.blink.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
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
	
	@PostMapping("/registerUser")
	public ResponseEntity<CommonResponse> registUser(@RequestBody List<UserParserRequestDto> userList) {

		List<Map<String, Object>> result = parserService.save(userList);

		if (result.size() == 0) {
			return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS));
		}
		return ResponseEntity.ok(new CommonResponse(CommonResultCode.NO_USER_DATA, result));
	}
	
	@PostMapping("/registerExaminationData")
	public ResponseEntity insertEntireUserData(@RequestBody List<Map<String, Object>> param) {

		Map<String, Object> result = new HashMap<String, Object>();

		parserService.entireSave(param);

		result.put("resultCode", 200);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
