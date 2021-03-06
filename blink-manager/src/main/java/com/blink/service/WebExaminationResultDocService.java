package com.blink.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.json.JSONException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blink.domain.examinationResultDoc.WebExaminationResultDoc;
import com.blink.domain.examinationResultDoc.WebExaminationResultDocRepository;
import com.blink.domain.examinationResultDocMobile.WebExaminationResultDocMobile;
import com.blink.domain.examinationResultDocMobile.WebExaminationResultDocMobileRepository;
import com.blink.domain.hospital.Hospital;
import com.blink.domain.hospital.HospitalRepository;
import com.blink.domain.pdf.PdfWeb;
import com.blink.domain.pdf.PdfWebRepository;
import com.blink.domain.sendMailResultWeb.FileInfo;
import com.blink.domain.webfiles.WebFilesRepository;
import com.blink.enumeration.FileUploadUserType;
import com.blink.enumeration.PdfProcessStatus;
import com.blink.service.aws.BucketService;
import com.blink.util.FCMUtils;
import com.blink.util.FileUploadUtils;
import com.blink.web.admin.web.dto.WebFileResponseDto;
import com.blink.web.hospital.dto.webExaminationResultDoc.WebExaminationResultDocResponseDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class WebExaminationResultDocService {

	private final WebExaminationResultDocRepository webExaminationResultDocRepository;
	private final WebFilesRepository webFilesRepository;
	private final FileUploadUtils fileUploadUtils;
	private final HospitalRepository hospitalRepository;
	private final PdfWebRepository pdfWebRepository;
	private final BucketService bucketService;
	private final WebExaminationResultDocMobileRepository mobileRepository;
	
	public void registerExaminationResultDocs(MultipartFile[] files, Long hospitalId) {

		Hospital hospital = hospitalRepository.findById(hospitalId)
				.orElseThrow(() -> new IllegalArgumentException("No such hospital"));

		String hospitalName = hospital.getName();

		Map<String, Object> uploadedFiles = fileUploadUtils.uploadMultipleFiles(files, "examinationResultDocFiles",
				hospitalName, FileUploadUserType.WEB, hospitalId, null);

		String groupId = (String) uploadedFiles.get("groupId");

		if (groupId != null) {

			WebExaminationResultDoc webExaminationResultDoc = WebExaminationResultDoc.builder() //
					.hospital(hospital) //
					.groupId(groupId) //
					.build();

			webExaminationResultDocRepository.save(webExaminationResultDoc);

			List<String> pdfFilekeyList = (List<String>) uploadedFiles.get("pdfFilekeyList");

			List<MultipartFile> pdfFiles = new ArrayList<>();
			for (MultipartFile file : files) {
				if (file.getOriginalFilename() != null && file.getOriginalFilename().toLowerCase().endsWith(".pdf")) {
					pdfFiles.add(file);
				}
			}

			for (int i = 0; i < pdfFilekeyList.size(); i++) {
				String pdfFilekey = pdfFilekeyList.get(i);
				FileInfo fileInfo = new FileInfo(pdfFilekey, pdfFiles.get(i).getOriginalFilename());
				PdfWeb pdfWeb = PdfWeb.builder() //
						.hospital(hospital) //
						.fileInfo(fileInfo) //
						.status(PdfProcessStatus.UPLOAD) //
						.build();
				pdfWebRepository.save(pdfWeb);
			}
		}
	}

	public Page<WebExaminationResultDocResponseDto> getExaminationResultDocList(String searchText, Date startDate, Date endDate,
			Pageable pageable, Long hospitalId) {
		
		Page<WebExaminationResultDocResponseDto> examinationResultDocList = webExaminationResultDocRepository.findBySearchTextAndPeriodWithHospital(searchText, startDate, endDate, hospitalId, pageable);
		
		List<WebExaminationResultDocResponseDto> content = examinationResultDocList.getContent();
		
		for(WebExaminationResultDocResponseDto dto : content) {
			String groupId = dto.getGroupId();
			List<WebFileResponseDto> examinationResultDocFiles = webFilesRepository.findByGroupId(groupId);
			dto.setFiles(examinationResultDocFiles);
		}
		
		return examinationResultDocList;
	}

	public void deleteExaminationResultDoc(Long webExaminationResultDocId) {

		WebExaminationResultDoc resultDoc = webExaminationResultDocRepository.findById(webExaminationResultDocId).orElseThrow(() -> new IllegalArgumentException("NO such examination result doc"));
		
		String groupId = resultDoc.getGroupId();
		
		List<WebFileResponseDto> examinationResultDocFiles = webFilesRepository.findByGroupId(groupId);
		
		for(WebFileResponseDto dto : examinationResultDocFiles) {
			String fileKey = dto.getFileKey();
			bucketService.delete(fileKey);
		}
		
		webExaminationResultDocRepository.deleteById(webExaminationResultDocId);
	}

	public void deleteExaminationDataMobile(Long examinationResultDocMobileId) {
		WebExaminationResultDocMobile resultDocMobile = mobileRepository.findById(examinationResultDocMobileId).orElseThrow(() -> new IllegalArgumentException("NO such mobile examination result doc"));
		
		String groupId = resultDocMobile.getGroupId();
		List<WebFileResponseDto> examinationResultDocFiles = webFilesRepository.findByGroupId(groupId);
		
		for(WebFileResponseDto dto : examinationResultDocFiles) {
			String fileKey = dto.getFileKey();
			bucketService.delete(fileKey);
		}
		
		mobileRepository.delete(resultDocMobile);
	}

}
