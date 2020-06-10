package com.blink.web.hospital;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blink.common.CommonResponse;
import com.blink.common.CommonResultCode;
import com.blink.enumeration.QuestionType;
import com.blink.service.WebQnaService;
import com.blink.web.hospital.dto.WebQnaResponseDto;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController("hospitalWebQnaController")
@RequestMapping("/hospital/web/qna")
public class WebQnaController {

	private final WebQnaService webQnaService;

	@ApiOperation(value = "고객센터 - 질문 등록")
	@PostMapping("/question/{hospitalId}")
	public ResponseEntity<CommonResponse> registerQuestion(@PathVariable("hospitalId") Long hospitalId,
			@RequestParam("questionType") QuestionType questionType, @RequestParam("title") String title,
			@RequestParam("questionContent") String questionContent,
			@RequestParam(name = "file", required = false) MultipartFile[] files) {
		webQnaService.registerQuestion(hospitalId, questionType, title, questionContent, files);
		return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS));
	}

	@ApiOperation(value = "고객센터 - 해당 병원 질문 조회")
	@GetMapping("/{hospitalId}")
	public ResponseEntity<CommonResponse> getQnaList(@PathVariable("hospitalId") Long hospitalId,
			@RequestParam("searchText") Optional<String> searchText,
			@RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
			@RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate, Pageable pageable) {

		Page<WebQnaResponseDto> webQnaList = webQnaService.getHospitalQnaList(hospitalId, searchText.orElse("_"),
				startDate, endDate, pageable);

		return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS, webQnaList));
	}

}
