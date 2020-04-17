package com.blink.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CommonResultCode {

	SUCCESS(1000, "성공"),
	ID_AVAILABLE(1001,"사용 가능한 ID입니다."),
	ID_ALREADY_EXISTS(2101, "이미 사용중인 ID입니다."),
	PHONE_NUMBER_DUPLICATED(2101, "이미 가입된 핸드폰 번호입니다."),
	AUTH_CODE_NOT_EQUAL(2102, "인증코드가 불일치 혹은 만료됐습니다."),
	AUTH_CODE_NOT_CHECKED(2103, "인증코드 체크가 필요합니다."), 
	ACCOUNT_INFO_NONEXIST(2013, "존재하지 않는 계정입니다."),
	ACCOUNT_PASSWORD_INCORRECT(2014, "비밀번호가 틀렸습니다."),
	INTERNAL_ERROR(5000, "서버 내부 오류"); 
	
	private final int resultCode;
	private final String resultMessage;
}
