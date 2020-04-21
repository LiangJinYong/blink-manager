package com.blink.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.blink.domain.user.UserData;
import com.blink.domain.user.UserDataRepository;
import com.blink.domain.user.UserExaminationMetadata;
import com.blink.domain.user.UserExaminationMetadataRepository;
import com.blink.web.admin.web.dto.UserDataRequestDto;
import com.blink.web.admin.web.dto.UserExaminationMetadataRequestDto;
import com.blink.web.admin.web.dto.UserParserRequestDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class ParserService {

	private final UserDataRepository userDataRepository;
	private final UserExaminationMetadataRepository userExaminationMetadataRepository;
	
	public List<Map<String, Object>> save(List<UserParserRequestDto> userList) {
		
		List<Map<String, Object>> result = new ArrayList<>();
		
		for(UserParserRequestDto user: userList) {
			UserDataRequestDto userDataDto = user.getUserData();
			UserExaminationMetadataRequestDto userExaminationMetadataDto = user.getUserExaminationMetadata();
			String phone = userDataDto.getPhone();
			String ssnPartial = userDataDto.getSsnPartial();
			
			Optional<UserData> userData = null;
			
			if (!"".equals(phone) || phone == null) {
				userData = userDataRepository.findByPhone(phone);
			}
			
			if (userData != null && userData.isPresent()) {
				
				// 이름으로 한번 체크
				Optional<UserData> userDataByName = userDataRepository.findByPhoneAndSsnPartial(phone, ssnPartial);
				
				// 없으면 
				if (!userDataByName.isPresent()) {
					Map<String, Object> map = new HashMap<>();
					map.put("phone", phone);
					map.put("ssnPartial", ssnPartial);
					
					result.add(map);
				}
				
				System.out.println("==> UserData 등록됨");
				//userData 등록됨
				Integer examinationYear = userExaminationMetadataDto.getExaminationYear();
				Optional<UserExaminationMetadata> userExaminationMetadata = userExaminationMetadataRepository.findByUserAndExaminationYear(userData, examinationYear);
				
				if (userExaminationMetadata.isPresent()) {
					System.out.println("==> UserExaminationMetadata 업데이트");
					// 기록 등록됨 -> 업데이트
					Integer agreeYn = userExaminationMetadataDto.getAgreeYn();
					String dateExamined = userExaminationMetadataDto.getDateExamined();
					Long hospitalDataId = userExaminationMetadataDto.getHospitalDataId();
					Integer agreeMail = userExaminationMetadataDto.getAgreeMail();
					Integer agreeSms = userExaminationMetadataDto.getAgreeSms();
					Integer agreeVisit = userExaminationMetadataDto.getAgreeVisit();
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					
					userExaminationMetadata.get().update(agreeYn, LocalDate.parse(dateExamined, formatter), hospitalDataId, agreeMail, agreeSms, agreeVisit, examinationYear);
					
				} else {
					// 기록 미동록
					System.out.println("==> UserExaminationMetadata 미동록");
					UserExaminationMetadata userExaminationMetadataEntity = userExaminationMetadataDto.toEntity();
					userExaminationMetadataEntity.setUserData(userData.get());
					
					userExaminationMetadataRepository.save(userExaminationMetadataEntity);
				}
			} else {
				System.out.println("==> UserData 미동록");
				//userData 미등록
				UserData userDataEntity = userDataDto.toEntity();
				userDataEntity = userDataRepository.save(userDataEntity);
				
				UserExaminationMetadata userExaminationMetadataEntity = userExaminationMetadataDto.toEntity();
				userExaminationMetadataEntity.setUserData(userDataEntity);
				
				userExaminationMetadataRepository.save(userExaminationMetadataEntity);
			}
		}
		
		return result;
	}

}
