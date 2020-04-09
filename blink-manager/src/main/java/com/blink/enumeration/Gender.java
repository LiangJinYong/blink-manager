package com.blink.enumeration;

import lombok.Getter;

public enum Gender {

	MALE("남성"), FEMALE("여성");
	
	@Getter
	private String name;

	Gender(String name) {
		this.name = name;
	}
}
