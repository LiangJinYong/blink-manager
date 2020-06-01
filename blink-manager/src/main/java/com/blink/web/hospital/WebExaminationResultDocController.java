package com.blink.web.hospital;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import com.blink.enumeration.SearchPeriod;
import com.blink.service.WebExaminationResultDocService;
import com.blink.web.hospital.dto.webExaminationResultDoc.WebExaminationResultDocResponseDto;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/hospital/web/examinationResultDocs")
public class WebExaminationResultDocController {

	private final WebExaminationResultDocService webExaminationResultDocService;
	
	@ApiOperation(value = "검진결과지 등록")
	@PostMapping("/{hospitalId}")
	public ResponseEntity<CommonResponse> registerExaminationResultDocs(@PathVariable("hospitalId") Long hospitalId, @RequestParam("file") MultipartFile[] files) {
		
		webExaminationResultDocService.registerExaminationResultDocs(files, hospitalId);
		return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS));
	}
 	
	@ApiOperation(value = "검진결과지 - 해당 병원 결과지 조회")
	@GetMapping("/{hospitalId}")
	public ResponseEntity<CommonResponse> getExaminationResultDocList(@PathVariable("hospitalId") Long hospitalId, @RequestParam("searchText") Optional<String> searchText,
			@RequestParam(name = "period", defaultValue = "ONEMONTH") Optional<SearchPeriod> period, Pageable pageable) {
		
		Page<WebExaminationResultDocResponseDto> result = webExaminationResultDocService.getExaminationResultDocList(searchText.orElse("_"),
				period.orElse(SearchPeriod.ONEMONTH), pageable, hospitalId);
		return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS, result));
	}
	
	@ApiOperation(value = "검진결과지 삭제")
	@DeleteMapping("/{webExaminationResultDocId}")
	public ResponseEntity<CommonResponse> deleteExaminationResultDoc(@PathVariable("webExaminationResultDocId") Long webExaminationResultDocId) {
		webExaminationResultDocService.deleteExaminationResultDoc(webExaminationResultDocId);
		return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS));
	}
}
