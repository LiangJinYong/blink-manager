package com.blink.domain.pdf;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blink.domain.user.UserData;
import com.blink.enumeration.InspectionSubType;
import com.blink.enumeration.InspectionType;

public interface PdfIndividualWebRepository extends JpaRepository<PdfIndividualWeb, Long>{

	List<PdfIndividualWeb> findByUserDataAndPdfWebAndInspectionTypeAndInspectionSubTypeAndExaminationYear(UserData userData,
			PdfWeb pdfWeb, InspectionType inspectionType, InspectionSubType inspectionSubTypemInspectionSubType, Integer examinationYear);

}
