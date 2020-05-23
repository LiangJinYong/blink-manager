
package com.blink.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blink.common.CommonResponse;
import com.blink.common.CommonResultCode;
import com.blink.service.AccountService;
import com.blink.service.JoinContactService;
import com.blink.util.CommonUtils;
import com.blink.util.JwtUtil;
import com.blink.web.admin.web.dto.UserSignupRequestDto;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController("unauthenticatedAccountController")
@RequestMapping("/account")
public class AccountController {

	private final AccountService accountService;
	private final JoinContactService joinContactService;

	private final JwtUtil jwtUtil;

	private final AuthenticationManager authenticationManager;

	@ApiOperation(value = "ID 중복 체크")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "username", value = "사용자ID", required = true, dataType = "string", paramType = "query", defaultValue = "") })
	@GetMapping("/idCheck")
	public ResponseEntity<CommonResponse> idCheck(@RequestParam("username") String username) {
		if (accountService.userExists(username)) {
			return ResponseEntity.ok(new CommonResponse(CommonResultCode.ID_ALREADY_EXISTS));
		} else {
			return ResponseEntity.ok(new CommonResponse(CommonResultCode.ID_AVAILABLE));
		}
	}

	@ApiOperation(value = "회원가입시 인증코드 발송")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "employeeTel", value = "핸드폰 번호", required = true, dataType = "string", paramType = "query", defaultValue = "") })
	@GetMapping("/code")
	public ResponseEntity<CommonResponse> sendAuthCode(@RequestParam(value = "employeeTel") String employeeTel) {

		boolean authCodeSendable = accountService.isAuthCodeSendable(employeeTel);

		if (!authCodeSendable) {
			return ResponseEntity.ok(new CommonResponse(CommonResultCode.PHONE_NUMBER_DUPLICATED));
		} else {
			accountService.sendAuthCode(employeeTel);
			return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS));
		}
	}

	@ApiOperation(value = "인증코드 확인")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "employeeTel", value = "핸드폰 번호", required = true, dataType = "string", paramType = "query", defaultValue = ""),
			@ApiImplicitParam(name = "authCode", value = "인증코드", required = true, dataType = "int", paramType = "query", defaultValue = "") })
	@GetMapping("/code/valid")
	public ResponseEntity<CommonResponse> checkAuthCode(@RequestParam(value = "employeeTel") String employeeTel,
			@RequestParam(value = "authCode") Integer authCode) {

		if (accountService.checkAuthCode(employeeTel, authCode)) {
			return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS));
		} else {
			return ResponseEntity.ok(new CommonResponse(CommonResultCode.AUTH_CODE_NOT_EQUAL));
		}
	}

	@ApiOperation(value = "회원가입")
	@PostMapping("/signup")
	public ResponseEntity<CommonResponse> signup(@RequestParam("username") String username, //
			@RequestParam("password") String password, //
			@RequestParam("hospitalName") String hospitalName, //
			@RequestParam("hospitalTel") String hospitalTel, //
			@RequestParam("postcode") String postcode, //
			@RequestParam("address") String address, //
			@RequestParam("addressDetail") String addressDetail, //
			@RequestParam("employeeName") String employeeName, //
			@RequestParam("employeePosition") String employeePosition, //
			@RequestParam("employeeTel") String employeeTel, //
			@RequestParam("employeeEmail") String employeeEmail, //
			@RequestParam("agreeSendYn") Integer agreeSendYn, //
			@RequestParam("programInUse") String programInUse, //
			@RequestParam("licensePhoto") MultipartFile file) {

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
				.build();
		try {
			
			if(!accountService.isSignupAble(employeeTel)) {
				return ResponseEntity.ok(new CommonResponse(CommonResultCode.AUTH_CODE_NOT_CHECKED));
			}
			
			accountService.signupUser(requestDto, file);
			return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(new CommonResponse(CommonResultCode.INTERNAL_ERROR));
		}
	}

	@ApiOperation(value = "로그인")
	@PostMapping("/login")
	public ResponseEntity<CommonResponse> login(@RequestParam("username") String username, @RequestParam("password") String password) throws Exception {

		Map<String, Object> result = new HashMap<>();

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			accountService.resetLoginTryCount(username);
			
		} catch (Exception e) {
			if (accountService.userExists(username)) {
				int loginTryCount = accountService.increaseLoginTryCount(username);
				result.put("loginTryCount", loginTryCount);
				return ResponseEntity.ok(new CommonResponse(CommonResultCode.ACCOUNT_PASSWORD_INCORRECT, result));
			} else {
				return ResponseEntity.ok(new CommonResponse(CommonResultCode.ACCOUNT_INFO_NONEXIST));
			}
		}
		
		Map<String, Object> userStatus = accountService.getUserStatusInfo(username);
		result.putAll(userStatus);
		
		String role = (String) userStatus.get("role");
		String token = jwtUtil.generateToken(username, role);
		result.put("token", token);

		return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS, result));
	}
	
	@ApiOperation("아이디/비밀번호 찾기 - 계정 유효성 체크")
	@GetMapping("/valid")
	public ResponseEntity<CommonResponse> isAccountValid(@RequestParam("employeeName") String employeeName, @RequestParam("employeeTel") String employeeTel) {
		
		if(!accountService.isAccountValid(employeeName, employeeTel)) {
			return ResponseEntity.ok(new CommonResponse(CommonResultCode.ACCOUNT_INFO_NONEXIST));
		} else {
			accountService.sendAuthCode(employeeTel);
			return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS));
		}
	}
	
	@ApiOperation("아이디 찾기")
	@GetMapping("/findID")
	public ResponseEntity<CommonResponse> findID(@RequestParam("employeeTel") String employeeTel, @RequestParam(value = "authCode") Integer authCode) {
		
		Map<String, Object> result = new HashMap<>();
		
		if (accountService.checkAuthCode(employeeTel, authCode)) {
			String username = accountService.getUsername(employeeTel);
			result.put("userID", username);
			return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS, result));
		} else {
			return ResponseEntity.ok(new CommonResponse(CommonResultCode.AUTH_CODE_NOT_EQUAL));
		}
	}
	
	@ApiOperation("비밀번호 찾기")
	@GetMapping("/findPassword")
	public ResponseEntity<CommonResponse> findPassword(@RequestParam("employeeTel") String employeeTel, @RequestParam(value = "authCode") Integer authCode) {
		
		Map<String, Object> result = new HashMap<>();
		
		if (accountService.checkAuthCode(employeeTel, authCode)) {
			
			String tempPassword = CommonUtils.getRamdomPassword(6);
			result.put("tempPassword", tempPassword);
			accountService.updatePassword(employeeTel, tempPassword);
			return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS, result));
		} else {
			return ResponseEntity.ok(new CommonResponse(CommonResultCode.AUTH_CODE_NOT_EQUAL));
		}
	}
	
	@ApiOperation(value = "제휴문의 등록하기")
	@PostMapping("/question")
	public ResponseEntity<CommonResponse> saveQuestion(@RequestParam("clinicName") String clinicName,
			@RequestParam("email") String email,
			@RequestParam("name") String name,
			@RequestParam("tel") String tel,
			@RequestParam("inquiry") String inquiry,
			@RequestParam("usedProgram") String usedProgram) {
		
		joinContactService.saveQuestion(clinicName, email, name, tel, inquiry, usedProgram);
		return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS));
	}
}
	
