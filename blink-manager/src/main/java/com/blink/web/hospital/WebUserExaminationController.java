package com.blink.web.hospital;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blink.common.CommonResponse;
import com.blink.common.CommonResultCode;
import com.blink.enumeration.Gender;
import com.blink.enumeration.SearchPeriod;
import com.blink.service.WebUserExaminationService;
import com.blink.web.hospital.dto.userExamination.UserExaminationResponseDto;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController("hospitalWebUserExaminationController")
@RequestMapping("/hospital/web/userExamination")
public class WebUserExaminationController {

	private final WebUserExaminationService webUserExaminationService;

	@ApiOperation(value = "해당병원의 수검자 데이터")
	@GetMapping("/{hospitalId}")
	public ResponseEntity<CommonResponse> getUserExamination(@PathVariable("hospitalId") Long hospitalId,
			@RequestParam("searchText") Optional<String> searchText,
			@RequestParam(name = "period", defaultValue = "ONEYEAR") Optional<SearchPeriod> period,
			Pageable pageable) {

		UserExaminationResponseDto result = webUserExaminationService.getUserExamination(hospitalId,
				searchText.orElse("_"), period.orElse(SearchPeriod.ONEYEAR), pageable);
		return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS, result));
	}

	@ApiOperation(value = "해당병원에서 수검자 데이터 등록")
	@PostMapping("/{hospitalId}")
	public ResponseEntity<CommonResponse> registerUserExamination(@PathVariable("hospitalId") Long hospitalId,
			@RequestParam("name") String name,
			@DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam("birthday") LocalDate birthday,
			@RequestParam("gender") Gender gender, @RequestParam("ssnPartial") String ssnPartial,
			@RequestParam("phone") String phone,
			@DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam("dateExamined") LocalDate dateExamined,
			@RequestParam("agreeYn") Integer agreeYn, @RequestParam("specialCase") String specialCase,
			@RequestParam("agreeMail") Integer agreeMail, @RequestParam("agreeSms") Integer agreeSms,
			@RequestParam("agreeVisit") Integer agreeVisit) {

		if (webUserExaminationService.checkUserExists(phone, ssnPartial)) {
			return ResponseEntity.ok(new CommonResponse(CommonResultCode.USER_DATA_ALREADY_EXISTS));
		}
		webUserExaminationService.registerUserExamination(hospitalId, name, birthday, gender, ssnPartial, phone,
				dateExamined, agreeYn, specialCase, agreeMail, agreeSms, agreeVisit);
		return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS));
	}

	@ApiOperation(value = "해당병원에서 수검자 데이터 수정")
	@PutMapping("/{hospitalId}")
	public ResponseEntity<CommonResponse> updateUserExamination(@PathVariable("hospitalId") Long hospitalId,
			@RequestParam("userExaminationId") Long userExaminationId, @RequestParam("name") String name,
			@DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam("birthday") LocalDate birthday,
			@RequestParam("gender") Gender gender, @RequestParam("ssnPartial") String ssnPartial,
			@RequestParam("phone") String phone,
			@DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam("dateExamined") LocalDate dateExamined,
			@RequestParam("agreeYn") Integer agreeYn, @RequestParam("specialCase") String specialCase,
			@RequestParam("agreeMail") Integer agreeMail, @RequestParam("agreeSms") Integer agreeSms,
			@RequestParam("agreeVisit") Integer agreeVisit) {

		webUserExaminationService.updateUserExamination(userExaminationId, name, birthday, gender, ssnPartial, phone,
				dateExamined, agreeYn, specialCase, agreeMail, agreeSms, agreeVisit);
		return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS));
	}
	
	@ApiOperation(value = "수검자 데이터 삭제")
	@DeleteMapping("/{userExaminationId}")
	public ResponseEntity<CommonResponse> deleteUserData(@PathVariable("userExaminationId") Long userExaminationId) {
		ResponseEntity<CommonResponse> result = webUserExaminationService.deleteUserData(userExaminationId);
		
		return result;
	}
}
