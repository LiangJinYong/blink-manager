package com.blink.jobs;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.blink.domain.consentForm.WebConsentFormRepository;
import com.blink.domain.hospital.Hospital;
import com.blink.domain.hospital.HospitalRepository;
import com.blink.domain.sendMailResultWeb.SendMailResultWebRepository;
import com.blink.domain.statistics.ageGender.StatisticsDailyAgeGender;
import com.blink.domain.statistics.ageGender.StatisticsDailyAgeGenderRepository;
import com.blink.domain.statistics.examination.StatisticsDailyExamination;
import com.blink.domain.statistics.examination.StatisticsDailyExaminationRepository;
import com.blink.domain.statistics.hospital.StatisticsDailyHospital;
import com.blink.domain.statistics.hospital.StatisticsDailyHospitalReporitory;
import com.blink.domain.user.UserDataRepository;
import com.blink.domain.user.UserExaminationMetadataDetailRepository;
import com.blink.domain.user.UserExaminationMetadataRepository;
import com.blink.enumeration.Gender;
import com.blink.enumeration.InspectionSubType;
import com.blink.enumeration.InspectionType;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
@Transactional
public class StatisticsScheduler {

	private final HospitalRepository hospitalRepository;
	private final UserExaminationMetadataRepository metadataRepository;
	private final UserExaminationMetadataDetailRepository metadataDetailRepository;
	private final UserDataRepository userDataRepository;
	private final StatisticsDailyAgeGenderRepository ageGenderRepository;
	private final StatisticsDailyHospitalReporitory hospitalStatisticsRepository;
	private final StatisticsDailyExaminationRepository examinationRepository;
	
	private final WebConsentFormRepository consentFormRepository;
	private final SendMailResultWebRepository mailResultWebRepository;

	@Scheduled(cron = "0 0 1 * * *")
	public void dailyStatistics() {
		LocalDate yesterday = LocalDate.now().minusDays(1L);
		List<Long> hospitalIds = hospitalRepository.findAllValidHospitalIds();

		for (Long hospitalId : hospitalIds) {

			Integer agreeNCount = metadataRepository.findAgreeYnCount(yesterday, hospitalId, 1);
			Integer agreeYCount = metadataRepository.findAgreeYnCount(yesterday, hospitalId, 0);
			Integer examinationCount = metadataDetailRepository.findExaminationCount(yesterday, hospitalId);
			Integer examineeCount = metadataRepository.findExamineeCount(yesterday, hospitalId);

			Integer consentCount = consentFormRepository.findConsentFormCountSum(yesterday, hospitalId);
			Integer sentCount = mailResultWebRepository.findSentCountSum(yesterday, hospitalId);
			Integer sentCost = agreeNCount * 550;
			Integer omissionCost = (agreeYCount - consentCount) * 550;
			Integer agreeSendYn = hospitalRepository.findById(hospitalId).get().getAgreeSendYn();

			Integer paymentAmount;

			if (agreeSendYn == 0) {
				paymentAmount = omissionCost;
			} else {
				paymentAmount = omissionCost + sentCost;
			}

			Hospital hospital = hospitalRepository.findById(hospitalId)
					.orElseThrow(() -> new IllegalArgumentException("No such hospital"));

			StatisticsDailyHospital hospitalStatisticsEntity = StatisticsDailyHospital.builder() //
					.date(yesterday) //
					.hospital(hospital) //
					.agreeNCount(agreeNCount) //
					.agreeYCount(agreeYCount) //
					.examinationCount(examinationCount) //
					.examineeCount(examineeCount) //
					.consentCount(consentCount) //
					.sentCount(sentCount) //
					.sentCost(sentCost) //
					.omissionCost(omissionCost) //
					.agreeSendYn(agreeSendYn) //
					.paymentAmount(paymentAmount) //
					.build();
			
			hospitalStatisticsRepository.save(hospitalStatisticsEntity);
		}
	}

	@Scheduled(cron = "0 10 1 * * *")
	public void dailyStatisticsAgeGender() {
		LocalDate yesterday = LocalDate.now().minusDays(1L);
		List<Long> hospitalIds = hospitalRepository.findAllValidHospitalIds();

		for (Long hospitalId : hospitalIds) {
			List<Long> userIdList = metadataRepository.findUserDataIdListByHospitalDataIdAndCreatedAt(hospitalId,
					yesterday);

			Integer female20Count = userDataRepository.countByGenderAndAgeBetween(userIdList, Gender.FEMALE, 20, 29);
			Integer female30Count = userDataRepository.countByGenderAndAgeBetween(userIdList, Gender.FEMALE, 30, 39);
			Integer female40Count = userDataRepository.countByGenderAndAgeBetween(userIdList, Gender.FEMALE, 40, 49);
			Integer female50Count = userDataRepository.countByGenderAndAgeBetween(userIdList, Gender.FEMALE, 50, 59);
			Integer female60Count = userDataRepository.countByGenderAndAgeBetween(userIdList, Gender.FEMALE, 60, 69);
			Integer female70Count = userDataRepository.countByGenderAndAgeBetween(userIdList, Gender.FEMALE, 70, 79);
			Integer female80Count = userDataRepository.countByGenderAndAgeBetween(userIdList, Gender.FEMALE, 80, 1000);

			Integer male20Count = userDataRepository.countByGenderAndAgeBetween(userIdList, Gender.MALE, 20, 29);
			Integer male30Count = userDataRepository.countByGenderAndAgeBetween(userIdList, Gender.MALE, 30, 39);
			Integer male40Count = userDataRepository.countByGenderAndAgeBetween(userIdList, Gender.MALE, 40, 49);
			Integer male50Count = userDataRepository.countByGenderAndAgeBetween(userIdList, Gender.MALE, 50, 59);
			Integer male60Count = userDataRepository.countByGenderAndAgeBetween(userIdList, Gender.MALE, 60, 69);
			Integer male70Count = userDataRepository.countByGenderAndAgeBetween(userIdList, Gender.MALE, 70, 79);
			Integer male80Count = userDataRepository.countByGenderAndAgeBetween(userIdList, Gender.MALE, 80, 1000);

			Hospital hospital = hospitalRepository.findById(hospitalId)
					.orElseThrow(() -> new IllegalArgumentException("No such hospital"));
			StatisticsDailyAgeGender ageGenderEntity = StatisticsDailyAgeGender.builder() //
					.date(yesterday) //
					.hospital(hospital) //
					.male20Count(male20Count) //
					.male30Count(male30Count) //
					.male40Count(male40Count) //
					.male50Count(male50Count) //
					.male60Count(male60Count) //
					.male70Count(male70Count) //
					.male80Count(male80Count) //
					.female20Count(female20Count) //
					.female30Count(female30Count) //
					.female40Count(female40Count) //
					.female50Count(female50Count) //
					.female60Count(female60Count) //
					.female70Count(female70Count) //
					.female80Count(female80Count) //
					.build();

			ageGenderRepository.save(ageGenderEntity);
		}
	}

	@Scheduled(cron = "0 20 1 * * *")
	public void dailyStatisticsExamination() {
		LocalDate yesterday = LocalDate.now().minusDays(1L);
		List<Long> hospitalIds = hospitalRepository.findAllValidHospitalIds();
		
		for (Long hospitalId : hospitalIds) {
			Integer firstGeneral=metadataDetailRepository.findExaminationCount(yesterday, hospitalId, InspectionType.first, InspectionSubType.reportFirstGeneral);
			Integer firstLifeChange=metadataDetailRepository.findExaminationCount(yesterday, hospitalId, InspectionType.first, InspectionSubType.reportFirstLifeChange);
			Integer secondLifeHabit=metadataDetailRepository.findExaminationCount(yesterday, hospitalId, InspectionType.second, InspectionSubType.reportSecondLifeHabit);
			Integer cancerWomb=metadataDetailRepository.findExaminationCount(yesterday, hospitalId, InspectionType.cancer, InspectionSubType.reportCancerWomb);
			Integer cancerStomach=metadataDetailRepository.findExaminationCount(yesterday, hospitalId, InspectionType.cancer, InspectionSubType.reportCancerStomach);
			Integer cancerColon=metadataDetailRepository.findExaminationCount(yesterday, hospitalId, InspectionType.cancer, InspectionSubType.reportCancerColon);
			Integer cancerBreast = metadataDetailRepository.findExaminationCount(yesterday, hospitalId, InspectionType.cancer, InspectionSubType.reportCancerBreast);
			Integer cancerLiver=metadataDetailRepository.findExaminationCount(yesterday, hospitalId, InspectionType.cancer, InspectionSubType.reportCancerLiver);
			Integer cancerLiverSecond=metadataDetailRepository.findExaminationCount(yesterday, hospitalId, InspectionType.cancer, InspectionSubType.reportCancerLiverSecond);
			Integer cancerLug=metadataDetailRepository.findExaminationCount(yesterday, hospitalId, InspectionType.cancer, InspectionSubType.reportCancerLung);
			Integer cancerColonSecond=metadataDetailRepository.findExaminationCount(yesterday, hospitalId, InspectionType.cancer, InspectionSubType.reportCancerColonSecond);
			Integer outpatientFirst=metadataDetailRepository.findExaminationCount(yesterday, hospitalId, InspectionType.outpatient, InspectionSubType.reportFirstOutpatient);
			Integer outpatientCancer=metadataDetailRepository.findExaminationCount(yesterday, hospitalId, InspectionType.outpatient, InspectionSubType.reportCancerOutpatient);
			
			Hospital hospital = hospitalRepository.findById(hospitalId)
					.orElseThrow(() -> new IllegalArgumentException("No such hospital"));
			
			StatisticsDailyExamination examinationEntity = StatisticsDailyExamination.builder() //
			.date(yesterday) //
			.hospital(hospital) //
			.cancerBreast(cancerBreast) //
			.cancerColon(cancerColon) //
			.cancerColonSecond(cancerColonSecond) //
			.cancerLiver(cancerLiver) //
			.cancerLiverSecond(cancerLiverSecond) //
			.cancerLug(cancerLug) //
			.cancerStomach(cancerStomach) //
			.cancerWomb(cancerWomb) //
			.firstGeneral(firstGeneral) //
			.firstLifeChange(firstLifeChange) //
			.secondLifeHabit(secondLifeHabit) //
			.outpatientFirst(outpatientFirst) //
			.outpatientCancer(outpatientCancer) //
			.build();
			
			examinationRepository.save(examinationEntity);
		}
	}
}