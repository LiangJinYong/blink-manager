package com.blink.domain.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class HepatitisData implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(columnDefinition = "varchar(255) comment 'B형간염 해당/비해당 여부 데이터 저장'")
    private String hepatitisbInspection;
    @Column(columnDefinition = "varchar(255) comment '표면항원에 대한 데이터 저장'")
    private String hepatitisbSurfaceAntigen;
    @Column(columnDefinition = "varchar(255) comment '표면항체에 대한 데이터 저장'")
    private String hepatitisbSurfaceAntibody;
    @Column(columnDefinition = "varchar(255) comment 'B형간염에 대한 결과데이터 저장'")
    private String hepatitisbResult;

    @Builder
    public HepatitisData(String hepatitisbInspection, String hepatitisbSurfaceAntibody, String hepatitisbSurfaceAntigen, String hepatitisbResult) {
    	this.hepatitisbInspection = hepatitisbInspection;
    	this.hepatitisbSurfaceAntibody = hepatitisbSurfaceAntibody;
    	this.hepatitisbSurfaceAntigen = hepatitisbSurfaceAntigen;
    	this.hepatitisbResult = hepatitisbResult;
    }
}
