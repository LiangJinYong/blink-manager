package com.blink.web;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blink.model.ApiResponseMessage;
import com.blink.service.ParserService;
import com.blink.web.dto.UserParserRequestDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/parser")
public class ParserController {

	private final ParserService parserService;
	
	@PostMapping("/registUser")
	public ResponseEntity<ApiResponseMessage> registUser(@RequestBody List<UserParserRequestDto> userList) {
		ApiResponseMessage message; 
		try {
			
			parserService.save(userList);
			message = new ApiResponseMessage("200", "success", "", "");
		} catch (Exception e) {
			message = new ApiResponseMessage("500", "error", "", "");
		}
		
		return new ResponseEntity<ApiResponseMessage>(message, HttpStatus.OK);
	}
}
