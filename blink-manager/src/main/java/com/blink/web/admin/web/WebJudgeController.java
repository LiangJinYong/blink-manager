package com.blink.web.admin.web;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blink.common.CommonResponse;
import com.blink.common.CommonResultCode;
import com.blink.enumeration.SearchPeriod;
import com.blink.service.WebJudgeService;
import com.blink.web.admin.web.dto.WebJudgeDetailResponseDto;
import com.blink.web.admin.web.dto.WebJudgeResponseDto;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/web/judges")
public class WebJudgeController {

	private final WebJudgeService webJudgeService;
	
	@ApiOperation(value = "심사 리스트 조회")
	@GetMapping
	public ResponseEntity<CommonResponse> getJudgeList(@RequestParam("searchText") Optional<String> searchText,
			@RequestParam(name = "period", defaultValue = "ONEMONTH") Optional<SearchPeriod> period,
			Pageable pageable) {

		WebJudgeResponseDto judgeInfo = webJudgeService.getJudgeInfo(searchText.orElse("_"), period.orElse(SearchPeriod.ONEMONTH), pageable);
		return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS, judgeInfo));
	}
	
	@ApiOperation(value = "해당 병원 가입심사 데이터 가져오기")
	@GetMapping("/{webJudgeId}")
	public ResponseEntity<CommonResponse> getJudgeDetail(@RequestParam("webJudgeId") Long webJudgeId) {
		
		Optional<WebJudgeDetailResponseDto> judgeDetail = webJudgeService.getJudgeDetail(webJudgeId);
		
		if (judgeDetail.isPresent()) {
			return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS, judgeDetail));
		} else {
			return ResponseEntity.ok(new CommonResponse(CommonResultCode.NO_SUCH_DATA));
			
		}
	}
	
	@ApiOperation(value = "가입심사 - 반려")
	@PutMapping("/reject")
	public ResponseEntity<CommonResponse> denyUser(@RequestParam("webJudgeId") Long webJudgeId, @RequestParam("rejectMsg") String rejectMsg) {
		webJudgeService.denyUser(webJudgeId, rejectMsg);
		return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS));
	}
	
	@ApiOperation(value = "가입심사 - 승인")
	@PutMapping("/approve")
	public ResponseEntity<CommonResponse> approveUser(@RequestParam("webJudgeId") Long webJudgeId) {
		webJudgeService.approveUser(webJudgeId);
		return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS));
	}
	
	@ApiOperation(value = "가입심사 - 계약완료")
	@PutMapping("/accomplish")
	public ResponseEntity<CommonResponse> accomplishUser(@RequestParam("webJudgeId") Long webJudgeId) {
		webJudgeService.accomplishUser(webJudgeId);
		return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS));
	}
}
