package com.blink.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blink.domain.hospital.Hospital;
import com.blink.domain.hospital.HospitalRepository;
import com.blink.domain.pdf.PdfWeb;
import com.blink.domain.pdf.PdfWebRepository;
import com.blink.domain.sendMailResultWeb.FileInfo;
import com.blink.domain.sendMailResultWeb.SendMailResultWeb;
import com.blink.domain.sendMailResultWeb.SendMailResultWebRepository;
import com.blink.enumeration.SearchPeriod;
import com.blink.util.CommonUtils;
import com.blink.util.FileUploadUtils;
import com.blink.web.hospital.dto.sendMailResultWeb.SendMailResultWebResponseDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class SendMailResultWebService {

	private final HospitalRepository hospitalRepository;
	private final SendMailResultWebRepository sendMailResultWebRepository;
	private final FileUploadUtils fileUploadUtils;
	private final PdfWebRepository pdfWebRepository;

	public Page<SendMailResultWebResponseDto> getSendMailResultWeb(String searchText, SearchPeriod period,
			Pageable pageable, Long hospitalId) {

		LocalDateTime time = CommonUtils.getSearchPeriod(period);

		Page<SendMailResultWebResponseDto> sendMailResultWebList = sendMailResultWebRepository
				.findBySearchTextAndPeriod(time, hospitalId, pageable);
		return sendMailResultWebList;
	}

	public void registerSendMailResultWeb(Long pdfWebId, Long hospitalId, Integer totalCount, Integer sentCount, LocalDate sentDate,
			MultipartFile file) {

		Hospital hospital = hospitalRepository.findById(hospitalId)
				.orElseThrow(() -> new IllegalArgumentException("No such hospital"));

		String hospitalName = hospital.getName();

		FileInfo fileInfo = fileUploadUtils.uploadPdfFile(file, hospitalName);

		PdfWeb pdfWeb = pdfWebRepository.findById(pdfWebId).orElseThrow(() -> new IllegalArgumentException("No such pdf web"));
		
		SendMailResultWeb sendMailResultWeb = SendMailResultWeb.builder() //
				.fileInfo(fileInfo) //
				.sentDate(sentDate) //
				.sentCount(sentCount) //
				.totalCount(totalCount) //
				.hospital(hospital) //
				.pdfWeb(pdfWeb) //
				.build();

		sendMailResultWebRepository.save(sendMailResultWeb);
	}

	public List<Map<Long, String>> getPdfInfoList(Long hospitalId) {
		List<Map<Long, String>> pdfInfoList = sendMailResultWebRepository.findPdfInfoList(hospitalId);
		return pdfInfoList;
	}

}
