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
public class MentalData implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "varchar(255) comment '인지기능검사 해당/비해당 여부에 관한 데이터'")
    private String cognitiveImpairmentInspection;
    @Column(columnDefinition = "varchar(255) comment '인지기능검사 이후 인지기능에 대한 판정데이터'")
    private String cognitiveImpairmentResult;

    @Column(columnDefinition = "varchar(255) comment '우울증 검사 해당/비해당 여부에 관한 데이터'")
    private String depressiveDisorderInspection;
    @Column(columnDefinition = "varchar(255) comment '우울증 검사 이후 인지기능에 대한 판정데이터'")
    private String depressiveDisorderResult;
    
    @Builder
    public MentalData(String depressiveDisorderInspection, String depressiveDisorderResult, String cognitiveImpairmentInspection, String cognitiveImpairmentResult) {
    	this.depressiveDisorderInspection = depressiveDisorderInspection;
    	this.depressiveDisorderResult = depressiveDisorderResult;
    	this.cognitiveImpairmentInspection = cognitiveImpairmentInspection;
    	this.cognitiveImpairmentResult = cognitiveImpairmentResult;
    }
}
