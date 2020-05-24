package com.blink.domain.sendMailResultWeb;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.blink.domain.BaseTimeEntity;
import com.blink.domain.admin.Admin;
import com.blink.domain.hospital.Hospital;
import com.blink.domain.pdf.PdfWeb;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {@Index(name = "IDX_HOSPITAL", columnList = "hospital_id")})
public class SendMailResultWeb extends BaseTimeEntity {
	 private static final long serialVersionUID = 1949440060922311222L;

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Embedded
	    private FileInfo fileInfo;

	    //발송 일자
	    private LocalDate sentDate;

	    @Column(columnDefinition = "int default 0 comment '발송수량'")
	    private Integer sentCount;
	    
	    private LocalDate uploadDate;

	    @JsonIgnore
	    @JoinColumn(name = "hospital_id", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT, name = "NONE"))
	    @ManyToOne(fetch = FetchType.LAZY)
	    //@JsonIgnoreProperties({"employeeName", "employeeEmail", "employeeTel", "role", "regDate", "isDelete", "name", "address", "createdAt", "updatedAt"})
	    private Hospital hospital;

	    @JsonIgnore
	    @JoinColumn(name = "admin_id", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT, name = "NONE"))
	    @ManyToOne(fetch = FetchType.LAZY)
	    private Admin admin;
	    
	    @Builder
	    public SendMailResultWeb(FileInfo fileInfo, LocalDate sentDate, Integer sentCount, Hospital hospital, LocalDate uploadDate) {
	    	this.fileInfo = fileInfo;
	    	this.sentDate = sentDate;
	    	this.sentCount = sentCount;
	    	this.hospital = hospital;
	    	this.uploadDate = uploadDate;
	    }
}
