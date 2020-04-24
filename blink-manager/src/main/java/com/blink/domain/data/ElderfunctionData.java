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
public class ElderfunctionData implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "varchar(255) comment '노인신체기능검사 해당/비해당 데이터가 저장됨'")
    private String physicalFunctionTestForElder;
    @Column(columnDefinition = "varchar(255) comment '노인신체기능검사에 해당하는 사람을 검진한 결과가 저장됨'")
    private String physicalFunctionTestForElderResult;
    @Column(columnDefinition = "varchar(255) comment '노인기능평가 해당/비해당 데이터가 저장됨'")
    private String assessmentOfFunctionalStatusOfElder;
    @Column(columnDefinition = "varchar(255) comment '노인기능평가에 해당하는 수검자의 낙상 항목에 대한 데이터 저장'")
    private String assessmentOfFallingOfElder;
    @Column(columnDefinition = "varchar(255) comment '노인기능평가에 해당하는 수검자의 일상생활수행능력 항목에 대한 데이터 저장'")
    private String assessmentOfActivitiesForDailyLivingOfElder;
    @Column(columnDefinition = "varchar(255) comment '노인기능평가에 해당하는 수검자의 예방접종 항목에 대한 데이터 저장'")
    private String assessmentOfVaccinationOfElder;
    @Column(columnDefinition = "varchar(255) comment '노인기능평가에 해당하는 수검자의 배뇨장애 항목에 대한 데이터 저장'")
    private String assessmentOfDysuresiaOfElder;
    
    private String physicalFunctionTestForElderInspection;
    private String assessmentOfFunctionalStatusOfElderInspection;
    private String assessmentOfFallingOfElderResult;
    private String assessmentOfActivitiesForDailyLivingOfElderResult;
    private String assessmentOfVaccinationOfElderResult;
    private String assessmentOfDysuresiaOfElderResult;
    
    
    @Builder
    public ElderfunctionData(String physicalFunctionTestForElderInspection, String physicalFunctionTestForElderResult, String assessmentOfFunctionalStatusOfElderInspection, String assessmentOfFallingOfElderResult, String assessmentOfActivitiesForDailyLivingOfElderResult, String assessmentOfVaccinationOfElderResult, String assessmentOfDysuresiaOfElderResult) {
    	this.physicalFunctionTestForElderInspection = physicalFunctionTestForElderInspection;
    	this.physicalFunctionTestForElderResult = physicalFunctionTestForElderResult;
    	this.assessmentOfFunctionalStatusOfElderInspection = assessmentOfFunctionalStatusOfElderInspection;
    	this.assessmentOfFallingOfElderResult = assessmentOfFallingOfElderResult;
    	this.assessmentOfActivitiesForDailyLivingOfElderResult= assessmentOfActivitiesForDailyLivingOfElderResult;
    	this.assessmentOfVaccinationOfElderResult = assessmentOfVaccinationOfElderResult;
    	this.assessmentOfDysuresiaOfElderResult = assessmentOfDysuresiaOfElderResult;
    	
    }
}
