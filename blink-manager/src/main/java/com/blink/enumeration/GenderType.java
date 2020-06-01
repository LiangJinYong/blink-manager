package com.blink.enumeration;

public enum GenderType {
	MALE("남성"), FEMALE("여성"), UNKNOWN("미확인");

	private String genderName;

	GenderType(String genderName){
		this.genderName = genderName;
	}

	public static boolean contains(String s) {
		for (GenderType choice : values())
			if (choice.name().equals(s))
				return true;
		return false;
	}

	/**
	 * 주민번호 뒤에 첫자리 기준, 성별 리턴
	 * @return GenderType 성별 타입
	 */
	public static GenderType getGenderByFirstNumber(String birthDay) {

		if(birthDay != null && birthDay.length() == 7) {
			int firstNumber = Character.getNumericValue(birthDay.charAt(6));

			return (firstNumber%2 == 0) ? FEMALE : MALE;
		} else {

			return UNKNOWN;
		}


	}

	/**
	 * 주민번호 뒤에 첫자리 기준, 탄생년도 리턴
	 * @return String yyyyMMdd 생년월일 리턴
	 */
	public static String getGeneralBirthDay(String birthDay) {

		if(birthDay != null && birthDay.length() == 7) {
			int firstNumber = Character.getNumericValue(birthDay.charAt(6));
			String yyMMdd = birthDay.substring(0,6);
			String prefixYY;

			if(firstNumber == 9 || firstNumber == 0) {
				prefixYY = "18";
			} else if(firstNumber == 1 || firstNumber == 2 || firstNumber == 5 || firstNumber == 6) {
				prefixYY = "19";
			} else {
				prefixYY = "20";
			}

			return prefixYY+yyMMdd;
		} else {
			return "20000101";
		}

	}

	/**
	 * 주민번호 뒤에 첫자리 기준, 탄생년도 리턴
	 * MyData용
	 * @return String yyyy-MM-dd 생년월일 리턴
	 */
	public static String getGeneralBirthDayForMyDataFormatCase(String birthDay) {

		if(birthDay != null && birthDay.length() == 7) {
			int firstNumber = Character.getNumericValue(birthDay.charAt(6));
			String yy = birthDay.substring(0,2);
			String MM = birthDay.substring(2,4);
			String dd = birthDay.substring(4,6);
			String prefixYY = "20";

			if(firstNumber == 9 || firstNumber == 0) {
				prefixYY = "18";
			} else if(firstNumber == 1 || firstNumber == 2 || firstNumber == 5 || firstNumber == 6) {
				prefixYY = "19";
			} else {
				prefixYY = "20";
			}

			return prefixYY + yy + "-" + MM + "-" + dd;
		} else {
			return "2000-01-01";
		}

	}

	public String getGenderName() {
		return genderName;
	}

	public void setGenderName(String genderName) {
		this.genderName = genderName;
	}


}
