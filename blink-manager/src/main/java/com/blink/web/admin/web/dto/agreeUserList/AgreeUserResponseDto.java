package com.blink.web.admin.web.dto.agreeUserList;

import org.springframework.data.domain.Page;

import com.blink.web.hospital.dto.agreeUserList.AgreeUserListResponseDto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AgreeUserResponseDto {

	private final Integer totalCount;
	private final Page<AgreeUserListResponseDto> agreeUserLists;
}
