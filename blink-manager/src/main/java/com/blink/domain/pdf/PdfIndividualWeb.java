package com.blink.domain.pdf;

import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.blink.domain.BaseEntity;
import com.blink.domain.sendMailResultWeb.FileInfo;
import com.blink.domain.user.UserData;
import com.blink.enumeration.InspectionSubType;
import com.blink.enumeration.InspectionType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PdfIndividualWeb extends BaseEntity {

    private static final long serialVersionUID = -6934411927502593893L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "tinyint comment '검진 1:1차검진, 2:2차검진, 3:암검진'")
    private InspectionType inspectionType;

    @Column(columnDefinition = "tinyint default 0 comment '0: 구분없음'")
    private InspectionSubType inspectionSubType;
    
    private Integer examinationYear;

    @JoinColumn(name = "pdf_web_id", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT, name = "NONE"))
    @ManyToOne(fetch = FetchType.LAZY)
    private PdfWeb pdfWeb;

    @JsonIgnore
    @JoinColumn(name = "user_data_id", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT, name = "NONE"))
    @ManyToOne(fetch = FetchType.LAZY)
    private UserData userData;

    @Embedded
    private FileInfo fileInfo;
    
    @Builder
    public PdfIndividualWeb(InspectionType inspectionType, InspectionSubType inspectionSubType, PdfWeb pdfWeb, UserData userData, FileInfo fileInfo) {
    	this.inspectionType = inspectionType;
    	this.inspectionSubType = inspectionSubType;
    	this.pdfWeb = pdfWeb;
    	this.userData = userData;
    	this.fileInfo = fileInfo;
    }

}
