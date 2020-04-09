package com.blink.service;

import java.io.IOException;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blink.common.FileUploadUserType;
import com.blink.config.aws.FileResource;
import com.blink.domain.admin.Admin;
import com.blink.domain.admin.AdminRepository;
import com.blink.domain.hospital.Hospital;
import com.blink.domain.hospital.HospitalRepository;
import com.blink.domain.webfiles.WebFiles;
import com.blink.domain.webfiles.WebFilesRepository;
import com.blink.service.aws.BucketService;
import com.blink.web.dto.UserSignupRequestDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final AdminRepository adminRepo;
	
	private final HospitalRepository hospitalRepo;
	
	private final PasswordEncoder passwordEncoder;
	
	private final BucketService bucketService;
	
	private final WebFilesRepository webFilesRepository;
	
	@Transactional
	public Long signupUser(UserSignupRequestDto requestDto, MultipartFile file) {
		
		String groupId = webFilesRepository.findFileGroupId("licensePhoto");

		try {
			
			FileResource fileResource = bucketService.upload(Optional.of("licensePhoto"), file);
			String imgUrlKey = fileResource.getKey();
			long fileSize = file.getSize();
			String filename = file.getOriginalFilename();
			
			WebFiles webFiles = WebFiles.builder()
					.groupId(groupId)
					.fileId(1)
					.fileKey(imgUrlKey)
					.fileName(filename)
					.uploadUserType(FileUploadUserType.APP)
					.uploadUserId(1L)
					.fileSize(fileSize)
					.build();
			webFilesRepository.save(webFiles);
			
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}
		
		Hospital hospital = requestDto.toHospitalEntity(requestDto.getHospitalName()
				, requestDto.getHospitalTel()
				, requestDto.getPostcode()
				, requestDto.getAddress()
				, requestDto.getAddressDetail()
				, requestDto.getEmployeeName()
				, requestDto.getEmployeePosition()
				, requestDto.getEmployeeTel()
				, requestDto.getEmployeeEmail()
				, requestDto.getAgreenSendYn()
				, requestDto.getProgramInUse());
		
		hospital = hospitalRepo.save(hospital);
		
		Admin admin = requestDto.toAdminEntity(requestDto.getUserId()
								, passwordEncoder.encode(requestDto.getPassword())
								, requestDto.getEmployeeEmail());
		admin.setHospital(hospital);
		
		return adminRepo.save(admin).getId();
	}

}
