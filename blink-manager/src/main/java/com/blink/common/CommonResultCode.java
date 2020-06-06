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
	NO_REQUIRED_NOTICE_FOUND(2201, "필수고지사항 데이터가 없습니다."),
	NO_SUCH_DATA(4000, "해당 데이터가 없습니다."),
	NO_USER_DATA(4001, "해당 유저의 정보가 없습니다."),
	USER_DATA_ALREADY_EXISTS(4002, "이미 존재하는 유저정보입니다."),
	METADATA_ENTIRE_DATA_OF_ONE_NOT_EXISTS(4003, "검진 데이터 이미 존재하여 삭제할수 없습니다."),
	ALREADY_10_SIGNS_EXIST(4004, "하나의 병원의 사인 기록은 최대 10건입니다."),
	ALREADY_EXIST_BY_DOCTOR(4005, "해당 의사가 등록한 사인이 이미 존재합니다."),
	REGISTER_EXAMINATION_DATA_ERROR(4006, "검진 데이터 등록 오류."),
	MOBILE_REGISTER_EXAMINATION_DATA_ERROR(4007, "모바일 검진 데이터 등록 오류."),
	INTERNAL_ERROR(5000, "서버 내부 오류"); 
	
	private final int resultCode;
	private final String resultMessage;
}
