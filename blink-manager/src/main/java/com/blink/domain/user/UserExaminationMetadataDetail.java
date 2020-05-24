package com.blink.domain.user;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.BatchSize;

import com.blink.domain.pdf.PdfIndividualWeb;
import com.blink.enumeration.InspectionSubType;
import com.blink.enumeration.InspectionType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserExaminationMetadataDetail implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @BatchSize(size = 30)
    @JsonIgnore
    @JoinColumn(name="user_examination_metadata_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private UserExaminationMetadata userExaminationMetadata;

    @NonNull
    @Column(columnDefinition = "tinyint comment '검진 0:구분없음, 1:1차검진, 2:2차검진, 3:암검진'")
    private InspectionType inspectionType;

    @NonNull
    @Column(columnDefinition = "tinyint default 0 comment '0: 구분없음'")
    private InspectionSubType inspectionSubType;

    @BatchSize(size = 30)
    @ManyToOne(fetch =FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT, name = "NONE"))
    private PdfIndividualWeb pdfIndividualWeb;
    
    @NonNull
    @Column(columnDefinition = "date comment '검진일'")
    private LocalDate dateExamined;

    @Column(columnDefinition = "date comment '검진결과작성일'")
    private LocalDate dateInterpreted;

    @Column(length = 10)
    private String doctorName;

    @Column(length = 30)
    private String doctorLicenseNumber;

    private String inspectionPlace;
    
    @Builder
    public UserExaminationMetadataDetail(UserExaminationMetadata userExaminationMetadata, InspectionType inspectionType, InspectionSubType inspectionSubType, LocalDate dateExamined, LocalDate dateInterpreted, String doctorName, String doctorLicenseNumber, String inspectionPlace) {
    	this.userExaminationMetadata = userExaminationMetadata;
    	this.inspectionType = inspectionType;
    	this.inspectionSubType = inspectionSubType;
    	this.dateExamined = dateExamined;
    	this.dateInterpreted = dateInterpreted;
    	this.doctorName = doctorName;
    	this.doctorLicenseNumber = doctorLicenseNumber;
    	this.inspectionPlace = inspectionPlace;
    }
    
    public void updateDetailInfo(LocalDate dateExamined, String inspectionPlace, String doctorName, String doctorLicenseNumber, LocalDate dateInterpreted) {
    	this.dateExamined = dateExamined;
    	this.inspectionPlace = inspectionPlace;
    	this.doctorName = doctorName;
    	this.doctorLicenseNumber = doctorLicenseNumber;
    	this.dateInterpreted = dateInterpreted;
    }
}
