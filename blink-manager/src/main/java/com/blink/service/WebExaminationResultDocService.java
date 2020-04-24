package com.blink.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blink.domain.examinationResultDoc.WebExaminationResultDoc;
import com.blink.domain.examinationResultDoc.WebExaminationResultDocRepository;
import com.blink.domain.hospital.Hospital;
import com.blink.domain.hospital.HospitalRepository;
import com.blink.domain.webfiles.WebFilesRepository;
import com.blink.enumeration.FileUploadUserType;
import com.blink.enumeration.SearchPeriod;
import com.blink.util.CommonUtils;
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
	
	public void registerExaminationResultDocs(MultipartFile[] files, Long hospitalId) {

		Hospital hospital = hospitalRepository.findById(hospitalId).orElseThrow(() -> new IllegalArgumentException("No such hospital"));
		String groupId = fileUploadUtils.upload(files, "examinationResultDocFiles", FileUploadUserType.WEB, hospitalId, null);
		
		WebExaminationResultDoc webExaminationResultDoc = WebExaminationResultDoc.builder() //
		.hospital(hospital) //
		.groupId(groupId) //
		.build();
		
		webExaminationResultDocRepository.save(webExaminationResultDoc);
	}

	public Page<WebExaminationResultDocResponseDto> getExaminationResultDocList(String searchText, SearchPeriod period,
			Pageable pageable, Long hospitalId) {
		
		LocalDateTime time = CommonUtils.getSearchPeriod(period);
		
		Page<WebExaminationResultDocResponseDto> examinationResultDocList = webExaminationResultDocRepository.findBySearchTextAndPeriodWithHospital(time, hospitalId, pageable);
		
		List<WebExaminationResultDocResponseDto> content = examinationResultDocList.getContent();
		
		for(WebExaminationResultDocResponseDto dto : content) {
			String groupId = dto.getGroupId();
			List<WebFileResponseDto> examinationResultDocFiles = webFilesRepository.findByGroupId(groupId);
			dto.setFiles(examinationResultDocFiles);
		}
		
		return examinationResultDocList;
	}

}
