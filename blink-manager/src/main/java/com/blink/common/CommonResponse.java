package com.blink.common;

import lombok.Getter;

@Getter
public class CommonResponse {
	
	private int resultCode;
	private String resultMsg;
	private Object resultData;
	
	public CommonResponse(CommonResultCode commonResultCode) {
		this.resultCode = commonResultCode.getResultCode();
		this.resultMsg = commonResultCode.getResultMessage();
		
	}
	
	public CommonResponse(CommonResultCode commonResultCode, Object resultData) {
		this(commonResultCode);
		this.resultData = resultData;
	}
}
