package com.blink.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blink.domain.consentForm.WebConsentForm;
import com.blink.domain.consentForm.WebConsentFormRepository;
import com.blink.domain.hospital.Hospital;
import com.blink.domain.hospital.HospitalRepository;
import com.blink.domain.webfiles.WebFilesRepository;
import com.blink.enumeration.FileUploadUserType;
import com.blink.enumeration.ReceiveType;
import com.blink.util.FileUploadUtils;
import com.blink.web.admin.web.dto.WebFileResponseDto;
import com.blink.web.admin.web.dto.consentForm.AdminConsentFormDetailResponseDto;
import com.blink.web.admin.web.dto.consentForm.ConsentFormResponseDto;
import com.blink.web.hospital.dto.consentForm.HospitalConsentFormDetailResponseDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class WebConsentFormService {
	private final WebConsentFormRepository webConsentFormRepository;
	private final WebFilesRepository webFilesRepository;
	private final FileUploadUtils fileUploadUtils;
	private final HospitalRepository hospitalRepository;

	public void registerConsentForm(Long hospitalId, LocalDate receiveDate, ReceiveType receiveType,
			String receiveTypeText, String consentYear, String consentMonth, Long count, MultipartFile[] files) {

		Hospital hospital = hospitalRepository.findById(hospitalId)
				.orElseThrow(() -> new IllegalArgumentException("No such hospital"));

		String groupId = fileUploadUtils.upload(files, "webConsentFormFiles", FileUploadUserType.WEB, 0L, null);

		WebConsentForm webConsentForm = WebConsentForm.builder() //
				.hospital(hospital) //
				.receiveDate(receiveDate) //
				.receiveType(receiveType) //
				.receiveTypeText(receiveTypeText) //
				.consentYear(consentYear) //
				.consentMonth(consentMonth) //
				.groupId(groupId) //
				.count(count) //
				.build();

		webConsentFormRepository.save(webConsentForm);
	}

	public ConsentFormResponseDto getConsentForms(String searchText, Date startDate, Date endDate, Pageable pageable) {

		Page<AdminConsentFormDetailResponseDto> consentFormList = webConsentFormRepository
				.findBySearchTextAndPeriodForAdmin(searchText, startDate, endDate, pageable);

		List<AdminConsentFormDetailResponseDto> content = consentFormList.getContent();

		for (AdminConsentFormDetailResponseDto dto : content) {
			String groupId = dto.getGroupId();
			if (groupId != null) {

				List<WebFileResponseDto> files = webFilesRepository.findByGroupId(groupId);
				dto.setFiles(files);
			}
		}

		Integer totalCount = webConsentFormRepository.findTotalCountForAdmin();
		ConsentFormResponseDto responseDto = new ConsentFormResponseDto(totalCount, consentFormList);
		return responseDto;
	}

	public ConsentFormResponseDto getConsentFormsForHospital(Long hospitalId, String searchText, Date startDate, Date endDate,
			Pageable pageable) {

		Page<AdminConsentFormDetailResponseDto> consentFormList = webConsentFormRepository
				.findBySearchTextAndPeriodForHospital(hospitalId, startDate, endDate, pageable);
		List<AdminConsentFormDetailResponseDto> content = consentFormList.getContent();

		for (AdminConsentFormDetailResponseDto dto : content) {
			String groupId = dto.getGroupId();
			if (groupId != null) {

				List<WebFileResponseDto> files = webFilesRepository.findByGroupId(groupId);
				dto.setFiles(files);
			}
		}

		Integer totalCount = webConsentFormRepository.findTotalCountForHospital(hospitalId);
		ConsentFormResponseDto responseDto = new ConsentFormResponseDto(totalCount, consentFormList);
		return responseDto;
	}

	public void registerConsentFormForHospital(Long hospitalId, String consentYear, String consentMonth, Long count,
			MultipartFile[] files) {

		String groupId = fileUploadUtils.upload(files, "webConsentFormFiles", FileUploadUserType.WEB, hospitalId, null);

		WebConsentForm webConsentForm = WebConsentForm.builder() //
				.hospital(hospitalRepository.findById(hospitalId).get()) //
				.consentYear(consentYear) //
				.consentMonth(consentMonth) //
				.groupId(groupId) //
				.count(count) //
				.receiveDate(LocalDate.now()) //
				.receiveType(ReceiveType.WEBPAGE) //
				.build();

		webConsentFormRepository.save(webConsentForm);
	}

	public com.blink.web.hospital.dto.consentForm.ConsentFormResponseDto getConsentFormsForHospitalSelf(Long hospitalId,
			String searchText, Date startDate, Date endDate, Pageable pageable) {

		Page<HospitalConsentFormDetailResponseDto> consentFormList = webConsentFormRepository
				.findBySearchTextAndPeriodForHospitalSelf(hospitalId, startDate, endDate, pageable);

		List<HospitalConsentFormDetailResponseDto> content = consentFormList.getContent();

		for (HospitalConsentFormDetailResponseDto dto : content) {
			String groupId = dto.getGroupId();
			if (groupId != null) {

				List<WebFileResponseDto> files = webFilesRepository.findByGroupId(groupId);
				dto.setFiles(files);
			}
		}

		Integer totalCount = webConsentFormRepository.findTotalCountForHospital(hospitalId);
		com.blink.web.hospital.dto.consentForm.ConsentFormResponseDto responseDto = new com.blink.web.hospital.dto.consentForm.ConsentFormResponseDto(
				totalCount, consentFormList);
		return responseDto;
	}

}
