package com.blink.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blink.domain.notice.WebNotice;
import com.blink.domain.notice.WebNoticeRepository;
import com.blink.domain.webfiles.WebFilesRepository;
import com.blink.enumeration.FileUploadUserType;
import com.blink.enumeration.SearchPeriod;
import com.blink.util.CommonUtils;
import com.blink.util.FileUploadUtils;
import com.blink.web.admin.web.dto.WebFileResponseDto;
import com.blink.web.admin.web.dto.WebNoticeResponseDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class WebNoticeService {

	private final WebNoticeRepository webNoticeRepository;
	private final WebFilesRepository webFilesRepository;
	private final FileUploadUtils fileUploadUtils;

	public Page<WebNoticeResponseDto> getNoticeList(String title, SearchPeriod period, Pageable pageable) {

		LocalDateTime time = CommonUtils.getSearchPeriod(period);

		Page<WebNoticeResponseDto> list = webNoticeRepository.findByTitleAndPeriod(title, time, pageable);

		List<WebNoticeResponseDto> content = list.getContent();

		for (WebNoticeResponseDto dto : content) {
			String groupId = dto.getGroupId();
			if (groupId != null) {

				List<WebFileResponseDto> files = webFilesRepository.findByGroupId(groupId);
				dto.setFiles(files);
			}
		}

		return list;
	}

	public void save(String title, String description, MultipartFile[] files) {

		String groupId = fileUploadUtils.upload(files, "webNoticeFiles", FileUploadUserType.WEB, 0L, null);

		WebNotice webNotice = WebNotice.builder() //
				.title(title) //
				.description(description) //
				.build();

		if (groupId != null) {
			webNotice.setGroupId(groupId);
		}

		webNoticeRepository.save(webNotice);
	}

}
