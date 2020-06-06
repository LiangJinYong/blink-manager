package com.blink.web.admin.app;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blink.common.CommonResponse;
import com.blink.common.CommonResultCode;
import com.blink.service.ParserService;
import com.blink.service.WebExaminationResultDocService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController("AppWebExaminationResultDocMobileController")
@RequestMapping("/admin/app/examinationResultDocMobile")
public class WebExaminationResultDocMobileController {

	private final ParserService parserService;
	private final WebExaminationResultDocService webExaminationResultDocService;
	
	@PostMapping("/{examinationResultDocMobileId}")
	public ResponseEntity<CommonResponse> registerExaminationDataMobile(@PathVariable("examinationResultDocMobileId") Long examinationResultDocMobileId, @RequestBody Map<String, Object> param) {
		
		List<Map<String, Object>> result = parserService.registerExaminationDataMobile(examinationResultDocMobileId, param);
		if (result.size() == 0) {
			return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS));
		}
		
		return ResponseEntity.ok(new CommonResponse(CommonResultCode.MOBILE_REGISTER_EXAMINATION_DATA_ERROR, result));
	}
	
	@DeleteMapping("/{examinationResultDocMobileId}")
	public ResponseEntity<CommonResponse> deleteExaminationDataMobile(@PathVariable("examinationResultDocMobileId") Long examinationResultDocMobileId) {
		webExaminationResultDocService.deleteExaminationDataMobile(examinationResultDocMobileId);
		return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS));
	}
}
