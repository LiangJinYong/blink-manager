package com.blink.util;

import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.blink.config.aws.FileResource;
import com.blink.domain.webfiles.WebFiles;
import com.blink.domain.webfiles.WebFilesRepository;
import com.blink.enumeration.FileUploadUserType;
import com.blink.service.aws.BucketService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class FileUploadUtils {

	private final WebFilesRepository webFilesRepository;
	private final BucketService bucketService;
	
	public String upload(MultipartFile[] files, String uploadDirectory, FileUploadUserType fileUploadUserType, Long userId, String groupId) {

		if (files != null && files.length > 0) {
			
			int fileId = 1;
			
			if (groupId == null) {
				groupId = webFilesRepository.findFileGroupId("fileGroup");
			} else {
				fileId = getNextFileId(groupId);
			}
			
			for (MultipartFile file : files) {
				try {
					FileResource fileResource = bucketService.upload(Optional.of(uploadDirectory), file);

					String imgUrlKey = fileResource.getKey();
					long fileSize = file.getSize();
					String filename = file.getOriginalFilename();

					WebFiles webFiles = WebFiles.builder() //
							.groupId(groupId) //
							.fileId(fileId) //
							.fileKey(imgUrlKey) //
							.fileName(filename) //
							.uploadUserType(fileUploadUserType) //
							.uploadUserId(userId) //
							.fileSize(fileSize) //
							.build();
					webFilesRepository.save(webFiles);
				} catch (Exception e) {
					e.printStackTrace();
				}

				fileId++;
			}
		}
		
		return groupId;
	}

	private int getNextFileId(String groupId) {

		int maxFileId = webFilesRepository.findMaxFileIdByGroupId(groupId);
		return maxFileId + 1;
	}
}
