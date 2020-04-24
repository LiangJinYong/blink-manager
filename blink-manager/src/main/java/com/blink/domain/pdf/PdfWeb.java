package com.blink.domain.pdf;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.tomcat.jni.FileInfo;

import com.blink.domain.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
//@Table(indexes = {@Index(name = "hospitalId", columnList = "hospitalId")})
public class PdfWeb extends BaseEntity {

    private static final long serialVersionUID = 4345026762797135575L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private FileInfo fileInfo;

}
