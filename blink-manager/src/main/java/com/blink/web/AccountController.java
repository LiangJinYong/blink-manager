package com.blink.web;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blink.model.ApiResponseMessage;
import com.blink.service.UserService;
import com.blink.web.dto.UserSignupRequestDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class AccountController {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	private final UserService userService;

	@GetMapping("/sendAuthCode")
	public ResponseEntity<ApiResponseMessage> sendAuthCode(@RequestParam(value = "employeeTel") String employeeTel) {
		
		ApiResponseMessage message = new ApiResponseMessage("Success", "Hello, World", "", "");
		
		return new ResponseEntity<ApiResponseMessage>(message, HttpStatus.OK);
	}

	@PostMapping("/signup")
	public Long signup(HttpServletRequest request, @RequestParam("licensePhoto") MultipartFile file) {

		UserSignupRequestDto requestDto = UserSignupRequestDto.builder().userId(request.getParameter("userId"))
				.password(request.getParameter("password")).hospitalName(request.getParameter("hospitalName"))
				.hospitalTel(request.getParameter("hospitalTel")).postcode(request.getParameter("postcode"))
				.address(request.getParameter("address")).addressDetail(request.getParameter("addressDetail"))
				.employeeName(request.getParameter("employeeName"))
				.employeePosition(request.getParameter("employeePosition"))
				.employeeTel(request.getParameter("employeeTel")).employeeEmail(request.getParameter("employeeEmail"))
				.agreenSendYn(1).programInUse(request.getParameter("programInUse")).build();
		return userService.signupUser(requestDto, file);
	}
}
