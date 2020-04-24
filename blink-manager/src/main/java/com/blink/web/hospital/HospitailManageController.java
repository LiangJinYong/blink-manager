package com.blink.web.hospital;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blink.common.CommonResponse;
import com.blink.common.CommonResultCode;
import com.blink.service.HospitalService;
import com.blink.web.admin.web.dto.hospital.HospitalDetailResponseDto;
import com.blink.web.hospital.dto.hospital.HospitalUpdateRequestDto;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController("normalHospitailManageController")
@RequestMapping("/hospital/web/hospitals")
public class HospitailManageController {

	private final HospitalService hospitalService;

	@ApiOperation(value = "병원관리 - 해당 병원정보 가져오기")
	@GetMapping("/{hospitalId}")
	public ResponseEntity<CommonResponse> getHospitalDetail(@PathVariable("hospitalId") Long hospitalId) {

		HospitalDetailResponseDto result = hospitalService.getHospitalDetail(hospitalId);
		return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS, result));
	}

	@ApiOperation(value = "병원관리 - 병원정보 수정")
	@PutMapping("/{hospitalId}")
	public ResponseEntity<CommonResponse> updateHospitalDetail(@PathVariable("hospitalId") Long hospitalId,
			@RequestParam("hospitalName") String hospitalName, @RequestParam("employeeName") String employeeName,
			@RequestParam("hospitalTel") String hospitalTel, @RequestParam("employeeTel") String employeeTel,
			@RequestParam("agreeSendYn") Integer agreeSendYn, @RequestParam("postcode") String postcode,
			@RequestParam("address") String address, @RequestParam("addressDetail") String addressDetail,
			@RequestParam("employeeEmail") String employeeEmail, @RequestParam("programInUse") String programInUse,
			@RequestParam(name = "signagesStand", required = false) Integer signagesStand,
			@RequestParam(name = "signagesMountable", required = false) Integer signagesMountable,
			@RequestParam(name = "signagesExisting", required = false) Integer signagesExisting,
			@RequestParam(name = "groupFileId", required = false) String groupFileId,
			@RequestParam("employeePosition") String employeePosition,
			@RequestParam(name = "file", required = false) MultipartFile[] files) {

		HospitalUpdateRequestDto requestDto = HospitalUpdateRequestDto.builder() //
				.hospitalId(hospitalId) //
				.hospitalName(hospitalName) //
				.employeeName(employeeName) //
				.hospitalTel(hospitalTel) //
				.employeeTel(employeeTel) //
				.agreeSendYn(agreeSendYn) //
				.postcode(postcode) //
				.address(address) //
				.addressDetail(addressDetail) //
				.employeeEmail(employeeEmail) //
				.programInUse(programInUse) //
				.signagesStand(signagesStand) //
				.signagesMountable(signagesMountable) //
				.signagesExisting(signagesExisting) //
				.employeePosition(employeePosition) //
				.groupFileId(groupFileId)
				.build();

		hospitalService.updateHospitalDetail(requestDto, files);
		return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS));
	}
	
	@ApiOperation(value = "병원관리 - 병원 탈퇴")
	@DeleteMapping("/{hospitalId}")
	public ResponseEntity<CommonResponse> deleteHospital(@PathVariable("hospitalId") Long hospitalId) {
		hospitalService.deleteHospital(hospitalId);
		return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS));
	}
}
