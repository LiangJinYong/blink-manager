package com.blink.web.hospital;

import java.security.Principal;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blink.common.CommonResponse;
import com.blink.common.CommonResultCode;
import com.blink.enumeration.SearchPeriod;
import com.blink.service.AgreeUserService;
import com.blink.web.hospital.dto.agreeUserList.AgreeUserResponseDto;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController("hospitalAgreeUserController")
@RequestMapping("/hospital/web/agreeUserLists")
public class AgreeUserController {

	private final AgreeUserService agreeUserService;

	@ApiOperation(value = "동의자 리스트 등록")
	@PostMapping
	public ResponseEntity<CommonResponse> registerAgreeUserList(@RequestParam("file") MultipartFile[] files,
			Principal principal) {

		if (files.length > 2) {
			throw new RuntimeException("The number of uploaded files cannot be more than 2.");
		}

		for (MultipartFile file : files) {
			if (!file.getOriginalFilename().endsWith(".xls") && !file.getOriginalFilename().endsWith(".xlsx")) {
				throw new RuntimeException("The type of uploaded files must be .xls or .xlsx.");
			}
		}

		String username = principal.getName();

		agreeUserService.registerAgreeUserList(files, username);

		return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS));
	}

	@ApiOperation(value = "해당 병원 동의자 리스트 가져오기", notes = "필요한 정보외에 groupFileId(형식 groupId-FileId)도 같이 전달, 동이자 라시트 정보 수정시 사용")
	@GetMapping
	public ResponseEntity<CommonResponse> getAgreeUserInfo(@RequestParam("searchText") Optional<String> searchText,
			@RequestParam(name = "period", defaultValue = "ONEMONTH") Optional<SearchPeriod> period, Pageable pageable,
			Principal principal) {

		String username = principal.getName();

		AgreeUserResponseDto responseDto = agreeUserService.getHospitalAgreeUserInfo(searchText.orElse("_"),
				period.orElse(SearchPeriod.ONEMONTH), pageable, username);

		return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS, responseDto));
	}

	@ApiOperation(value = "동의자 리스트 수정", notes="삭제된 파일의 groupFileId를 넘겨야함")
	@PutMapping("/{agreeUserListId}")
	public ResponseEntity<CommonResponse> updateAgreeUserListInfo(@PathVariable("agreeUserListId") Long agreeUserListId,
			@RequestParam("groupFileId") String[] groupFileIdsToBeDeleted,
			@RequestParam(name = "file", required = false) MultipartFile[] files, Principal principal) {

		String username = principal.getName();
		agreeUserService.updateAgreeUserListInfo(agreeUserListId, groupFileIdsToBeDeleted, files, username);
		return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS));
	}

	@DeleteMapping("/{agreeUserListId}")
	@ApiOperation(value = "동의자 리스트 수정")
	public ResponseEntity<CommonResponse> deleteAgreeUserListInfo(@PathVariable("agreeUserListId") Long agreeUserListId) {
		agreeUserService.deleteAgreeUserListInfo(agreeUserListId);
		return ResponseEntity.ok(new CommonResponse(CommonResultCode.SUCCESS));
	}
}
