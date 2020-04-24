package com.blink.service;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blink.domain.hospital.Hospital;
import com.blink.domain.hospital.HospitalRepository;
import com.blink.domain.webfiles.WebFiles;
import com.blink.domain.webfiles.WebFilesRepository;
import com.blink.enumeration.FileUploadUserType;
import com.blink.enumeration.SearchPeriod;
import com.blink.service.aws.BucketService;
import com.blink.util.CommonUtils;
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

	// ------------------ ADMIN ------------------
	public HospitalResponseDto getHospitalList(String searchText, SearchPeriod period, Pageable pageable) {

		LocalDateTime time = CommonUtils.getSearchPeriod(period);

		Page<HospitalDetailResponseDto> hospitalList = hospitalRepository.findBySearchTextAndPeriod(searchText, time,
				pageable);
		HospitalResponseDto responseDto = new HospitalResponseDto(0, 0, "xxx", hospitalList);

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

		if (files.length == 1) {
			fileUploadUtils.upload(files, "licensePhoto", FileUploadUserType.WEB, hospitalId, hospital.getGroupId());
		}

		hospital.updateHospitalInfo(requestDto.getHospitalName(), requestDto.getEmployeeTel(), requestDto.getAddress(),
				requestDto.getAddressDetail(), requestDto.getPostcode(), requestDto.getEmployeeEmail(),
				requestDto.getEmployeePosition(), requestDto.getEmployeeName(), requestDto.getAgreeSendYn(),
				requestDto.getProgramInUse(), requestDto.getSignagesStand(), requestDto.getSignagesMountable(),
				requestDto.getSignagesExisting());
	}

	public void deleteHospital(Long hospitalId) {
		Hospital hospital = hospitalRepository.findById(hospitalId)
				.orElseThrow(() -> new IllegalArgumentException("No such hospital"));
		
		hospital.setDelete();
	}

}
