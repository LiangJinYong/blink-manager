package com.blink.service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blink.domain.admin.Admin;
import com.blink.domain.hospital.Hospital;
import com.blink.domain.hospital.HospitalRepository;
import com.blink.domain.user.UserExaminationMetadataDetailRepository;
import com.blink.domain.webfiles.WebFiles;
import com.blink.domain.webfiles.WebFilesRepository;
import com.blink.enumeration.FileUploadUserType;
import com.blink.service.aws.BucketService;
import com.blink.util.FileUploadUtils;
import com.blink.web.admin.web.dto.hospital.HospitalDetailResponseDto;
import com.blink.web.admin.web.dto.hospital.HospitalResponseDto;
import com.blink.web.hospital.dto.hospital.HospitalUpdateRequestDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class HospitalService {

	private final HospitalRepository hospitalRepository;
	private final WebFilesRepository webFilesRepository;
	private final BucketService bucketService;
	private final FileUploadUtils fileUploadUtils;
	private final UserExaminationMetadataDetailRepository userExaminationMetadataDetailRepository;
	private final PasswordEncoder passwordEncoder;
	

	// ------------------ ADMIN ------------------
	public HospitalResponseDto getHospitalList(String searchText, Date startDate, Date endDate, Pageable pageable) {

		Integer hospitalCount = hospitalRepository.findHospitalCount();
		Integer diagnoseCount = userExaminationMetadataDetailRepository.findDiagnoseCount();
		String mostExaminationHospitalName = userExaminationMetadataDetailRepository.findMostExaminationHospitalName();
				
		Page<HospitalDetailResponseDto> hospitalList = hospitalRepository.findBySearchTextAndPeriod(searchText, startDate, endDate,
				pageable);
		
		List<HospitalDetailResponseDto> content = hospitalList.getContent();
		
		for(HospitalDetailResponseDto responseDto : content) {
			LocalDateTime regDate = responseDto.getRegDate();
			responseDto.setRegDate2(regDate.plusHours(9L));
		}
		
		HospitalResponseDto responseDto = new HospitalResponseDto(hospitalCount, diagnoseCount, mostExaminationHospitalName, hospitalList);

		return responseDto;
	}

	// ------------------ HOSPITAL ------------------
	public HospitalDetailResponseDto getHospitalDetail(Long hospitalId) {

		HospitalDetailResponseDto responseDto = hospitalRepository.findDetailById(hospitalId);
		return responseDto;
	}

	public void updateHospitalDetail(HospitalUpdateRequestDto requestDto, MultipartFile[] files) {

		Long hospitalId = requestDto.getHospitalId();
		Hospital hospital = hospitalRepository.findById(hospitalId)
				.orElseThrow(() -> new IllegalArgumentException("No such hospital"));

		String groupFileId = requestDto.getGroupFileId();

		if (groupFileId != null) {
			String[] split = groupFileId.split("-");

			String groupId = split[0];
			Integer fileId = Integer.parseInt(split[1]);

			Optional<WebFiles> webFile = webFilesRepository.findByGroupIdAndFileId(groupId, fileId);

			if (webFile.isPresent()) {
				WebFiles webFileEntity = webFile.get();
				bucketService.delete(webFileEntity.getFileKey());
				webFilesRepository.delete(webFileEntity);
			}
		}

		if (files != null && files.length == 1) {
			fileUploadUtils.upload(files, "licensePhoto", FileUploadUserType.WEB, hospitalId, hospital.getGroupId());
		}

		hospital.updateHospitalInfo(requestDto.getHospitalName(), requestDto.getHospitalTel(), requestDto.getEmployeeTel(), requestDto.getAddress(),
				requestDto.getAddressDetail(), requestDto.getPostcode(), requestDto.getEmployeeEmail(),
				requestDto.getEmployeePosition(), requestDto.getEmployeeName(), requestDto.getAgreeSendYn(),
				requestDto.getProgramInUse(), requestDto.getSignagesStand(), requestDto.getSignagesMountable(),
				requestDto.getSignagesExisting());
		
		String password = requestDto.getPassword();
		
		if (password != null) {
			Admin admin = hospital.getAdmin();
			admin.setPassword(passwordEncoder.encode(password));
		}
	}

	public void deleteHospital(Long hospitalId) {
		Hospital hospital = hospitalRepository.findById(hospitalId)
				.orElseThrow(() -> new IllegalArgumentException("No such hospital"));
		
		hospital.setDelete();
	}

}
