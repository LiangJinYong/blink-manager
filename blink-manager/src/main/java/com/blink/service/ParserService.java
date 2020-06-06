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
import org.springframework.web.multipart.MultipartFile;

import com.blink.domain.data.BloodData;
import com.blink.domain.data.BloodDataRepository;
import com.blink.domain.data.BoneData;
import com.blink.domain.data.BoneDataRepository;
import com.blink.domain.data.CancerData;
import com.blink.domain.data.CancerDataApp;
import com.blink.domain.data.CancerDataAppRepository;
import com.blink.domain.data.CancerDataRepository;
import com.blink.domain.data.CommentData;
import com.blink.domain.data.CommentDataRepository;
import com.blink.domain.data.ElderfunctionData;
import com.blink.domain.data.ElderfunctionDataRepository;
import com.blink.domain.data.HepatitisData;
import com.blink.domain.data.HepatitisDataRepository;
import com.blink.domain.data.InstrumentationData;
import com.blink.domain.data.InstrumentationDataRepository;
import com.blink.domain.data.MentalData;
import com.blink.domain.data.MentalDataRepository;
import com.blink.domain.data.RadiologyData;
import com.blink.domain.data.RadiologyDataRepository;
import com.blink.domain.data.SurveyData;
import com.blink.domain.data.SurveyDataRepository;
import com.blink.domain.data.UrineData;
import com.blink.domain.data.UrineDataRepository;
import com.blink.domain.examinationResultDocMobile.WebExaminationResultDocMobile;
import com.blink.domain.examinationResultDocMobile.WebExaminationResultDocMobileRepository;
import com.blink.domain.hospital.Hospital;
import com.blink.domain.hospital.HospitalRepository;
import com.blink.domain.json.JsonIndividualApi;
import com.blink.domain.json.JsonIndividualApiRepository;
import com.blink.domain.pdf.PdfIndividualWeb;
import com.blink.domain.pdf.PdfIndividualWebRepository;
import com.blink.domain.pdf.PdfWeb;
import com.blink.domain.pdf.PdfWebRepository;
import com.blink.domain.sendMailResultWeb.FileInfo;
import com.blink.domain.user.UserData;
import com.blink.domain.user.UserDataRepository;
import com.blink.domain.user.UserExaminationEntireDataOfOne;
import com.blink.domain.user.UserExaminationEntireDataOfOneRepository;
import com.blink.domain.user.UserExaminationMetadata;
import com.blink.domain.user.UserExaminationMetadataDetail;
import com.blink.domain.user.UserExaminationMetadataDetailRepository;
import com.blink.domain.user.UserExaminationMetadataRepository;
import com.blink.enumeration.CancerAnalyzerStatus;
import com.blink.enumeration.CancerType;
import com.blink.enumeration.Gender;
import com.blink.enumeration.InspectionSubType;
import com.blink.enumeration.InspectionType;
import com.blink.enumeration.Nationality;
import com.blink.util.FileUploadUtils;
import com.blink.web.admin.web.dto.UserDataRequestDto;
import com.blink.web.admin.web.dto.UserExaminationMetadataRequestDto;
import com.blink.web.admin.web.dto.UserParserRequestDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class ParserService {

	private final HospitalRepository hospitalRepository;
	
	private final UserDataRepository userDataRepository;
	private final UserExaminationMetadataRepository userExaminationMetadataRepository;

	private final CancerDataRepository cancerDataRepository;
	private final BloodDataRepository bloodDataRepository;
	private final RadiologyDataRepository radiologyDataRepository;
	private final UrineDataRepository urineDataRepository;
	private final CommentDataRepository commentDataRepository;
	private final InstrumentationDataRepository instrumentationDataRepository;
	private final SurveyDataRepository surveyDataRepository;
	private final HepatitisDataRepository hepatitisDataRepository;
	private final MentalDataRepository mentalDataRepository;
	private final BoneDataRepository boneDataRepository;
	private final ElderfunctionDataRepository elderfunctionDataRepository;
	private final CancerDataAppRepository cancerDataAppRepository;

	private final UserExaminationEntireDataOfOneRepository userExaminationEntireDataOfOneRepository;
	private final UserExaminationMetadataDetailRepository userExaminationMetadataDetailRepository;

	private final PdfWebRepository pdfWebRepository;
	private final PdfIndividualWebRepository pdfIndividualWebRepository;
	private final JsonIndividualApiRepository jsonIndividualApiRepository;
	
	private final WebExaminationResultDocMobileRepository resultDocMobileRepository;

	private final FileUploadUtils fileUploadUtils;
	
	private UserExaminationMetadata metadataForMobile;

	public List<Map<String, Object>> save(List<UserParserRequestDto> userList) {

		List<Map<String, Object>> result = new ArrayList<>();
		
		for (UserParserRequestDto user : userList) {
			UserDataRequestDto userDataDto = user.getUserData();
			UserExaminationMetadataRequestDto userExaminationMetadataDto = user.getUserExaminationMetadata();
			
			String phone = userDataDto.getPhone();
			String ssnPartial = userDataDto.getSsnPartial();
			String userName = userDataDto.getName();
			
			List<UserData> userDataList= userDataRepository.findByPhoneAndSsnPartial(phone, ssnPartial);
			
			if (userDataList.size() > 0) {
				Integer count = userDataRepository.findUserDataCountByPhoneAndSsnPartial(phone, ssnPartial);
				
				if (count == 1) {
					UserData userData = userDataList.get(0);
					String name = userData.getName();
					if (name == null || "".equals(name)) {
						userData.setName(userName);
					}
					
					Integer examinationYear = userExaminationMetadataDto.getExaminationYear();
					
					List<UserExaminationMetadata> metadataList = userExaminationMetadataRepository
							.findByUserAndExaminationYear(userData, examinationYear);
					
					if (metadataList.size() == 1) {
						UserExaminationMetadata metadata = metadataList.get(0);
						Long hospitalDataId = userExaminationMetadataDto.getHospitalDataId();
						
						String dateExamined = userExaminationMetadataDto.getDateExamined();
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
						
						Integer agreeYn = metadata.getAgreeYn();
						Integer agreeMail = metadata.getAgreeMail();
						Integer agreeSms = metadata.getAgreeSms();
						Integer agreeVisit = metadata.getAgreeVisit();
						
						if ((agreeYn != null && agreeYn == 0) && (agreeMail != null && agreeMail == 1) && (agreeSms != null && agreeSms == 1) && (agreeVisit != null && agreeVisit == 0)) {
							// update all
							metadata.update(LocalDate.parse(dateExamined, formatter), hospitalDataId, examinationYear, agreeYn, agreeMail, agreeSms, agreeVisit);
						} else {
							// update all except agreeYn, agreeMail, agreeSms, agreeVisit
							metadata.update(LocalDate.parse(dateExamined, formatter), hospitalDataId, examinationYear);
						}
					} else if (metadataList.size() == 0) {
						UserExaminationMetadata userExaminationMetadataEntity = userExaminationMetadataDto.toEntity();
						userExaminationMetadataEntity.setUserData(userDataList.get(0));

						userExaminationMetadataRepository.save(userExaminationMetadataEntity);
					} else {
						Map<String, Object> existedMultipleMetadatas = new HashMap<>();
						existedMultipleMetadatas.put("phone", phone);
						existedMultipleMetadatas.put("ssnPartial", ssnPartial);
						existedMultipleMetadatas.put("errorType", "Multiple metadatas exist");
						result.add(existedMultipleMetadatas);
						continue;
					}
				} else if (count > 1) {
					Map<String, Object> existedMultipleUsers = new HashMap<>();
					existedMultipleUsers.put("phone", phone);
					existedMultipleUsers.put("ssnPartial", ssnPartial);
					
					result.add(existedMultipleUsers);
					continue;
				}
			} else {
				UserData userDataEntity = userDataDto.toEntity();
				userDataEntity = userDataRepository.save(userDataEntity);

				UserExaminationMetadata userExaminationMetadataEntity = userExaminationMetadataDto.toEntity();
				userExaminationMetadataEntity.setUserData(userDataEntity);

				userExaminationMetadataRepository.save(userExaminationMetadataEntity);
			}
		}
		return result;
	}

	public List<Map<String, Object>> registerExaminationDataMobile(Long examinationResultDocMobileId, Map<String, Object> param) {
		
		List<Map<String, Object>> userExaminationData = (List<Map<String, Object>>) param.get("data");
		
		List<Map<String, Object>> registerExaminationDataResult = registerExaminationData(userExaminationData);
		
		if (registerExaminationDataResult.size() == 0) {
			WebExaminationResultDocMobile resultDocMobile = resultDocMobileRepository.findById(examinationResultDocMobileId).orElseThrow(() -> new IllegalArgumentException("No such mobile examination result doc"));
			
			Integer hospitalId = (Integer) param.get("hospitalId");
			Hospital hospital = hospitalRepository.findById(hospitalId.longValue()).orElseThrow(() -> new IllegalArgumentException("No such hospital"));
			String hospitalName = (String) param.get("hospitalName");
			String hospitalAddress = (String) param.get("hospitalAddress");
			String hospitalPostcode = (String) param.get("hospitalPostcode");
			Integer status = (Integer) param.get("status");
			Integer resultStatus = (Integer) param.get("resultStatus");
			
			String pushMsg = (String) param.get("pushMsg");
			String appMsg1 = (String) param.get("appMsg1");
			String appMsg2 = (String) param.get("appMsg2");
			
			resultDocMobile.update(hospital, metadataForMobile, status, hospitalName, hospitalAddress, hospitalPostcode, resultStatus, pushMsg, appMsg1, appMsg2);
		}
		
		return registerExaminationDataResult;
	}
	
	public List<Map<String, Object>> registerExaminationData(List<Map<String, Object>> param) {

		List<Map<String, Object>> result = new ArrayList<>();
		
		for (Map<String, Object> map : param) {

			Map<String, String> userDataMap = (Map<String, String>) map.get("userData");

			String phone = userDataMap.get("phone");
			String ssnPartial = userDataMap.get("ssnPartial");

			List<UserData> userData = userDataRepository.findByPhoneAndSsnPartial(phone, ssnPartial);

			if (userData.size() == 1) {
				Integer examinationYear = Integer.parseInt((String) map.get("examinationYear"));
				Integer inspectionType = Integer.parseInt((String) map.get("inspectionType"));
				Integer inspectionSubType = Integer.parseInt((String) map.get("inspectionSubType"));

				String hospitalDataId = (String) map.get("hospitalDataId");
				// DB에 해당 연도에 존재하는 metadata
				List<UserExaminationMetadata> metadataList = userExaminationMetadataRepository
						.findByUserDataAndExaminationYearAndHospitalDataId(userData.get(0), examinationYear, Long.parseLong(hospitalDataId));

				if (metadataList.size() == 1) {
					// update address
					UserExaminationMetadata metadataEntity = metadataList.get(0);
					metadataForMobile = metadataEntity;
					String userAddress = (String) map.get("userAddress");
					metadataEntity.updateAddress(userAddress);
					
					Map<String, Object> userExaminationMetadata = (Map<String, Object>) map.get("userExaminationMetadata");
					String dateExamined = (String) userExaminationMetadata.get("dateExamined");
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					
					Integer agreeYnParam = (Integer) userExaminationMetadata.get("agreeYn");
					Integer agreeMailParam = (Integer) userExaminationMetadata.get("agreeMail");
					Integer agreeSmsParam = (Integer) userExaminationMetadata.get("agreeSms");
					Integer agreeVisitParam = (Integer) userExaminationMetadata.get("agreeVisit");
					
					Integer agreeYn = metadataEntity.getAgreeYn();
					Integer agreeMail = metadataEntity.getAgreeMail();
					Integer agreeSms = metadataEntity.getAgreeSms();
					Integer agreeVisit = metadataEntity.getAgreeVisit();
					
					if ((agreeYn != null && agreeYn == 0) && (agreeMail != null && agreeMail == 1) && (agreeSms != null && agreeSms == 1) && (agreeVisit != null && agreeVisit == 0)) {
						// update all
						metadataEntity.update(LocalDate.parse(dateExamined, formatter), Long.parseLong(hospitalDataId), examinationYear, agreeYnParam, agreeMailParam, agreeSmsParam, agreeVisitParam);
					} else {
						// update all except agreeYn, agreeMail, agreeSms, agreeVisit
						metadataEntity.update(LocalDate.parse(dateExamined, formatter), Long.parseLong(hospitalDataId), examinationYear);
					}
					
					List<UserExaminationMetadataDetail> metadataDetailList = userExaminationMetadataDetailRepository
							.findByMetaDataAndExaminationYearAndType(metadataEntity.getId(), examinationYear,
									inspectionType, inspectionSubType);

					if (metadataDetailList.size() == 1) {
						saveMetadataDetail(map, metadataEntity, metadataDetailList.get(0));
					} else if (metadataDetailList.size() == 0) {
						saveMetadataDetail(map, metadataEntity, null);
					} else {
						Map<String, Object> existedMultipleMetadataDetail = new HashMap<>();
						existedMultipleMetadataDetail.put("phone", phone);
						existedMultipleMetadataDetail.put("ssnPartial", ssnPartial);
						StringBuilder idBuilder = new StringBuilder();
						for(UserExaminationMetadataDetail metadataDetail : metadataDetailList) {
							Long id = metadataDetail.getId();
							idBuilder.append(id+"-");
						}

						existedMultipleMetadataDetail.put("existingMetadataDetailIds", idBuilder.toString());
						existedMultipleMetadataDetail.put("errorType", "multiple metadata exist");
						result.add(existedMultipleMetadataDetail);
						continue;
					}

					UserExaminationEntireDataOfOne dataOfOne = metadataEntity.getUserExaminationEntireDataOfOne();
					dataOfOne = saveDataOfOne(map, dataOfOne, userData.get(0));

					metadataEntity.setUserExaminationEntireDataOfOne(dataOfOne);

					saveCancerDataApp(map, dataOfOne);
				} else if(metadataList.size() == 0) {
					UserExaminationEntireDataOfOne newDataOfOne = saveDataOfOne(map, null, userData.get(0));
					UserExaminationMetadata newMetaData = saveMetadata(map, newDataOfOne, userData.get(0));
					saveMetadataDetail(map, newMetaData, null);
					saveCancerDataApp(map, newDataOfOne);
				} else {
					Map<String, Object> existedMultipleMetadata = new HashMap<>();
					existedMultipleMetadata.put("phone", phone);
					existedMultipleMetadata.put("ssnPartial", ssnPartial);
					StringBuilder idBuilder = new StringBuilder();
					for(UserExaminationMetadata metadata : metadataList) {
						Long id = metadata.getId();
						idBuilder.append(id+"-");
					}
					existedMultipleMetadata.put("existingMetadataIds", idBuilder.toString());
					existedMultipleMetadata.put("errorType", "multiple metadata exist");
					
					result.add(existedMultipleMetadata);
					continue;
				}
			} else if (userData.size() == 0) {
				
				String birthday = userDataMap.get("birthday");
				String gender = userDataMap.get("gender");
				String nationality = userDataMap.get("nationality");
				
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		        LocalDate birthdayDate = LocalDate.parse(birthday, formatter);
				
				UserData newUserEntity = UserData.builder() //
						.name("") //
						.nationality(Nationality.values()[Integer.parseInt(nationality)]) //
						.phone(phone) //
						.birthday(birthdayDate) //
						.gender(Gender.values()[Integer.parseInt(gender)]) //
						.ssnPartial(ssnPartial) //
						.build();

				newUserEntity = userDataRepository.save(newUserEntity);

				UserExaminationEntireDataOfOne newDataOfOne = saveDataOfOne(map, null, newUserEntity);
				UserExaminationMetadata newMetaData = saveMetadata(map, newDataOfOne, newUserEntity);
				saveMetadataDetail(map, newMetaData, null);
				saveCancerDataApp(map, newDataOfOne);
			} else {
				Map<String, Object> existedMultipleUsers = new HashMap<>();
				existedMultipleUsers.put("phone", phone);
				existedMultipleUsers.put("ssnPartial", ssnPartial);
				existedMultipleUsers.put("errorType", "multiple user exist");
				
				result.add(existedMultipleUsers);
				continue;
			}
		}
		
		return result;
	}

	private void saveCancerDataApp(Map<String, Object> map, UserExaminationEntireDataOfOne dataOfOne) {

		Map<String, Map<String, Object>> dataOfOneMap = (Map<String, Map<String, Object>>) map
				.get("userExaminationEntireDataOfOne");

		if (dataOfOneMap != null) {
			Map<String, Object> cancerDataAppMap = dataOfOneMap.get("cancerDataApp");

			if (cancerDataAppMap != null) {
				String caseCondition1 = (String) cancerDataAppMap.get("caseCondition1");
				String caseCondition2 = (String) cancerDataAppMap.get("caseCondition2");
				String caseCondition3 = (String) cancerDataAppMap.get("caseCondition3");
				String caseCondition4 = (String) cancerDataAppMap.get("caseCondition4");
				String title = (String) cancerDataAppMap.get("title");
				String description = (String) cancerDataAppMap.get("description");
				String childLeft = (String) cancerDataAppMap.get("childLeft");
				String childDescription = (String) cancerDataAppMap.get("childDescription");
				String childRight = (String) cancerDataAppMap.get("childRight");
				String comment = (String) cancerDataAppMap.get("comment");
				String postComment = (String) cancerDataAppMap.get("postComment");
				String preComment = (String) cancerDataAppMap.get("preComment");
				Object analyzerStatusObj = cancerDataAppMap.get("analyzerStatus");
				CancerAnalyzerStatus analyzerStatus = null;
				if (analyzerStatusObj != null) {
					analyzerStatus = CancerAnalyzerStatus.valueOf((String) analyzerStatusObj);
				}

				String hadCancer = (String) cancerDataAppMap.get("hadCancer");

				Object typeObj = cancerDataAppMap.get("type");

				CancerType type = null;
				if (typeObj != null) {
					type = CancerType.valueOf((String) typeObj);
				}

				Optional<CancerDataApp> cancerDataApp = cancerDataAppRepository
						.findByTypeAndUserExaminationEntireDataOfOne(type, dataOfOne);

				if (!cancerDataApp.isPresent()) {
					CancerDataApp cancerDataAppEntity = CancerDataApp.builder() //
							.userExaminationEntireDataOfOne(dataOfOne) //
							.caseCondition1(caseCondition1) //
							.caseCondition2(caseCondition2) //
							.caseCondition3(caseCondition3) //
							.caseCondition4(caseCondition4) //
							.title(title) //
							.description(description) //
							.childLeft(childLeft) //
							.childDescription(childDescription) //
							.childRight(childRight) //
							.comment(comment) //
							.postComment(postComment) //
							.preComment(preComment) //
							.analyzerStatus(analyzerStatus) //
							.hadCancer(hadCancer) //
							.type(type) //
							.build();

					cancerDataAppRepository.save(cancerDataAppEntity);
				} else {
					cancerDataApp.get().update(caseCondition1, caseCondition2, caseCondition3, caseCondition4, title,
							description, childLeft, childDescription, childRight, comment, postComment, preComment,
							analyzerStatus, hadCancer);
				}
			}
		}

	}

	private void saveMetadataDetail(Map<String, Object> map, UserExaminationMetadata metadata,
			UserExaminationMetadataDetail metadataDetail) {

		String dateExamined = (String) map.get("dateExamined");
		LocalDate dateExaminedDate = LocalDate.parse(dateExamined);

		String inspectionType = (String) map.get("inspectionType");
		String inspectionSubType = (String) map.get("inspectionSubType");

		String doctorName = (String) map.get("doctorName");
		String doctorLicenseNumber = (String) map.get("doctorLicenseNumber");
		String inspectionPlace = (String) map.get("inspectionPlace");
		String dateInterpreted = (String) map.get("dateInterpreted");
		LocalDate dateInterpretedDate = null;
		if (!"".equals(dateInterpreted)) {
			dateInterpretedDate = LocalDate.parse(dateInterpreted);
		}

		if (metadataDetail == null) {
			UserExaminationMetadataDetail metadataDetailEntity = UserExaminationMetadataDetail.builder() //
					.userExaminationMetadata(metadata) //
					.inspectionType(InspectionType.values()[Integer.parseInt(inspectionType)]) //
					.inspectionSubType(InspectionSubType.values()[Integer.parseInt(inspectionSubType)]) //
					.dateExamined(dateExaminedDate) //
					.dateInterpreted(dateInterpretedDate) //
					.doctorName(doctorName) //
					.doctorLicenseNumber(doctorLicenseNumber) //
					.inspectionPlace(inspectionPlace) //
					.build();

			metadataDetailEntity = userExaminationMetadataDetailRepository.save(metadataDetailEntity);
		} else {
			metadataDetail.updateDetailInfo(dateExaminedDate, inspectionPlace, doctorName, doctorLicenseNumber,
					dateInterpretedDate);
		}
	}

	private UserExaminationMetadata saveMetadata(Map<String, Object> map, UserExaminationEntireDataOfOne dataOfOne,
			UserData userData) {
		String address = (String) map.get("userAddress");
		String dateExamined = (String) map.get("dateExamined");
		LocalDate dateExaminedDate = LocalDate.parse(dateExamined);
		String examinationYear = (String) map.get("examinationYear");
		String hospitalDataId = (String) map.get("hospitalDataId");
		
		Map<String, Object> userExaminationMetadata = (Map<String, Object>) map.get("userExaminationMetadata");
		
		Integer agreeYn = (Integer) userExaminationMetadata.get("agreeYn");
		Integer agreeMail = (Integer) userExaminationMetadata.get("agreeMail");
		Integer agreeSms = (Integer) userExaminationMetadata.get("agreeSms");
		Integer agreeVisit = (Integer) userExaminationMetadata.get("agreeVisit");

		UserExaminationMetadata userExaminationMetadataEntity = UserExaminationMetadata.builder() //
				.userData(userData) //
				.examinationYear(Integer.parseInt(examinationYear)) //
				.dateExamined(dateExaminedDate) //
				.hospitalDataId(Long.parseLong(hospitalDataId)) //
				.address(address) //
				.userExaminationEntireDataOfOne(dataOfOne) //
				.agreeYn(agreeYn) //
				.agreeMail(agreeMail) //
				.agreeSms(agreeSms) //
				.agreeVisit(agreeVisit) //
				.build();
		
		userExaminationMetadataEntity = userExaminationMetadataRepository.save(userExaminationMetadataEntity);
		metadataForMobile = userExaminationMetadataEntity;
		System.out.println("userExaminationMetadata: " + userExaminationMetadataEntity.getId());
		return userExaminationMetadataEntity;
	}

	private UserExaminationEntireDataOfOne saveDataOfOne(Map<String, Object> map,
			UserExaminationEntireDataOfOne dataOfOne, UserData userData) {
		Map<String, Map<String, String>> dataItem = (Map<String, Map<String, String>>) map
				.get("userExaminationEntireDataOfOne");

		if (dataOfOne == null) {
			dataOfOne = new UserExaminationEntireDataOfOne();

			// cancerData
			CancerData cancerData = insertCancerData(dataItem);
			dataOfOne.setCancerData(cancerData);

			// bloodData
			BloodData bloodDataEntity = insertBloodData(dataItem);
			dataOfOne.setBloodData(bloodDataEntity);

			// radiologyData
			RadiologyData radiologyDataEntity = insertRadiologyData(dataItem);
			dataOfOne.setRadiologyData(radiologyDataEntity);

			// urineData
			UrineData UrineDataEntity = insertUrineData(dataItem);
			dataOfOne.setUrineData(UrineDataEntity);

			// commentData
			CommentData commentDataEntity = insertCommentData(dataItem);
			dataOfOne.setCommentData(commentDataEntity);

			// instrumentationData
			InstrumentationData instrumentationDataEntity = insertInstrumentationData(dataItem);
			dataOfOne.setInstrumentationData(instrumentationDataEntity);

			// surveyData
			SurveyData surveyDataEntity = insertSurveyData(dataItem);
			dataOfOne.setSurveyData(surveyDataEntity);

			// hepatitisData
			HepatitisData hepatitisDataEntity = insertHepatitisData(dataItem);
			dataOfOne.setHepatitisData(hepatitisDataEntity);

			// mentalData
			MentalData mentalDataEntity = insertMentalData(dataItem);
			dataOfOne.setMentalData(mentalDataEntity);

			// boneData
			BoneData boneDataEntity = insertBoneData(dataItem);
			dataOfOne.setBoneData(boneDataEntity);

			// elderfunctionData
			ElderfunctionData elderfunctionDataEntity = insertElderfunctionData(dataItem);
			dataOfOne.setElderfunctionData(elderfunctionDataEntity);

			String examinationYearString = (String) map.get("examinationYear");
			dataOfOne.setExaminationYear(Integer.parseInt(examinationYearString));
			dataOfOne.setUserData(userData);
			userExaminationEntireDataOfOneRepository.save(dataOfOne);
		} else {

			CancerData cancerData = dataOfOne.getCancerData();

			if (cancerData == null) {
				CancerData cancerDataEntity = insertCancerData(dataItem);
				dataOfOne.setCancerData(cancerDataEntity);
			} else {
				updateCancerData(map, cancerData);
			}

			BloodData bloodData = dataOfOne.getBloodData();

			if (bloodData == null) {
				BloodData bloodDataEntity = insertBloodData(dataItem);
				dataOfOne.setBloodData(bloodDataEntity);
			} else {
				updateBloodData(dataItem, bloodData);
			}

			RadiologyData radiologyData = dataOfOne.getRadiologyData();

			if (radiologyData == null) {
				RadiologyData radiologyDataEntity = insertRadiologyData(dataItem);
				dataOfOne.setRadiologyData(radiologyDataEntity);
			} else {
				updateRadiologyData(dataItem, radiologyData);
			}

			UrineData urineData = dataOfOne.getUrineData();

			if (urineData == null) {
				UrineData UrineDataEntity = insertUrineData(dataItem);
				dataOfOne.setUrineData(UrineDataEntity);
			} else {
				updateUrineData(dataItem, urineData);
			}

			CommentData commentData = dataOfOne.getCommentData();

			if (commentData == null) {
				CommentData commentDataEntity = insertCommentData(dataItem);
				dataOfOne.setCommentData(commentDataEntity);
			} else {
				updateCommentData(dataItem, commentData);
			}

			InstrumentationData instrumentationData = dataOfOne.getInstrumentationData();

			if (instrumentationData == null) {
				InstrumentationData instrumentationDataEntity = insertInstrumentationData(dataItem);
				dataOfOne.setInstrumentationData(instrumentationDataEntity);
			} else {
				updateInstrumentationData(dataItem, instrumentationData);
			}

			SurveyData surveyData = dataOfOne.getSurveyData();

			if (surveyData == null) {
				SurveyData surveyDataEntity = insertSurveyData(dataItem);
				dataOfOne.setSurveyData(surveyDataEntity);
			} else {
				updateSurveyData(dataItem, surveyData);
			}

			HepatitisData hepatitisData = dataOfOne.getHepatitisData();

			if (hepatitisData == null) {
				HepatitisData hepatitisDataEntity = insertHepatitisData(dataItem);
				dataOfOne.setHepatitisData(hepatitisDataEntity);
			} else {
				updateHepatitisData(dataItem, hepatitisData);
			}

			MentalData mentalData = dataOfOne.getMentalData();

			if (mentalData == null) {
				MentalData mentalDataEntity = insertMentalData(dataItem);
				dataOfOne.setMentalData(mentalDataEntity);
			} else {
				updateMentalData(dataItem, mentalData);
			}

			BoneData boneData = dataOfOne.getBoneData();

			if (boneData == null) {
				BoneData boneDataEntity = insertBoneData(dataItem);
				dataOfOne.setBoneData(boneDataEntity);
			} else {
				updateBoneData(dataItem, boneData);
			}

			ElderfunctionData elderfunctionData = dataOfOne.getElderfunctionData();

			if (elderfunctionData == null) {
				ElderfunctionData elderfunctionDataEntity = insertElderfunctionData(dataItem);
				dataOfOne.setElderfunctionData(elderfunctionDataEntity);
			} else {
				updateElderfunctionData(dataItem, elderfunctionData);
			}
		}

		System.out.println("userExaminationEntireDataOfOne: " + dataOfOne.getId());
		return dataOfOne;
	}

	private CancerData insertCancerData(Map<String, Map<String, String>> dataItem) {
		Map<String, String> cancerDataItem = dataItem.get("cancerData");

		if (cancerDataItem != null) {
			CancerData cancerData = CancerData.builder() //
					.cancerWombInspectionItems(cancerDataItem.get("cancerWombInspectionItems")) //
					.cancerWombResult(cancerDataItem.get("cancerWombResult")) //
					.cancerWombDecision(cancerDataItem.get("cancerWombDecision")) //
					.cancerWombRecommendedText(cancerDataItem.get("cancerWombRecommendedText")) //
					.cancerLiverInspectionItems(cancerDataItem.get("cancerLiverInspectionItems")) //
					.cancerLiverResult(cancerDataItem.get("cancerLiverResult")) //
					.cancerLiverDecision(cancerDataItem.get("cancerLiverDecision")) //
					.cancerLiverRecommendedText(cancerDataItem.get("cancerLiverRecommendedText")) //
					.cancerStomachInspectionItems(cancerDataItem.get("cancerStomachInspectionItems")) //
					.cancerStomachResult(cancerDataItem.get("cancerStomachResult")) //
					.cancerStomachDecision(cancerDataItem.get("cancerStomachDecision")) //
					.cancerStomachRecommendedText(cancerDataItem.get("cancerStomachRecommendedText")) //
					.cancerColonInspectionItems(cancerDataItem.get("cancerColonInspectionItems")) //
					.cancerColonResult(cancerDataItem.get("cancerColonResult")) //
					.cancerColonDecision(cancerDataItem.get("cancerColonDecision")) //
					.cancerColonRecommendedText(cancerDataItem.get("cancerColonRecommendedText")) //
					.cancerBreastInspectionItems(cancerDataItem.get("cancerBreastInspectionItems")) //
					.cancerBreastResult(cancerDataItem.get("cancerBreastResult")) //
					.cancerBreastDecision(cancerDataItem.get("cancerBreastDecision")) //
					.cancerBreastRecommendedText(cancerDataItem.get("cancerBreastRecommendedText")) //
					.cancerLiverSecondInspectionItems(cancerDataItem.get("cancerLiverSecondInspectionItems")) //
					.cancerLiverSecondResult(cancerDataItem.get("cancerLiverSecondResult")) //
					.cancerLiverSecondDecision(cancerDataItem.get("cancerLiverSecondDecision")) //
					.cancerLiverSecondRecommendedText(cancerDataItem.get("cancerLiverSecondRecommendedText")) //
					.cancerLungInspectionItems(cancerDataItem.get("cancerLungInspectionItems")) //
					.cancerLungResult(cancerDataItem.get("cancerLungResult")) //
					.cancerLungDecision(cancerDataItem.get("cancerLungDecision")) //
					.cancerLungRecommendedText(cancerDataItem.get("cancerLungRecommendedText")) //
					.cancerColonSecondInspectionItems(cancerDataItem.get("cancerColonSecondInspectionItems")) //
					.cancerColonSecondResult(cancerDataItem.get("cancerColonSecondResult")) //
					.cancerColonSecondDecision(cancerDataItem.get("cancerColonSecondDecision")) //
					.cancerColonSecondRecommendedText(cancerDataItem.get("cancerColonSecondRecommendedText")) //
					.build();

			cancerData = cancerDataRepository.save(cancerData);
			return cancerData;
		}

		return null;
	}

	private void updateCancerData(Map<String, Object> map, CancerData cancerData) {
		Map<String, Object> dataItem = (Map<String, Object>) map.get("userExaminationEntireDataOfOne");

		Map<String, String> cancerDataItem = (Map<String, String>) dataItem.get("cancerData");

		if (cancerDataItem != null) {
			String inspectionType = (String) map.get("inspectionType");
			String inspectionSubType = (String) map.get("inspectionSubType");

			if ("3".equals(inspectionType) && "4".equals(inspectionSubType)) {
				cancerData.setCancerWombDecision(cancerDataItem.get("cancerWombDecision"));
				cancerData.setCancerWombInspectionItems(cancerDataItem.get("cancerWombInspectionItems"));
				cancerData.setCancerWombRecommendedText(cancerDataItem.get("cancerWombRecommendedText"));
				cancerData.setCancerWombResult(cancerDataItem.get("cancerWombResult"));
			} else if ("3".equals(inspectionType) && "5".equals(inspectionSubType)) {
				cancerData.setCancerStomachDecision(cancerDataItem.get("cancerStomachDecision"));
				cancerData.setCancerStomachInspectionItems(cancerDataItem.get("cancerStomachInspectionItems"));
				cancerData.setCancerStomachRecommendedText(cancerDataItem.get("cancerStomachRecommendedText"));
				cancerData.setCancerStomachResult(cancerDataItem.get("cancerStomachResult"));
			} else if ("3".equals(inspectionType) && "6".equals(inspectionSubType)) {
				cancerData.setCancerColonDecision(cancerDataItem.get("cancerColonDecision"));
				cancerData.setCancerColonInspectionItems(cancerDataItem.get("cancerColonInspectionItems"));
				cancerData.setCancerColonRecommendedText(cancerDataItem.get("cancerColonRecommendedText"));
				cancerData.setCancerColonResult(cancerDataItem.get("cancerColonResult"));
			} else if ("3".equals(inspectionType) && "7".equals(inspectionSubType)) {
				cancerData.setCancerBreastDecision(cancerDataItem.get("cancerBreastDecision"));
				cancerData.setCancerBreastInspectionItems(cancerDataItem.get("cancerBreastInspectionItems"));
				cancerData.setCancerBreastRecommendedText(cancerDataItem.get("cancerBreastRecommendedText"));
				cancerData.setCancerBreastResult(cancerDataItem.get("cancerBreastResult"));
			} else if ("3".equals(inspectionType) && "8".equals(inspectionSubType)) {
				cancerData.setCancerLiverDecision(cancerDataItem.get("cancerLiverDecision"));
				cancerData.setCancerLiverInspectionItems(cancerDataItem.get("cancerLiverInspectionItems"));
				cancerData.setCancerLiverRecommendedText(cancerDataItem.get("cancerLiverRecommendedText"));
				cancerData.setCancerLiverResult(cancerDataItem.get("cancerLiverResult"));
			} else if ("3".equals(inspectionType) && "9".equals(inspectionSubType)) {
				cancerData.setCancerLiverSecondDecision(cancerDataItem.get("cancerLiverSecondDecision"));
				cancerData.setCancerLiverSecondInspectionItems(cancerDataItem.get("cancerLiverSecondInspectionItems"));
				cancerData.setCancerLiverSecondRecommendedText(cancerDataItem.get("cancerLiverSecondRecommendedText"));
				cancerData.setCancerLiverSecondResult(cancerDataItem.get("cancerLiverSecondResult"));
			} else if ("3".equals(inspectionType) && "10".equals(inspectionSubType)) {
				cancerData.setCancerLungDecision(cancerDataItem.get("cancerLungDecision"));
				cancerData.setCancerLungInspectionItems(cancerDataItem.get("cancerLungInspectionItems"));
				cancerData.setCancerLungRecommendedText(cancerDataItem.get("cancerLungRecommendedText"));
				cancerData.setCancerLungResult(cancerDataItem.get("cancerLungResult"));
			} else if ("3".equals(inspectionType) && "11".equals(inspectionSubType)) {
				cancerData.setCancerColonDecision(cancerDataItem.get("cancerColonDecision"));
				cancerData.setCancerColonSecondDecision(cancerDataItem.get("cancerColonSecondDecision"));
				cancerData.setCancerColonSecondInspectionItems(cancerDataItem.get("cancerColonSecondInspectionItems"));
				cancerData.setCancerColonSecondRecommendedText(cancerDataItem.get("cancerColonSecondRecommendedText"));
				cancerData.setCancerColonSecondResult(cancerDataItem.get("cancerColonSecondResult"));
			}
		}
	}

	private BloodData insertBloodData(Map<String, Map<String, String>> dataItem) {
		Map<String, String> bloodDataItem = dataItem.get("bloodData");

		if (bloodDataItem != null) {
			BloodData bloodData = BloodData.builder() //
					.hemoglobin(bloodDataItem.get("hemoglobin")) //
					.hemoglobinResult(bloodDataItem.get("hemoglobinResult")) //
					.fastingGlucose(bloodDataItem.get("fastingGlucose")) //
					.fastingGlucoseResult(bloodDataItem.get("fastingGlucoseResult")) //
					.lipidResult(bloodDataItem.get("lipidResult")) //
					.totalCholesterol(bloodDataItem.get("totalCholesterol")) //
					.hdlCholesterol(bloodDataItem.get("hdlCholesterol")) //
					.triglyceride(bloodDataItem.get("triglyceride")) //
					.ldlCholesterol(bloodDataItem.get("ldlCholesterol")) //
					.renalDiseaseResult(bloodDataItem.get("renalDiseaseResult")) //
					.creatinine(bloodDataItem.get("creatinine")) //
					.eGfr(bloodDataItem.get("eGfr")) //
					.liverDiseaseResult(bloodDataItem.get("liverDiseaseResult")) //
					.alt(bloodDataItem.get("alt")) //
					.ast(bloodDataItem.get("ast")) //
					.gammaGtp(bloodDataItem.get("gammaGtp")) //
					.build();
			bloodData = bloodDataRepository.save(bloodData);
			return bloodData;
		}
		return null;
	}

	private void updateBloodData(Map<String, Map<String, String>> dataItem, BloodData bloodData) {
		Map<String, String> bloodDataItem = dataItem.get("bloodData");

		if (bloodDataItem != null) {
			bloodData.setAlt(bloodDataItem.get("alt"));
			bloodData.setAst(bloodDataItem.get("ast"));
			bloodData.setCreatinine(bloodDataItem.get("creatinine"));
			bloodData.setEGfr(bloodDataItem.get("eGfr"));
			bloodData.setFastingGlucose(bloodDataItem.get("fastingGlucose"));
			bloodData.setFastingGlucoseResult(bloodDataItem.get("fastingGlucoseResult"));
			bloodData.setGammaGtp(bloodDataItem.get("gammaGtp"));
			bloodData.setHdlCholesterol(bloodDataItem.get("hdlCholesterol"));
			bloodData.setHemoglobin(bloodDataItem.get("hemoglobin"));
			bloodData.setHemoglobinResult(bloodDataItem.get("hemoglobinResult"));
			bloodData.setLdlCholesterol(bloodDataItem.get("ldlCholesterol"));
			bloodData.setLipidResult(bloodDataItem.get("lipidResult"));
			bloodData.setLiverDiseaseResult(bloodDataItem.get("liverDiseaseResult"));
			bloodData.setRenalDiseaseResult(bloodDataItem.get("renalDiseaseResult"));
			bloodData.setTotalCholesterol(bloodDataItem.get("totalCholesterol"));
			bloodData.setTriglyceride(bloodDataItem.get("triglyceride"));
		}
	}

	private RadiologyData insertRadiologyData(Map<String, Map<String, String>> dataItem) {
		Map<String, String> radiologyDataItem = dataItem.get("radiologyData");

		if (radiologyDataItem != null) {
			RadiologyData radiologyData = RadiologyData.builder() //
					.chestXRay(radiologyDataItem.get("chestXRay")) //
					.chestXRayResult(radiologyDataItem.get("chestXRayResult")) //
					.build();

			radiologyData = radiologyDataRepository.save(radiologyData);
			return radiologyData;
		}
		return null;
	}

	private void updateRadiologyData(Map<String, Map<String, String>> dataItem, RadiologyData radiologyData) {
		Map<String, String> radiologyDataItem = dataItem.get("radiologyData");

		if (radiologyDataItem != null) {
			radiologyData.setChestXRay(radiologyDataItem.get("chestXRay"));
			radiologyData.setChestXRayResult(radiologyDataItem.get("chestXRayResult"));
		}
	}

	private UrineData insertUrineData(Map<String, Map<String, String>> dataItem) {
		Map<String, String> urineDataItem = dataItem.get("urineData");

		if (urineDataItem != null) {
			UrineData urineData = UrineData.builder() //
					.urineProtein(urineDataItem.get("urineProtein")) //
					.build();
			urineData = urineDataRepository.save(urineData);
			return urineData;
		}
		return null;
	}

	private void updateUrineData(Map<String, Map<String, String>> dataItem, UrineData urineData) {
		Map<String, String> urineDataItem = dataItem.get("urineData");

		if (urineDataItem != null) {
			urineData.setUrineProtein(urineDataItem.get("urineProtein"));
		}
	}

	private CommentData insertCommentData(Map<String, Map<String, String>> dataItem) {
		Map<String, String> commentDataItem = dataItem.get("commentData");

		if (commentDataItem != null) {
			CommentData commentData = CommentData.builder() //
					.finalInterpretation(commentDataItem.get("finalInterpretation")) //
					.suspiciousDisease(commentDataItem.get("suspiciousDisease")) //
					.existingDisease(commentDataItem.get("existingDisease")) //
					.howLifeHabitManagement(commentDataItem.get("howLifeHabitManagement")) //
					.otherComments(commentDataItem.get("otherComments")) //
					.build();
			commentData = commentDataRepository.save(commentData);

			return commentData;
		}

		return null;
	}

	private void updateCommentData(Map<String, Map<String, String>> dataItem, CommentData commentData) {
		Map<String, String> commentDataItem = dataItem.get("commentData");

		if (commentDataItem != null) {
			commentData.setExistingDisease(commentDataItem.get("existingDisease"));
			commentData.setFinalInterpretation(commentDataItem.get("finalInterpretation"));
			commentData.setHowLifeHabitManagement(commentDataItem.get("howLifeHabitManagement"));
			commentData.setOtherComments(commentDataItem.get("otherComments"));
		}
	}

	private InstrumentationData insertInstrumentationData(Map<String, Map<String, String>> dataItem) {
		Map<String, String> instrumentationDataItem = dataItem.get("instrumentationData");

		if (instrumentationDataItem != null) {
			InstrumentationData instrumentationData = InstrumentationData.builder() //
					.height(instrumentationDataItem.get("height")) //
					.weight(instrumentationDataItem.get("weight")) //
					.bmi(instrumentationDataItem.get("bmi")) //
					.bmiResult(instrumentationDataItem.get("bmiResult")) //
					.waistCircumference(instrumentationDataItem.get("waistCircumference")) //
					.waistCircumferenceResult(instrumentationDataItem.get("waistCircumferenceResult")) //
					.visionLeft(instrumentationDataItem.get("visionLeft")) //
					.visionRight(instrumentationDataItem.get("visionRight")) //
					.visionCorrection(instrumentationDataItem.get("visionCorrection")) //
					.hearingLeft(instrumentationDataItem.get("hearingLeft")) //
					.hearingRight(instrumentationDataItem.get("hearingRight")) //
					.hearingResult(instrumentationDataItem.get("hearingResult")) //
					.bloodPressureResult(instrumentationDataItem.get("bloodPressureResult")) //
					.systolicBloodPressure(instrumentationDataItem.get("systolicBloodPressure")) //
					.diastolicBloodPressure(instrumentationDataItem.get("diastolicBloodPressure")) //
					.build();
			instrumentationData = instrumentationDataRepository.save(instrumentationData);
			return instrumentationData;
		}

		return null;
	}

	private void updateInstrumentationData(Map<String, Map<String, String>> dataItem,
			InstrumentationData instrumentationData) {
		Map<String, String> instrumentationDataItem = dataItem.get("instrumentationData");

		if (instrumentationDataItem != null) {
			instrumentationData.setHeight(instrumentationDataItem.get("height"));
			instrumentationData.setWeight(instrumentationDataItem.get("weight"));
			instrumentationData.setBmi(instrumentationDataItem.get("bmi"));
			instrumentationData.setBmiResult(instrumentationDataItem.get("bmiResult"));
			instrumentationData.setWaistCircumference(instrumentationDataItem.get("waistCircumference"));
			instrumentationData.setWaistCircumferenceResult(instrumentationDataItem.get("waistCircumferenceResult"));
			instrumentationData.setVisionLeft(instrumentationDataItem.get("visionLeft"));
			instrumentationData.setVisionRight(instrumentationDataItem.get("visionRight"));
			instrumentationData.setVisionCorrection(instrumentationDataItem.get("visionCorrection"));
			instrumentationData.setHearingLeft(instrumentationDataItem.get("hearingLeft"));
			instrumentationData.setHearingRight(instrumentationDataItem.get("hearingRight"));
			instrumentationData.setHearingResult(instrumentationDataItem.get("hearingResult"));
			instrumentationData.setBloodPressureResult(instrumentationDataItem.get("bloodPressureResult"));
			instrumentationData.setSystolicBloodPressure(instrumentationDataItem.get("systolicBloodPressure"));
			instrumentationData.setDiastolicBloodPressure(instrumentationDataItem.get("diastolicBloodPressure"));
		}
	}

	private SurveyData insertSurveyData(Map<String, Map<String, String>> dataItem) {
		Map<String, String> surveyDataItem = dataItem.get("surveyData");

		if (surveyDataItem != null) {
			SurveyData surveyData = SurveyData.builder() //
					.pastDiseaseHistory(surveyDataItem.get("pastDiseaseHistory")) //
					.nowDrugTreatment(surveyDataItem.get("nowDrugTreatment")) //
					.recommendationsForLifeStyle(surveyDataItem.get("recommendationsForLifeStyle")) //
					.smokerCategory(surveyDataItem.get("smokerCategory")) //
					.levelOfNicotineDependence(surveyDataItem.get("levelOfNicotineDependence")) //
					.drinkingCategory(surveyDataItem.get("drinkingCategory")) //
					.exerciseStatus(surveyDataItem.get("exerciseStatus")) //
					.nutritionStatus(surveyDataItem.get("nutritionStatus")) //
					.obesityStatus(surveyDataItem.get("obesityStatus")) //
					.drugTreatment(surveyDataItem.get("drugTreatment")) //
					.build();

			surveyData = surveyDataRepository.save(surveyData);
			return surveyData;
		}

		return null;
	}

	private void updateSurveyData(Map<String, Map<String, String>> dataItem, SurveyData surveyData) {
		Map<String, String> surveyDataItem = dataItem.get("surveyData");

		if (surveyDataItem != null) {
			String pastDiseaseHistory = surveyDataItem.get("pastDiseaseHistory");
			if (pastDiseaseHistory != null) {
				surveyData.setPastDiseaseHistory(pastDiseaseHistory);
			}
			
			String nowDrugTreatment = surveyDataItem.get("nowDrugTreatment");
			if (nowDrugTreatment != null) {
				surveyData.setNowDrugTreatment(nowDrugTreatment);
			}
			
			String recommendationsForLifeStyle = surveyDataItem.get("recommendationsForLifeStyle");
			if (recommendationsForLifeStyle != null) {
				surveyData.setRecommendationsForLifeStyle(recommendationsForLifeStyle);
			}
			
			String smokerCategory = surveyDataItem.get("smokerCategory");
			if (smokerCategory != null) {
				surveyData.setSmokerCategory(smokerCategory);
			}
			
			String levelOfNicotineDependence = surveyDataItem.get("levelOfNicotineDependence");
			if (levelOfNicotineDependence != null) {
				surveyData.setLevelOfNicotineDependence(levelOfNicotineDependence);
			}
			
			String drinkingCategory = surveyDataItem.get("drinkingCategory");
			if (drinkingCategory != null) {
				surveyData.setDrinkingCategory(drinkingCategory);
			}
			
			String exerciseStatus = surveyDataItem.get("exerciseStatus");
			if (exerciseStatus != null) {
				surveyData.setExerciseStatus(exerciseStatus);
			}
			
			String nutritionStatus = surveyDataItem.get("nutritionStatus");
			if (nutritionStatus != null) {
				surveyData.setNutritionStatus(nutritionStatus);
			}
			
			String obesityStatus = surveyDataItem.get("obesityStatus");
			if (obesityStatus != null) {
				surveyData.setObesityStatus(obesityStatus);
			}
			
			String drugTreatment = surveyDataItem.get("drugTreatment");
			if (drugTreatment != null) {
				surveyData.setDrugTreatment(drugTreatment);
			}
		}
	}
	
	private HepatitisData insertHepatitisData(Map<String, Map<String, String>> dataItem) {
		Map<String, String> hepatitisDataItem = dataItem.get("hepatitisData");

		if (hepatitisDataItem != null) {
			HepatitisData hepatitisData = HepatitisData.builder() //
					.hepatitisbInspection(hepatitisDataItem.get("hepatitisbInspection")) //
					.hepatitisbSurfaceAntibody(hepatitisDataItem.get("hepatitisbSurfaceAntibody")) //
					.hepatitisbSurfaceAntigen(hepatitisDataItem.get("hepatitisbSurfaceAntigen")) //
					.hepatitisbResult(hepatitisDataItem.get("hepatitisbResult")) //
					.build();
			hepatitisData = hepatitisDataRepository.save(hepatitisData);

			return hepatitisData;
		}
		return null;
	}

	private void updateHepatitisData(Map<String, Map<String, String>> dataItem, HepatitisData hepatitisData) {
		Map<String, String> hepatitisDataItem = dataItem.get("hepatitisData");

		if (hepatitisDataItem != null) {
			hepatitisData.setHepatitisbInspection(hepatitisDataItem.get("hepatitisbInspection"));
			hepatitisData.setHepatitisbResult(hepatitisDataItem.get("hepatitisbResult"));
			hepatitisData.setHepatitisbSurfaceAntibody(hepatitisDataItem.get("hepatitisbSurfaceAntibody"));
			hepatitisData.setHepatitisbSurfaceAntigen(hepatitisDataItem.get("hepatitisbSurfaceAntigen"));
		}
	}

	private MentalData insertMentalData(Map<String, Map<String, String>> dataItem) {

		Map<String, String> mentalDataItem = dataItem.get("mentalData");

		if (mentalDataItem != null) {
			MentalData mentalData = MentalData.builder() //
					.depressiveDisorderInspection(mentalDataItem.get("depressiveDisorderInspection")) //
					.depressiveDisorderResult(mentalDataItem.get("depressiveDisorderResult")) //
					.cognitiveImpairmentInspection(mentalDataItem.get("cognitiveImpairmentInspection")) //
					.cognitiveImpairmentResult(mentalDataItem.get("cognitiveImpairmentResult")) //
					.build();
			mentalData = mentalDataRepository.save(mentalData);

			return mentalData;
		}

		return null;
	}

	private void updateMentalData(Map<String, Map<String, String>> dataItem, MentalData mentalData) {
		Map<String, String> mentalDataItem = dataItem.get("mentalData");

		if (mentalDataItem != null) {
			mentalData.setDepressiveDisorderInspection(mentalDataItem.get("depressiveDisorderInspection"));
			mentalData.setDepressiveDisorderResult(mentalDataItem.get("depressiveDisorderResult"));
			mentalData.setCognitiveImpairmentInspection(mentalDataItem.get("cognitiveImpairmentInspection"));
			mentalData.setCognitiveImpairmentResult(mentalDataItem.get("cognitiveImpairmentResult"));
		}
	}

	private BoneData insertBoneData(Map<String, Map<String, String>> dataItem) {
		Map<String, String> boneDataItem = dataItem.get("boneData");

		if (boneDataItem != null) {
			BoneData boneData = BoneData.builder() //
					.boneDensityInspection(boneDataItem.get("boneDensityInspection")) //
					.boneDensityValue(boneDataItem.get("boneDensityValue")) //
					.boneDensityResult(boneDataItem.get("boneDensityResult")) //
					.build();
			boneData = boneDataRepository.save(boneData);

			return boneData;
		}
		return null;
	}

	private void updateBoneData(Map<String, Map<String, String>> dataItem, BoneData boneData) {
		Map<String, String> boneDataItem = dataItem.get("boneData");

		if (boneDataItem != null) {
			boneData.setBoneDensityInspection(boneDataItem.get("boneDensityInspection"));
			boneData.setBoneDensityValue(boneDataItem.get("boneDensityValue"));
			boneData.setBoneDensityResult(boneDataItem.get("boneDensityResult"));
		}
	}

	private ElderfunctionData insertElderfunctionData(Map<String, Map<String, String>> dataItem) {
		Map<String, String> elderfunctionDataItem = dataItem.get("elderfunctionData");

		if (elderfunctionDataItem != null) {
			ElderfunctionData elderfunctionData = ElderfunctionData.builder() //
					.physicalFunctionTestForElderInspection(
							elderfunctionDataItem.get("physicalFunctionTestForElderInspection")) //
					.physicalFunctionTestForElderResult(elderfunctionDataItem.get("physicalFunctionTestForElderResult")) //
					.assessmentOfFunctionalStatusOfElderInspection(
							elderfunctionDataItem.get("assessmentOfFunctionalStatusOfElderInspection")) //
					.assessmentOfFallingOfElderResult(elderfunctionDataItem.get("assessmentOfFallingOfElderResult")) //
					.assessmentOfActivitiesForDailyLivingOfElderResult(
							elderfunctionDataItem.get("assessmentOfActivitiesForDailyLivingOfElderResult")) //
					.assessmentOfVaccinationOfElderResult(
							elderfunctionDataItem.get("assessmentOfVaccinationOfElderResult")) //
					.assessmentOfDysuresiaOfElderResult(elderfunctionDataItem.get("assessmentOfDysuresiaOfElderResult")) //
					.build();
			elderfunctionData = elderfunctionDataRepository.save(elderfunctionData);

			return elderfunctionData;
		}

		return null;
	}

	private void updateElderfunctionData(Map<String, Map<String, String>> dataItem,
			ElderfunctionData elderfunctionData) {
		Map<String, String> elderfunctionDataItem = dataItem.get("elderfunctionData");

		if (elderfunctionDataItem != null) {
			elderfunctionData.setPhysicalFunctionTestForElderInspection(
					elderfunctionDataItem.get("physicalFunctionTestForElderInspection"));
			elderfunctionData.setPhysicalFunctionTestForElderResult(
					elderfunctionDataItem.get("physicalFunctionTestForElderResult"));
			elderfunctionData.setAssessmentOfFunctionalStatusOfElderInspection(
					elderfunctionDataItem.get("assessmentOfFunctionalStatusOfElderInspection"));
			elderfunctionData
					.setAssessmentOfFallingOfElderResult(elderfunctionDataItem.get("assessmentOfFallingOfElderResult"));
			elderfunctionData.setAssessmentOfActivitiesForDailyLivingOfElderResult(
					elderfunctionDataItem.get("assessmentOfActivitiesForDailyLivingOfElderResult"));
			elderfunctionData.setAssessmentOfVaccinationOfElderResult(
					elderfunctionDataItem.get("assessmentOfVaccinationOfElderResult"));
			elderfunctionData.setAssessmentOfDysuresiaOfElderResult(
					elderfunctionDataItem.get("assessmentOfDysuresiaOfElderResult"));
		}
	}

	public List<Map<String, Object>> registerPdfFiles(List<Map<String, String>> dataList, MultipartFile[] files) {
		List<Map<String, Object>> result = new ArrayList<>();
		for (int i = 0; i < dataList.size(); i++) {

			Map<String, String> data = dataList.get(i);

			Integer inspectionTypeIndex = Integer.parseInt(data.get("inspectionType"));
			InspectionType inspectionType = InspectionType.values()[inspectionTypeIndex];

			Integer inspectionSubTypeIndex = Integer.parseInt(data.get("inspectionSubType"));
			InspectionSubType inspectionSubType = InspectionSubType.values()[inspectionSubTypeIndex];

			Long pdfWebId = Long.parseLong(data.get("pdfWebId"));
			PdfWeb pdfWeb = null;
			Optional<PdfWeb> pdfWebOptional = pdfWebRepository.findById(pdfWebId);

			if (pdfWebOptional.isPresent()) {
				pdfWeb = pdfWebOptional.get();
			} else {
				Map<String, Object> nonexistPdf = new HashMap<>();
				
				nonexistPdf.put("pdfWebId", pdfWebId);
				nonexistPdf.put("errorType", "Pdf web not exist");
				result.add(nonexistPdf);
				continue;
			}

			String phone = data.get("phone");
			String ssnPartial = data.get("ssnPartial");

			UserData userData = null;
			List<UserData> userDataList = userDataRepository.findByPhoneAndSsnPartial(phone, ssnPartial);

			if (userDataList.size() == 1) {
				userData = userDataList.get(0);
			} else if(userDataList.size() == 0) {
				Map<String, Object> nonexistUser = new HashMap<>();
				nonexistUser.put("phone", phone);
				nonexistUser.put("ssnPartial", ssnPartial);
				nonexistUser.put("errorType", "No such user");
				result.add(nonexistUser);
				continue;
			} else {
				Map<String, Object> existedMultipleUsers = new HashMap<>();
				existedMultipleUsers.put("phone", phone);
				existedMultipleUsers.put("ssnPartial", ssnPartial);
				existedMultipleUsers.put("errorType", "Multiple user exist");
				result.add(existedMultipleUsers);
				continue;
			}
			
			List<PdfIndividualWeb> pdfList = pdfIndividualWebRepository.findByUserDataAndPdfWebAndInspectionTypeAndInspectionSubType(userData, pdfWeb, inspectionType, inspectionSubType);
			
			if (pdfList.size() == 0) {
				
				FileInfo fileInfo = fileUploadUtils.uploadIndividualFile(files[i]);
				
				PdfIndividualWeb pdfIndividualWeb = PdfIndividualWeb.builder() //
						.inspectionType(inspectionType) //
						.inspectionSubType(inspectionSubType) //
						.pdfWeb(pdfWeb) //
						.userData(userData) //
						.fileInfo(fileInfo) //
						.build();
				
				PdfIndividualWeb pdfEntity = pdfIndividualWebRepository.save(pdfIndividualWeb);
				
				Integer examinationYear = Integer.parseInt(data.get("dateExaminedYear"));
				List<UserExaminationMetadata> metadataList = userExaminationMetadataRepository
						.findByUserAndExaminationYear(userData, examinationYear);
				
				if (metadataList.size() == 1) {
					List<UserExaminationMetadataDetail> metadataDetailList = metadataList.get(0)
							.getUserExaminationMetadataDetailList();
					for (UserExaminationMetadataDetail detail : metadataDetailList) {
						if (inspectionType.equals(detail.getInspectionType())
								&& inspectionSubType.equals(detail.getInspectionSubType())) {
							detail.setPdfIndividualWeb(pdfEntity);
							break;
						}
					}
				} else if (metadataList.size() == 0) {
					Map<String, Object> nonexistMetadata = new HashMap<>();
					nonexistMetadata.put("phone", phone);
					nonexistMetadata.put("ssnPartial", ssnPartial);
					nonexistMetadata.put("errorType", "No such metadata");
					result.add(nonexistMetadata);
					continue;
				} else {
					Map<String, Object> existedMultipleMetadatas = new HashMap<>();
					existedMultipleMetadatas.put("phone", phone);
					existedMultipleMetadatas.put("ssnPartial", ssnPartial);
					existedMultipleMetadatas.put("errorType", "Multiple metadatas exist");
					result.add(existedMultipleMetadatas);
					continue;
				}
			} else {
				Map<String, Object> existedPdfFile = new HashMap<>();
				existedPdfFile.put("phone", phone);
				existedPdfFile.put("ssnPartial", ssnPartial);
				existedPdfFile.put("inspectionType", inspectionType);
				existedPdfFile.put("inspectionSubType", inspectionSubType);
				existedPdfFile.put("errorType", "Multiple pdf individual exist");
				result.add(existedPdfFile);
			}
		}
		
		return result;
	}

	public List<Map<String, Object>> registerJsonFiles(List<Map<String, String>> dataList, MultipartFile[] files) {
		List<Map<String, Object>> result = new ArrayList<>();
		for (int i = 0; i < dataList.size(); i++) {

			Map<String, String> data = dataList.get(i);

			Integer inspectionTypeIndex = Integer.parseInt(data.get("inspectionType"));
			InspectionType inspectionType = InspectionType.values()[inspectionTypeIndex];

			Integer inspectionSubTypeIndex = Integer.parseInt(data.get("inspectionSubType"));
			InspectionSubType inspectionSubType = InspectionSubType.values()[inspectionSubTypeIndex];

			String phone = data.get("phone");
			String ssnPartial = data.get("ssnPartial");

			UserData userData = null;
			List<UserData> userDataList = userDataRepository.findByPhoneAndSsnPartial(phone, ssnPartial);

			if (userDataList.size() == 1) {
				userData = userDataList.get(0);
			} else if(userDataList.size() == 0) {
				Map<String, Object> nonexistUser = new HashMap<>();
				nonexistUser.put("phone", phone);
				nonexistUser.put("ssnPartial", ssnPartial);
				nonexistUser.put("errorType", "No such user");
				result.add(nonexistUser);
				continue;
			} else {
				Map<String, Object> existedMultipleUsers = new HashMap<>();
				existedMultipleUsers.put("phone", phone);
				existedMultipleUsers.put("ssnPartial", ssnPartial);
				existedMultipleUsers.put("errorType", "Multiple user exist");
				result.add(existedMultipleUsers);
				continue;
			}

			Integer examinationYear = Integer.parseInt(data.get("dateExaminedYear"));
			
			UserExaminationMetadata metadata = null;
			List<UserExaminationMetadata> metadataList = userExaminationMetadataRepository.findByUserDataAndExaminationYear(userData, examinationYear);
			
			if (metadataList.size() == 1) {
				metadata = metadataList.get(0);
			} else if (metadataList.size() == 0) {
				Map<String, Object> nonexistMetadata = new HashMap<>();
				nonexistMetadata.put("phone", phone);
				nonexistMetadata.put("ssnPartial", ssnPartial);
				nonexistMetadata.put("errorType", "No such metadata");
				result.add(nonexistMetadata);
				continue;
			} else {
				Map<String, Object> existedMultipleMetadatas = new HashMap<>();
				existedMultipleMetadatas.put("phone", phone);
				existedMultipleMetadatas.put("ssnPartial", ssnPartial);
				existedMultipleMetadatas.put("errorType", "Multiple metadatas exist");
				result.add(existedMultipleMetadatas);
				continue;
			}
			
			List<JsonIndividualApi> jsonList = jsonIndividualApiRepository.findByUserDataAndInspectionTypeAndInspectionSubType(userData, inspectionType, inspectionSubType);
			
			if (jsonList .size() == 0) {
				
				FileInfo fileInfo = fileUploadUtils.uploadIndividualFile(files[i]);
				
				JsonIndividualApi jsonIndividualApi = JsonIndividualApi.builder() //
						.inspectionType(inspectionType) //
						.inspectionSubType(inspectionSubType) //
						.userData(userData) //
						.userExaminationMetadata(metadata) //
						.fileInfo(fileInfo) //
						.build();
				
				jsonIndividualApiRepository.save(jsonIndividualApi);
			} else {
				Map<String, Object> existedJsonFile = new HashMap<>();
				existedJsonFile.put("phone", phone);
				existedJsonFile.put("ssnPartial", ssnPartial);
				existedJsonFile.put("inspectionType", inspectionType);
				existedJsonFile.put("inspectionSubType", inspectionSubType);
				existedJsonFile.put("errorType", "Multiple json individual exist");
				result.add(existedJsonFile);
			}
		}
		
		return result;
	}

	public void deleteUserData(Long userDataId) {
		
		userDataRepository.deleteById(userDataId);
	}

	public void updateUserMetadata(Long userExaminationId) {
		UserExaminationMetadata metadata = userExaminationMetadataRepository.findById(userExaminationId).orElseThrow(() -> new IllegalArgumentException("No such metadata"));
		
		Integer agreeYn = metadata.getAgreeYn();
		
		if (agreeYn.equals(1)) {
			metadata.setUserExaminationEntireDataOfOne(null);
			metadata.setAddress("");
		}
	}

}
