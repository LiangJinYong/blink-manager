package com.blink.enumeration;

public enum CancerType {
    stomachCancer("위암"),
    colorectalCancer("대장암"),
    liverCancer("간암상반기"),
    cervicalCancer("자궁경부암"),
    breastCancer("유방암"),
    lungCancer("폐암"),
    colorectalCancerSecond("대장암2차"),
    liverCancerSecond("간암하반기");

    private String name;
    CancerType(String name) {
        this.name = name;
    }
}
