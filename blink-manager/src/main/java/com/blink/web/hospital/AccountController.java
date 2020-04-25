package com.blink.web.hospital;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blink.common.CommonResponse;
import com.blink.common.CommonResultCode;
import com.blink.service.AccountService;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController("authenticatedAccountController")
@RequestMapping("/hospital")
public class AccountController {

	private final AccountService accountService;

	@ApiOperation(value = "이메일 변겅")
	@PutMapping("/email/{hospitalId}")
	public ResponseEntity<CommonResponse> update(@PathVariable("hospitalId") Long hospitalId, @RequestParam("newEmail") String newEmail) {
		accountService.updateEmail(newEmail, hospitalId);
		return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS));
	}
	
	@ApiOperation(value = "계약하기 - 확인완료")
	@PutMapping("/signConfirm/{hospitalId}")
	public ResponseEntity<CommonResponse> signConfirm(@PathVariable("hospitalId") Long hospitalId) {
		accountService.signConfirm(hospitalId);
		return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS));
	}
}
