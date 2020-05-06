package com.blink.util;

import java.io.IOException;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.blink.config.aws.FileResource;
import com.blink.domain.sendMailResultWeb.FileInfo;
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

					String filekey = fileResource.getKey();
					long fileSize = file.getSize();
					String filename = file.getOriginalFilename();

					WebFiles webFiles = WebFiles.builder() //
							.groupId(groupId) //
							.fileId(fileId) //
							.fileKey(filekey) //
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
	
	public FileInfo uploadPdfFile(MultipartFile file, String hospitalName) {
		FileResource fileResource;
		try {
			fileResource = bucketService.upload(Optional.of("upload/" + hospitalName), file);
			String filekey = fileResource.getKey();
			String filename = file.getOriginalFilename();
			FileInfo fileInfo = new FileInfo(filekey, filename);
			return fileInfo;
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public FileInfo uploadIndividualFile(MultipartFile file) {
		FileResource fileResource;
		try {
			fileResource = bucketService.upload(Optional.of("upload"), file);
			String filekey = fileResource.getKey();
			String filename = file.getOriginalFilename();
			FileInfo fileInfo = new FileInfo(filekey, filename);
			return fileInfo;
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
