package com.blink.web.hospital.dto.agreeUserList;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.blink.web.admin.web.dto.WebFileResponseDto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AgreeUserListResponseDto {

	private final Long id;
	private final LocalDateTime createdAt;
	private final String groupId;
	private final Integer count;
	private final Long hospitalId;
	private final String username; 
	private List<WebFileResponseDto> files = new ArrayList<>();
}
