package com.blink.enumeration;

public enum InspectionType {
    unknown("알수없음"),
    first("1차 검진"),
    second("2차 검진"),
    cancer("암 검진"),
    outpatient("문진");

    private String name;

    InspectionType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

}