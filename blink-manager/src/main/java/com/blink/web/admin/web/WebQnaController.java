package com.blink.web.admin.web;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blink.common.CommonResponse;
import com.blink.common.CommonResultCode;
import com.blink.enumeration.SearchPeriod;
import com.blink.service.WebQnaService;
import com.blink.web.admin.web.dto.WebQnaAdminResponseDto;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController("adminWebQnaController")
@RequestMapping("/admin/web/qna")
public class WebQnaController {

	private final WebQnaService webQnaService;

	@ApiOperation(value = "고객센터 - 질문 답변")
	@PostMapping("/answer")
	public ResponseEntity<CommonResponse> registerAnswer(@RequestParam("qnaId") Long qnaId,
			@RequestParam("answerContent") String answerContent,
			@RequestParam(name = "file", required = false) MultipartFile[] files) {

		webQnaService.registerAnswer(qnaId, answerContent, files);
		return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS));
	}

	@ApiOperation(value = "고객센터 - 전체 조회")
	@GetMapping
	public ResponseEntity<CommonResponse> getQnaList(@RequestParam("title") Optional<String> title,
			@RequestParam(name = "period", defaultValue = "ONEMONTH") Optional<SearchPeriod> period, Pageable pageable) {

		WebQnaAdminResponseDto webQnaAdmin = webQnaService.getAdminQnaInfo(title.orElse("_"),
				period.orElse(SearchPeriod.ONEMONTH), pageable);

		return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS, webQnaAdmin));
	}

}
