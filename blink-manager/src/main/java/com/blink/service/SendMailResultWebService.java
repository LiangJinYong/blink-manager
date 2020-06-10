package com.blink.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blink.domain.hospital.Hospital;
import com.blink.domain.hospital.HospitalRepository;
import com.blink.domain.sendMailResultWeb.FileInfo;
import com.blink.domain.sendMailResultWeb.SendMailResultWeb;
import com.blink.domain.sendMailResultWeb.SendMailResultWebRepository;
import com.blink.service.aws.BucketService;
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
	private final BucketService bucketService;

	public Page<SendMailResultWebResponseDto> getSendMailResultWeb(String searchText, Date startDate, Date endDate,
			Pageable pageable, Long hospitalId) {

		Page<SendMailResultWebResponseDto> sendMailResultWebList = sendMailResultWebRepository
				.findBySearchTextAndPeriod(startDate, endDate, hospitalId, pageable);
		return sendMailResultWebList;
	}

	public void registerSendMailResultWeb(Long hospitalId, Integer sentCount, LocalDate sentDate, LocalDate uploadDate,
			MultipartFile file) {

		Hospital hospital = hospitalRepository.findById(hospitalId)
				.orElseThrow(() -> new IllegalArgumentException("No such hospital"));

		String hospitalName = hospital.getName();

		FileInfo fileInfo = fileUploadUtils.uploadPdfFile(file, hospitalName);

		SendMailResultWeb sendMailResultWeb = SendMailResultWeb.builder() //
				.fileInfo(fileInfo) //
				.sentDate(sentDate) //
				.sentCount(sentCount) //
				.uploadDate(uploadDate) //
				.hospital(hospital) //
				.build();

		sendMailResultWebRepository.save(sendMailResultWeb);
	}

	public List<Map<Long, String>> getPdfInfoList(Long hospitalId) {
		List<Map<Long, String>> pdfInfoList = sendMailResultWebRepository.findPdfInfoList(hospitalId);
		return pdfInfoList;
	}

	public void deleteSendMailResultWeb(Long sendMailResultWebId) {
	
		SendMailResultWeb sendMailResultWeb = sendMailResultWebRepository.findById(sendMailResultWebId).orElseThrow(() -> new IllegalArgumentException("No such send mail result record"));
		
		String filekey = sendMailResultWeb.getFileInfo().getFilekey();
		bucketService.delete(filekey);
		
		sendMailResultWebRepository.deleteById(sendMailResultWebId);
	}

}
