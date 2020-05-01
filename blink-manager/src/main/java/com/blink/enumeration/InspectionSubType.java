package com.blink.enumeration;

public enum InspectionSubType {
    none("구분 없음"),
    reportFirstGeneral("기본검진"),
    reportFirstLifeChange("내생애전환주기"),
    reportSecondLifeHabit("생활"),
    reportCancerWomb("자궁경부암"),
    reportCancerStomach("위암"),
    reportCancerColon("대장암1차"),
    reportCancerBreast("유방암"),
    reportCancerLiver("간암상반기"),
    reportCancerLiverSecond("간암하반기"),
    reportCancerLung("폐암"),
    reportCancerColonSecond("대장암2차");

    private String name;

    InspectionSubType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
