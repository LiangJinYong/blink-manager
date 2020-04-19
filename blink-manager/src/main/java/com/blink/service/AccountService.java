package com.blink.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blink.config.aws.FileResource;
import com.blink.domain.admin.Admin;
import com.blink.domain.admin.AdminRepository;
import com.blink.domain.authCode.UserAuthCode;
import com.blink.domain.authCode.UserAuthCodeRepository;
import com.blink.domain.hospital.Hospital;
import com.blink.domain.hospital.HospitalRepository;
import com.blink.domain.judge.WebJudge;
import com.blink.domain.judge.WebJudgeRepository;
import com.blink.domain.webfiles.WebFiles;
import com.blink.domain.webfiles.WebFilesRepository;
import com.blink.enumeration.FileUploadUserType;
import com.blink.enumeration.JudgeStatus;
import com.blink.enumeration.Role;
import com.blink.service.aws.BucketService;
import com.blink.service.aws.remote.FeignAuthMTService;
import com.blink.util.CommonUtils;
import com.blink.web.admin.web.dto.AuthCodeMTDto;
import com.blink.web.admin.web.dto.UserSignupRequestDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class AccountService {

	@Value("${admin.display.name}")
	private String adminDisplayName;

	@Value("${auth.code.length}")
	private int authCodeLength;

	@Value("${auth.code.expire.second}")
	private int authCodeExpireSecond;

	private final AdminRepository adminRepo;

	private final HospitalRepository hospitalRepo;

	private final WebFilesRepository webFilesRepo;

	private final UserAuthCodeRepository userAuthCodeRepo;

	private final WebJudgeRepository webJudgeRepo;

	private final PasswordEncoder passwordEncoder;

	private final BucketService bucketService;

	private final FeignAuthMTService mtService;

	public Long signupUser(UserSignupRequestDto requestDto, MultipartFile file) {
		
		// image upload
		String groupId = webFilesRepo.findFileGroupId("fileGroup");

		Hospital hospital = requestDto.toHospitalEntity(requestDto.getHospitalName(), requestDto.getHospitalTel(),
				requestDto.getPostcode(), requestDto.getAddress(), requestDto.getAddressDetail(),
				requestDto.getEmployeeName(), requestDto.getEmployeePosition(), requestDto.getEmployeeTel(),
				requestDto.getEmployeeEmail(), requestDto.getAgreeSendYn(), requestDto.getProgramInUse());

		hospital.assignGroupId(groupId);
		
		hospital = hospitalRepo.save(hospital);

		Admin admin = requestDto.toAdminEntity(requestDto.getUsername(),
				passwordEncoder.encode(requestDto.getPassword()), requestDto.getEmployeeEmail());
		admin.setHospital(hospital);

		admin = adminRepo.save(admin);
		
		Optional<UserAuthCode> userAuthCode = userAuthCodeRepo.findByPhoneNumber(requestDto.getEmployeeTel());
		userAuthCode.get().setSignupYn(true);

		WebJudge webJudge = WebJudge.builder() //
				.hospital(hospital) //
				.judgeStatus(JudgeStatus.WAITING) //
				.build();

		webJudgeRepo.save(webJudge);

		try {
			FileResource fileResource = bucketService.upload(Optional.of("licensePhoto"), file);
			String imgUrlKey = fileResource.getKey();
			long fileSize = file.getSize();
			String filename = file.getOriginalFilename();

			WebFiles webFiles = WebFiles.builder() //
					.groupId(groupId) //
					.fileId(1) //
					.fileKey(imgUrlKey) //
					.fileName(filename) //
					.uploadUserType(FileUploadUserType.WEB) //
					.uploadUserId(hospital.getId()) //
					.fileSize(fileSize) //
					.build();
			
			webFilesRepo.save(webFiles);

		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}

		return admin.getId();
	}

	public void resetLoginTryCount(String username) {
		Admin admin = adminRepo.findByName(username);
		admin.resetLoginTryCount();
	}

	public int increaseLoginTryCount(String username) {
		Admin admin = adminRepo.findByName(username);
		admin.increaseLoginTryCount();
		return admin.getLoginTryCnt();
	}

	public boolean userExists(String username) {
		int count = adminRepo.findCountByName(username);
		return count > 0;
	}

	public boolean isAuthCodeSendable(String employeeTel) {
		Optional<UserAuthCode> userAuthCode = userAuthCodeRepo.findByPhoneNumber(employeeTel);
		// 인증번호 테이블에 해당 핸드폰 기록이 없거나, 있는 경우에 가입여부가 가짜일 경우에 인증코드를 보낼수 있다
		return !userAuthCode.isPresent() || (userAuthCode.isPresent() && !userAuthCode.get().isSignupYn());
	}

	public void sendAuthCode(String employeeTel) {
		int authCode = CommonUtils.createRandomNumber(authCodeLength);

		AuthCodeMTDto authCodeMTDto = AuthCodeMTDto.builder() //
				.authCode(String.valueOf(authCode)) //
				.phoneNumber(employeeTel) //
				.build();

		mtService.sendAuthCodeMT(authCodeMTDto);

		Optional<UserAuthCode> userAuthCode = userAuthCodeRepo.findByPhoneNumber(employeeTel);

		if (userAuthCode.isPresent()) {
			UserAuthCode userAuthcodeEntity = userAuthCode.get();
			userAuthcodeEntity.reSetAuthCode(authCode);
			userAuthcodeEntity.reSetExpireDate(LocalDateTime.now().plusSeconds(authCodeExpireSecond));
		} else {
			UserAuthCode userAuthCodeEntity = UserAuthCode.builder() //
					.authCode(authCode) //
					.expireDate(LocalDateTime.now().plusSeconds(authCodeExpireSecond)).phoneNumber(employeeTel)
					.authYn(false).signupYn(false).build();

			userAuthCodeRepo.save(userAuthCodeEntity);
		}
	}

	public boolean checkAuthCode(String employeeTel, Integer authCode) {

		Optional<UserAuthCode> userAuthCode = userAuthCodeRepo.findValidAuthCode(employeeTel, authCode,
				LocalDateTime.now());
		boolean checked = userAuthCode.isPresent();
		if (checked) {
			userAuthCode.get().setAuthYn(true);
		}

		return checked;
	}

	public boolean isSignupAble(String employeeTel) {
		Optional<UserAuthCode> userAuthCode = userAuthCodeRepo.findByEmployeeTelAndAuthYn(employeeTel);
		return userAuthCode.isPresent();
	}

	public Map<String, Object> getUserStatus(String username) {

		Map<String, Object> userStatus = new HashMap<String, Object>();

		Admin user = adminRepo.findByName(username);

		if (user.getRoleId() == Role.MASTER) {
			userStatus.put("displayName", adminDisplayName);
		} else {
			String displayName = user.getHospital().getDisplayName();
			userStatus.put("displayName", displayName);
		}

		boolean accountStatus = user.isAccountStatus();
		userStatus.put("accountStatus", accountStatus);
		if (!accountStatus) {
			JudgeStatus judgeStatus = user.getHospital().getWebJudge().getJudgeStatus();
			userStatus.put("judgeStatus", judgeStatus);

			if (judgeStatus.equals(JudgeStatus.DENIED)) {
				userStatus.put("rejectMsg", user.getHospital().getWebJudge().getRejectMsg());
			}
		}

		return userStatus;
	}

	public boolean isAccountValid(String username, String employeeTel) {
		int accountCount = hospitalRepo.findCountByNameAndEmployeeTel(username, employeeTel);
		return accountCount > 0;
	}

	public String getUsername(String employeeTel) {
		List<Hospital> list = hospitalRepo.findByEmployeeTel(employeeTel);
		return list.get(0).getName();
	}

	public void updatePassword(String employeeTel, String tempPassword) {
		
		List<Hospital> list = hospitalRepo.findByEmployeeTel(employeeTel);
		if (list.size() > 0) {
			Admin admin = list.get(0).getAdmin();
			admin.setTempPassword(passwordEncoder.encode(tempPassword));
		} else {
			throw new RuntimeException("No such account");
		}
	}

	public void updateEmail(String username, String newEmail) {
		Admin admin = adminRepo.findByName(username);
		
		if (admin != null) {
			admin.modifyEmail(newEmail);
			
			Hospital hospital = admin.getHospital();
			hospital.modifyEmaail(newEmail);
		} else {
			throw new RuntimeException("No such hospital");
		}
	}
}
