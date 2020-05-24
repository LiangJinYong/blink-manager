package com.blink.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blink.common.CommonResultCode;
import com.blink.domain.hospital.Hospital;
import com.blink.domain.hospital.HospitalRepository;
import com.blink.domain.sign.WebSign;
import com.blink.domain.sign.WebSignRepository;
import com.blink.domain.webfiles.WebFilesRepository;
import com.blink.enumeration.FileUploadUserType;
import com.blink.service.aws.BucketService;
import com.blink.util.FileUploadUtils;
import com.blink.web.admin.web.dto.WebFileResponseDto;
import com.blink.web.hospital.dto.webSign.WebSignResponseDto;
import com.google.common.base.Optional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class WebSignService {
	private final WebSignRepository webSignRepository;
	private final FileUploadUtils fileUploadUtils;
	private final HospitalRepository hospitalRepository;
	private final WebFilesRepository webFilesRepository;
	private final BucketService bucketService;
	
	public CommonResultCode registerSign(Long hospitalId, String doctorName, String doctorPhone, String doctorId,
			String license, MultipartFile[] files) {

		Hospital hospital = hospitalRepository.findById(hospitalId).orElseThrow(() -> new IllegalArgumentException("No such hospital"));
		
		Long countByHospital = webSignRepository.countByHospital(hospital);
		
		if (countByHospital >= 10) {
			return CommonResultCode.ALREADY_10_SIGNS_EXIST;
		}
		
		Long countByDoctorId = webSignRepository.countByDoctorId(doctorId);
		
		if (countByDoctorId > 0) {
			return CommonResultCode.ALREADY_EXIST_BY_DOCTOR;
		}
		
		String groupId = fileUploadUtils.upload(files, "webSignFiles", FileUploadUserType.WEB, hospitalId, null);
		
		WebSign webSign = WebSign.builder() //
		.doctorName(doctorName) //
		.doctorPhone(doctorPhone) //
		.doctorId(doctorId) //
		.license(license) //
		.hospital(hospital) //
		.groupId(groupId) //
		.build();
		
		webSignRepository.save(webSign);		
		
		return CommonResultCode.SUCCESS;
	}

	public List<WebSignResponseDto> getSignList(Long hospitalId) {
		
		Hospital hospital = hospitalRepository.findById(hospitalId).orElseThrow(() -> new IllegalArgumentException("No such hospital"));
		
		List<WebSignResponseDto> webSignList = webSignRepository.findByHospital(hospital);
		
		for(WebSignResponseDto dto : webSignList) {
			String groupId = dto.getGroupId();
			WebFileResponseDto file = webFilesRepository.findByGroupId(groupId).get(0);
			dto.setFile(file);
		}
		return webSignList;
	}

	public void deleteSign(String doctorId) {
		Optional<WebSign> webSign = webSignRepository.findByDoctorId(doctorId);
		if (webSign.isPresent()) {
			String groupId = webSign.get().getGroupId();
			WebFileResponseDto file = webFilesRepository.findByGroupId(groupId).get(0);
			String fileKey = file.getFileKey();
			bucketService.delete(fileKey);
		}
		
		webSignRepository.deleteByDoctorId(doctorId);
	}
}
