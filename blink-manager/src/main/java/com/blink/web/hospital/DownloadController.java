package com.blink.web.hospital;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blink.common.DownloadResponse;
import com.blink.config.aws.FileResource;
import com.blink.service.aws.BucketService;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/hospital/web/download")
public class DownloadController {

	private final BucketService bucketService;
	
	@ApiOperation(value = "파일 다운로드")
	@GetMapping
	public ResponseEntity<byte[]> downloadFile(@RequestParam("fileKey") String fileKey) throws IOException {
		FileResource s3Resource = bucketService.download(fileKey);
		return DownloadResponse.downloadResponse(s3Resource);
	}
}
