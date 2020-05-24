package com.blink.web.hospital;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blink.common.CommonResponse;
import com.blink.common.CommonResultCode;
import com.blink.service.WebSignService;
import com.blink.web.hospital.dto.webSign.WebSignResponseDto;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/hospital/web/sign")
public class WebSignController {

	private final WebSignService webSignService;

	@ApiOperation(value = "사인 리스트 가져오기")
	@GetMapping("/{hospitalId}")
	public ResponseEntity<CommonResponse> getSignList(@PathVariable("hospitalId") Long hospitalId) {

		List<WebSignResponseDto> result = webSignService.getSignList(hospitalId);
		return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS, result));
	}

	@ApiOperation(value = "사인 등록")
	@PostMapping("/{hospitalId}")
	public ResponseEntity<CommonResponse> registerSign(@PathVariable("hospitalId") Long hospitalId, @RequestParam("doctorName") String doctorName, @RequestParam("doctorPhone") String doctorPhone, @RequestParam("doctorId") String doctorId,@RequestParam("license") String license, @RequestParam("file") MultipartFile[] files) {

		CommonResultCode resultCode = webSignService.registerSign(hospitalId, doctorName, doctorPhone, doctorId, license, files);
		return ResponseEntity.ok(new CommonResponse(resultCode));
	}

	@ApiOperation(value = "사인 삭제")
	@DeleteMapping("/{doctorId}")
	public ResponseEntity<CommonResponse> deleteSign(@PathVariable("doctorId") String doctorId) {

		webSignService.deleteSign(doctorId);
		return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS));
	}
}
