package com.blink.domain.sendMailResultWeb;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class FileInfo {
	private static final long serialVersionUID = 1L;

    @Column(columnDefinition="varchar(255) comment 'S3Key'")
    private String filekey;

    @Column(columnDefinition="varchar(255) comment '업로드 파일명'")
    private String filename;
}
