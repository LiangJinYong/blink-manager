package com.blink.web.hospital;

import java.security.Principal;
import java.util.Optional;

import org.springframework.data.domain.Page;
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
import com.blink.enumeration.QuestionType;
import com.blink.enumeration.SearchPeriod;
import com.blink.service.WebQnaService;
import com.blink.web.hospital.dto.WebQnaResponseDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController("hospitalWebQnaController")
@RequestMapping("/hospital/web/qna")
public class WebQnaController {

	private final WebQnaService webQnaService;

	@PostMapping("/question")
	public ResponseEntity<CommonResponse> registerQuestion(@RequestParam("questionType") QuestionType questionType,
			@RequestParam("title") String title, @RequestParam("questionContent") String questionContent,
			@RequestParam("file") MultipartFile[] files, Principal principal) {
		String username = principal.getName();
		webQnaService.registerQuestion(questionType, title, questionContent, files, username);
		return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS));
	}

	@GetMapping
	public ResponseEntity<CommonResponse> getQnaList(@RequestParam("title") Optional<String> title,
			@RequestParam(name = "period", defaultValue = "ONEMONTH") Optional<SearchPeriod> period, Pageable pageable,
			Principal principal) {
		String username = principal.getName();
		Page<WebQnaResponseDto> webQnaList = webQnaService.getHospitalQnaList(title.orElse("_"),
				period.orElse(SearchPeriod.ONEMONTH), pageable, username);
		
		return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS, webQnaList));
	}

}
