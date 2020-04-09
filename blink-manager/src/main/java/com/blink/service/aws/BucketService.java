package com.blink.service.aws;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.blink.config.aws.FileResource;
import com.blink.config.aws.S3Bucket;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BucketService {
	private static final String DOT = ".";
	private static final String PATH_SEPARATOR = "/";

	private final S3Bucket s3Bucket;

	public BucketService(S3Bucket s3Bucket) {
		this.s3Bucket = s3Bucket;
	}

	/**
	 *
	 * oPrefix?/yyyy/MM/dd/filename
	 *
	 * @param oPrefix
	 * @param upload
	 * @return
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public FileResource upload(Optional<String> oPrefix, @RequestPart MultipartFile upload)
			throws InterruptedException, IOException {
		ZonedDateTime now = ZonedDateTime.now();
		// 서울시간 기준으로 디렉토링
		ZonedDateTime korNow = now.withZoneSameInstant(ZoneId.of("Asia/Seoul"));
		StringBuilder builder = new StringBuilder();
		oPrefix.map(prefix -> builder.append(prefix).append(PATH_SEPARATOR));
		builder.append(korNow.getYear()).append(PATH_SEPARATOR).append(korNow.getMonthValue()).append(PATH_SEPARATOR)
				.append(korNow.getDayOfMonth());
		String keyPrefix = builder.toString();
		String ext = FilenameUtils.getExtension(upload.getOriginalFilename()).toLowerCase();
		String filename = new StringBuilder().append(UUID.randomUUID().toString()).append(DOT).append(ext).toString();
		String key = s3Bucket.upload(keyPrefix, upload.getInputStream(), filename).waitForUploadResult().getKey();
		log.debug("upload file key : {}", key);
		return new FileResource(upload.getBytes(), upload.getOriginalFilename(), key);
	}

	/**
	 * pdf 파일 다운로드
	 * 
	 * @param key
	 * @return
	 */
	public FileResource download(String key) throws IOException {
		return s3Bucket.downloadFromBucket(key);
	}

	public void delete(String key) {
		s3Bucket.delete(key);
	}
}
