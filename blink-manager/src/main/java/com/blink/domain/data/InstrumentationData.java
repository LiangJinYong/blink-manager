package com.blink.domain.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class InstrumentationData implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "varchar(255) comment '신장 데이터를 저장함'")
    private String height;
    @Column(columnDefinition = "varchar(255) comment '체중 데이터를 저장함'")
    private String weight;
    @Column(columnDefinition = "varchar(255) comment '체질량지수 데이터를 저장함'")
    private String bmi;
    @Column(columnDefinition = "varchar(255) comment '체질량지수 데이터 결과를 저장함'")
    private String bmiResult;
    @Column(columnDefinition = "varchar(255) comment '허리둘레 데이터를 저장함'")
    private String waistCircumference;
    @Column(columnDefinition = "varchar(255) comment '허리둘레 수치에 따른 판정 데이터를 저장함'")
    private String waistCircumferenceResult;
    @Column(columnDefinition = "varchar(255) comment '시력(좌) 데이터를 저장함'")
    private String visionLeft;
    @Column(columnDefinition = "varchar(255) comment '시력(우) 데이터를 저장함'")
    private String visionRight;
    @Column(columnDefinition = "varchar(255) comment '교정시력인지 여부 데이터를 저장함'")
    private String visionCorrection;
    @Column(columnDefinition = "varchar(255) comment '청력(좌) 데이터를 저장함'")
    private String hearingLeft;
    @Column(columnDefinition = "varchar(255) comment '청력(우) 데이터를 저장함'")
    private String hearingRight;
    @Column(columnDefinition = "varchar(255) comment '청력에 대한 판정 데이터를 저장함'")
    private String hearingResult;
    @Column(columnDefinition = "varchar(255) comment '수축기혈압 데이터를 저장함'")
    private String systolicBloodPressure;
    @Column(columnDefinition = "varchar(255) comment '이완기혈압 데이터를 저장함'")
    private String diastolicBloodPressure;
    @Column(columnDefinition = "varchar(255) comment '혈압에 대한 판정 데이터를 저장함'")
    private String bloodPressureResult;

    @Builder
    public InstrumentationData(String height, String weight, String bmi, String bmiResult, String waistCircumference, String waistCircumferenceResult, String visionLeft, String visionRight, String visionCorrection, String hearingLeft, String hearingRight, String hearingResult, String bloodPressureResult, String systolicBloodPressure, String diastolicBloodPressure) {
    	this.height = height;
    	this.weight = weight;
    	this.bmi = bmi;
    	this.bmiResult = bmiResult;
    	this.waistCircumference = waistCircumference;
    	this.waistCircumferenceResult = waistCircumferenceResult;
    	this.visionLeft = visionLeft;
    	this.visionRight = visionRight;
    	this.visionCorrection = visionCorrection;
    	this.hearingLeft = hearingLeft;
    	this.hearingRight = hearingRight;
    	this.hearingResult = hearingResult;
    	this.bloodPressureResult = bloodPressureResult;
    	this.systolicBloodPressure = systolicBloodPressure;
    	this.diastolicBloodPressure = diastolicBloodPressure;
    }

}
