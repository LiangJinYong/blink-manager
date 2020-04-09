package com.blink.enumeration;

import lombok.Getter;

public enum Nationality {
	Native("native"), Foreigner("foreigner");

	@Getter
	private String value;

	Nationality(String value) {
		this.value = value;
	}
}
