package com.blink.common;

import java.util.concurrent.TimeUnit;

import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.blink.config.aws.FileResource;

public class DownloadResponse {
	private static final String CACHE_CONTROL_VALUE = CacheControl.maxAge(3600, TimeUnit.SECONDS).getHeaderValue();

    public static ResponseEntity<byte[]> downloadResponse(FileResource fileResource) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        httpHeaders.setContentLength(fileResource.getBytes().length);
        httpHeaders.setContentDispositionFormData("attachment", fileResource.getFilename());

        httpHeaders.setCacheControl(CACHE_CONTROL_VALUE);
        return new ResponseEntity<>(fileResource.getBytes(), httpHeaders, HttpStatus.OK);
    }
}
