package com.blink.domain.pdf;

import javax.persistence.ConstraintMode;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.blink.domain.BaseEntity;
import com.blink.domain.admin.Admin;
import com.blink.domain.hospital.Hospital;
import com.blink.domain.sendMailResultWeb.FileInfo;
import com.blink.enumeration.PdfProcessStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
//@Table(indexes = {@Index(name = "hospitalId", columnList = "hospitalId")})
public class PdfWeb extends BaseEntity {

    private static final long serialVersionUID = 4345026762797135575L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "hospital_id", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT, name = "NONE"))
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"employeeName", "employeeEmail", "employeeTel", "role", "regDate", "isDelete", "name", "address", "createdAt", "updatedAt"})
    private Hospital hospital;

    @JoinColumn(name = "hospital_user_id", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT, name = "NONE"))
    @ManyToOne(fetch = FetchType.LAZY)
    private Admin hospitalUser;

    @Embedded
    private FileInfo fileInfo;

    @Enumerated(EnumType.ORDINAL)
    private PdfProcessStatus status;

    @Builder
    public PdfWeb(Hospital hospital, FileInfo fileInfo, PdfProcessStatus status) {
    	this.hospital = hospital;
    	this.fileInfo = fileInfo;
    	this.status = status;
    }
}
