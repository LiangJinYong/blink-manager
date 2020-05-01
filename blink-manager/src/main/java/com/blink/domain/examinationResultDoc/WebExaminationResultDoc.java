package com.blink.domain.examinationResultDoc;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.blink.domain.BaseTimeEntity;
import com.blink.domain.hospital.Hospital;
import com.blink.domain.pdf.PdfWeb;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class WebExaminationResultDoc extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hospital_id")
	private Hospital hospital;

	private String groupId;
	
	@OneToOne
	@JoinColumn(name="pdf_id")
	private PdfWeb pdfWeb;
	
	@Builder
	public WebExaminationResultDoc(Hospital hospital, String groupId, PdfWeb pdfWeb) {
		this.hospital = hospital;
		this.groupId = groupId;
		this.pdfWeb = pdfWeb;
	}
}
