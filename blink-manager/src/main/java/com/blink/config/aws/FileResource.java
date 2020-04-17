package com.blink.config.aws;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileResource {
    private byte[] bytes;
    private String filename;
    private String key;
}