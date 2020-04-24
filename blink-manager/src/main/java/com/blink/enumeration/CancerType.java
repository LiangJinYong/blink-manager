package com.blink.enumeration;

public enum CancerType {
    stomachCancer("위암"),
    colorectalCancer("대장암"),
    liverCancer("간암"),
    cervicalCancer("자궁경부암"),
    breastCancer("유방암"),
    lungCancer("폐암");

    private String name;
    CancerType(String name) {
        this.name = name;
    }
}
