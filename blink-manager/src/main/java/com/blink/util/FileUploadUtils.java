package com.blink.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.io.FilenameUtils;
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

	// 검지 결과지 등록시 pdf파일의 filekey는 'upload/병원계정/...'로 처리  
	public Map<String, Object> uploadMultipleFiles(MultipartFile[] files, String uploadDirectory, String hospitalName, FileUploadUserType fileUploadUserType, Long userId, String groupId) {

		Map<String, Object> result = new HashMap<String, Object>();
		
		List<String> pdfFilekeyList = new ArrayList<String>();
		
		if (files != null && files.length > 0) {
			
			int fileId = 1;
			
			if (groupId == null) {
				groupId = webFilesRepository.findFileGroupId("fileGroup");
			} else {
				fileId = getNextFileId(groupId);
			}
			
			for (MultipartFile file : files) {
				try {
					FileResource fileResource;
					String extension = FilenameUtils.getExtension(file.getOriginalFilename());
					if ("pdf".equalsIgnoreCase(extension)) {
						fileResource = bucketService.upload(Optional.of(uploadDirectory), file);
						pdfFilekeyList.add(fileResource.getKey());
					} else {
						fileResource = bucketService.upload(Optional.of("upload/" + hospitalName), file);
					}

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
		
		result.put("groupId", groupId);
		result.put("pdfFilekeyList", pdfFilekeyList);
		
		return result;
	}
}
