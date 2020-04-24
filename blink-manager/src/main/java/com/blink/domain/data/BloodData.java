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
public class BloodData implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(columnDefinition = "varchar(255) comment '혈색소수치'")
	private String hemoglobin;

	@Column(columnDefinition = "varchar(255) comment '혈색소수치에 따른 판정'")
	private String hemoglobinResult;

	@Column(columnDefinition = "varchar(255) comment '공복혈당'")
	private String fastingGlucose;

	@Column(columnDefinition = "varchar(255) comment '공복혈당수치에 따른 판정'")
	private String fastingGlucoseResult;

	@Column(columnDefinition = "varchar(255) comment '총콜레스테롤'")
	private String totalCholesterol;
	@Column(columnDefinition = "varchar(255) comment 'HDL 콜레스테롤 수치'")
	private String hdlCholesterol;
	@Column(columnDefinition = "varchar(255) comment '중성지방 수치'")
	private String triglyceride;
	@Column(columnDefinition = "varchar(255) comment 'LDL 콜레스테롤 수치'")
	private String ldlCholesterol;
	@Column(columnDefinition = "varchar(255) comment '이상지질혈증 판정'")
	private String lipidResult;
	@Column(columnDefinition = "varchar(255) comment '혈청크레아티닌수치'")
	private String creatinine;
	@Column(columnDefinition = "varchar(255) comment '신사구체여과율 수치'")
	private String eGfr;
	@Column(columnDefinition = "varchar(255) comment '신장기능이상 판정'")
	private String renalDiseaseResult;
	@Column(columnDefinition = "varchar(255) comment 'AST 수치'")
	private String ast;
	@Column(columnDefinition = "varchar(255) comment 'ALT 수치'")
	private String alt;
	@Column(columnDefinition = "varchar(255) comment '감마 GTP 수치'")
	private String gammaGtp;
	@Column(columnDefinition = "varchar(255) comment '간기능상태 판정'")
	private String liverDiseaseResult;

	@Builder
    public BloodData(String hemoglobin, String hemoglobinResult, String fastingGlucose, String fastingGlucoseResult, String lipidResult, String totalCholesterol, String hdlCholesterol, String triglyceride, String ldlCholesterol, String renalDiseaseResult, String creatinine, String eGfr, String liverDiseaseResult, String ast, String alt, String gammaGtp) {
    	this.hemoglobin = hemoglobin;
    	this.hemoglobinResult=hemoglobinResult;
    	this.fastingGlucose=fastingGlucose;
    	this.fastingGlucoseResult=fastingGlucoseResult;
    	this.lipidResult=lipidResult;
    	this.totalCholesterol=totalCholesterol;
    	this.hdlCholesterol=hdlCholesterol;
    	this.triglyceride=triglyceride;
    	this.ldlCholesterol=ldlCholesterol;
    	this.renalDiseaseResult=renalDiseaseResult;
    	this.creatinine=creatinine;
    	this.eGfr=eGfr;
    	this.liverDiseaseResult=liverDiseaseResult;
    	this.ast=ast;
    	this.alt=alt;
    	this.gammaGtp=gammaGtp;
    }

}
