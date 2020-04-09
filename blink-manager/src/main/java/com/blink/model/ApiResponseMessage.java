package com.blink.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ApiResponseMessage {
	
	private final String status;
    private final String message;
    private final String errorMessage;
    private final String errorCode;
}
