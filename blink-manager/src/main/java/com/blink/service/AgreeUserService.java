package com.blink.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blink.domain.admin.Admin;
import com.blink.domain.admin.AdminRepository;
import com.blink.domain.agreeUser.AgreeUserList;
import com.blink.domain.agreeUser.AgreeUserListRepository;
import com.blink.domain.webfiles.WebFiles;
import com.blink.domain.webfiles.WebFilesRepository;
import com.blink.enumeration.FileUploadUserType;
import com.blink.enumeration.SearchPeriod;
import com.blink.service.aws.BucketService;
import com.blink.util.CommonUtils;
import com.blink.util.FileUploadUtils;
import com.blink.web.admin.web.dto.WebFileResponseDto;
import com.blink.web.hospital.dto.agreeUserList.AgreeUserListResponseDto;
import com.blink.web.hospital.dto.agreeUserList.AgreeUserResponseDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class AgreeUserService {

	private final AgreeUserListRepository agreeUserListRepository;
	private final WebFilesRepository webFilesRepository;
	private final FileUploadUtils fileUploadUtils;
	private final AdminRepository adminRepository;
	private final BucketService bucketService;

	// ------------------ HOSPITAL ------------------
	public void registerAgreeUserList(MultipartFile[] files, String username) {

		Admin user = adminRepository.findByName(username);
		Long hospitalId = user.getHospital().getId();

		String groupId = fileUploadUtils.upload(files, "agreeUserListFiles", FileUploadUserType.WEB, hospitalId, null);

		AgreeUserList agreeUserList = AgreeUserList.builder() //
				.hospital(user.getHospital()) //
				.groupId(groupId) //
				.build();
		
		agreeUserListRepository.save(agreeUserList);
	}

	public AgreeUserResponseDto getHospitalAgreeUserInfo(String searchText, SearchPeriod period, Pageable pageable,
			String username) {
		Admin user = adminRepository.findByName(username);
		Long hospitalId = user.getHospital().getId();

		LocalDateTime time = CommonUtils.getSearchPeriod(period);
		
		Page<AgreeUserListResponseDto> agreeUserLists = agreeUserListRepository.findBySearchTextAndPeriodWithHospital(time, hospitalId, pageable);
		
		List<AgreeUserListResponseDto> content = agreeUserLists.getContent();
		
		for(AgreeUserListResponseDto dto: content) {
			String groupId = dto.getGroupId();
			if (groupId != null) {
				List<WebFileResponseDto> questionFiles = webFilesRepository.findByGroupId(groupId);
				dto.setFiles(questionFiles);
			}
		}
		
		AgreeUserResponseDto responseDto = new AgreeUserResponseDto(agreeUserLists);
		
		return responseDto;
	}

	public void updateAgreeUserListInfo(Long agreeUserListId, String[] groupFileIdsToBeDeleted, MultipartFile[] files, String username) {
		
		// Delete specified files
		for(String groupFileId: groupFileIdsToBeDeleted) {
			String[] split = groupFileId.split("-");
			
			String groupId = split[0];
			Integer fileId = Integer.parseInt(split[1]);
			
			Optional<WebFiles> webFile = webFilesRepository.findByGroupIdAndFileId(groupId, fileId);
			
			if (webFile.isPresent()) {
				WebFiles webFileEntity = webFile.get();
				bucketService.delete(webFileEntity.getFileKey());
				webFilesRepository.delete(webFileEntity);
			}
		}
		
		// Upload new files
		Admin user = adminRepository.findByName(username);
		Long hospitalId = user.getHospital().getId();
		
		AgreeUserList agreeUserList = agreeUserListRepository.findById(agreeUserListId).orElseThrow(() -> new IllegalArgumentException("No such agree user list"));
		
		fileUploadUtils.upload(files, "agreeUserListFiles", FileUploadUserType.WEB, hospitalId, agreeUserList.getGroupId());
	}

	public void deleteAgreeUserListInfo(Long agreeUserListId) {
		AgreeUserList agreeUserList = agreeUserListRepository.findById(agreeUserListId).orElseThrow(() -> new IllegalArgumentException("No such agree user list"));
		
		String groupId = agreeUserList.getGroupId();
		
		List<WebFileResponseDto> webFileList = webFilesRepository.findByGroupId(groupId);
		
		for(WebFileResponseDto dto: webFileList) {
			String fileKey = dto.getFileKey();
			bucketService.delete(fileKey);
		}
		
		webFilesRepository.deleteByGroupId(groupId);
		
		agreeUserListRepository.delete(agreeUserList);
	}

	public com.blink.web.admin.web.dto.agreeUserList.AgreeUserResponseDto getAdminAgreeUserInfo(String title,
			SearchPeriod period, Pageable pageable) {

LocalDateTime time = CommonUtils.getSearchPeriod(period);
		
		Page<AgreeUserListResponseDto> agreeUserLists = agreeUserListRepository.findBySearchTextAndPeriodWithAdmin(time, pageable);
		
		List<AgreeUserListResponseDto> content = agreeUserLists.getContent();
		
		for(AgreeUserListResponseDto dto: content) {
			String groupId = dto.getGroupId();
			if (groupId != null) {
				List<WebFileResponseDto> questionFiles = webFilesRepository.findByGroupId(groupId);
				dto.setFiles(questionFiles);
			}
		}
		
		com.blink.web.admin.web.dto.agreeUserList.AgreeUserResponseDto responseDto = new com.blink.web.admin.web.dto.agreeUserList.AgreeUserResponseDto(agreeUserLists);
		
		return responseDto;
	}
}
