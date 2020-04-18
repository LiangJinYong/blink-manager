package com.blink.web.hospital;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
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
	@PutMapping("/email")
	public ResponseEntity<CommonResponse> update(@RequestParam("newEmail") String newEmail, Principal principal) {
		String username = principal.getName();
		accountService.updateEmail(username, newEmail);
		return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS));
	}
}
