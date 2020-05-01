package com.blink.web.hospital.dto.agreeUserList;

import org.springframework.data.domain.Page;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AgreeUserResponseDto {

	private final Integer totalCount;
	private final Page<AgreeUserListResponseDto> agreeUserLists;
}
