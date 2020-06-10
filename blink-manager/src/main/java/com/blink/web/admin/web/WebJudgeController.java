package com.blink.web.admin.web;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blink.common.CommonResponse;
import com.blink.common.CommonResultCode;
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
			@RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
			@RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate, Pageable pageable) {

		WebJudgeResponseDto judgeInfo = webJudgeService.getJudgeInfo(searchText.orElse("_"), startDate, endDate,
				pageable);
		return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS, judgeInfo));
	}

	@ApiOperation(value = "해당 병원 가입심사 데이터 가져오기")
	@GetMapping("/{webJudgeId}")
	public ResponseEntity<CommonResponse> getJudgeDetail(@PathVariable("webJudgeId") Long webJudgeId) {

		Optional<WebJudgeDetailResponseDto> judgeDetail = webJudgeService.getJudgeDetail(webJudgeId);

		if (judgeDetail.isPresent()) {
			return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS, judgeDetail));
		} else {
			return ResponseEntity.ok(new CommonResponse(CommonResultCode.NO_SUCH_DATA));

		}
	}

	@ApiOperation(value = "가입심사 - 반려")
	@PutMapping("/reject/{webJudgeId}")
	public ResponseEntity<CommonResponse> denyUser(@PathVariable("webJudgeId") Long webJudgeId,
			@RequestParam("rejectMsg") String rejectMsg) {
		webJudgeService.denyUser(webJudgeId, rejectMsg);
		return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS));
	}

	@ApiOperation(value = "가입심사 - 승인")
	@PutMapping("/approve/{webJudgeId}")
	public ResponseEntity<CommonResponse> approveUser(@PathVariable("webJudgeId") Long webJudgeId) {
		webJudgeService.approveUser(webJudgeId);
		return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS));
	}

	@ApiOperation(value = "가입심사 - 계약완료")
	@PutMapping("/accomplish/{webJudgeId}")
	public ResponseEntity<CommonResponse> accomplishUser(@PathVariable("webJudgeId") Long webJudgeId) {
		webJudgeService.accomplishUser(webJudgeId);
		return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS));
	}
}
