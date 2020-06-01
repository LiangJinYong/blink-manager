package com.blink.web.admin.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blink.common.CommonResponse;
import com.blink.common.CommonResultCode;
import com.blink.service.AccountService;
import com.blink.web.admin.web.dto.UserSignupRequestDto;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController("adminAccountController")
@RequestMapping("/admin")
public class AccountController {
	
	private final AccountService accountService;
	
	@ApiOperation(value = "회원가입")
	@PostMapping("/signup")
	public ResponseEntity<CommonResponse> signup(@RequestParam("username") String username, //
			@RequestParam("password") String password, //
			@RequestParam("hospitalName") String hospitalName, //
			@RequestParam("hospitalTel") String hospitalTel, //
			@RequestParam("postcode") String postcode, //
			@RequestParam("address") String address, //
			@RequestParam(name = "addressDetail", required = false) String addressDetail, //
			@RequestParam("employeeName") String employeeName, //
			@RequestParam("employeePosition") String employeePosition, //
			@RequestParam("employeeTel") String employeeTel, //
			@RequestParam("employeeEmail") String employeeEmail, //
			@RequestParam("agreeSendYn") Integer agreeSendYn, //
			@RequestParam("programInUse") String programInUse,
			@RequestParam(name = "signagesStand", required = false) Integer signagesStand,
			@RequestParam(name = "signagesMountable", required = false) Integer signagesMountable,
			@RequestParam(name = "signagesExisting", required = false) Integer signagesExisting) {

		UserSignupRequestDto requestDto = UserSignupRequestDto.builder().username(username) //
				.password(password) //
				.hospitalName(hospitalName) //
				.hospitalTel(hospitalTel) //
				.postcode(postcode) //
				.address(address) //
				.addressDetail(addressDetail) //
				.employeeName(employeeName) //
				.employeePosition(employeePosition) //
				.employeeTel(employeeTel) //
				.employeeEmail(employeeEmail) //
				.agreeSendYn(1) //
				.programInUse(programInUse) //
				.signagesStand(signagesStand)
				.signagesMountable(signagesMountable)
				.signagesExisting(signagesExisting)
				.build();
		try {
			accountService.signupUserByAdmin(requestDto);
			return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(new CommonResponse(CommonResultCode.INTERNAL_ERROR));
		}
	}
}
