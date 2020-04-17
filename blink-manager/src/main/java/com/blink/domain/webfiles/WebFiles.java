package com.blink.domain.webfiles;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.blink.domain.BaseTimeEntity;
import com.blink.enumeration.FileUploadUserType;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
@Entity
public class WebFiles extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String groupId;

	private Integer fileId;

	private String fileKey;

	private String fileName;

	@Enumerated(EnumType.STRING)
	private FileUploadUserType uploadUserType;

	private Long uploadUserId;

	private Long fileSize;
	
	@Builder
	public WebFiles(String groupId, Integer fileId, String fileKey, String fileName, FileUploadUserType uploadUserType,
			Long uploadUserId, Long fileSize) {
		this.groupId = groupId;
		this.fileId = fileId;
		this.fileKey = fileKey;
		this.fileName = fileName;
		this.uploadUserType = uploadUserType;
		this.uploadUserId = uploadUserId;
		this.fileSize = fileSize;
	}
}
