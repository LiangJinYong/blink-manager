package com.blink.domain.pdf;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blink.domain.user.UserData;
import com.blink.enumeration.InspectionSubType;
import com.blink.enumeration.InspectionType;

public interface PdfIndividualWebRepository extends JpaRepository<PdfIndividualWeb, Long>{

	Optional<PdfIndividualWeb> findByUserDataAndPdfWebAndInspectionTypeAndInspectionSubType(UserData userData,
			PdfWeb pdfWeb, InspectionType inspectionType, InspectionSubType inspectionSubType);

}
