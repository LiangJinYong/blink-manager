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
public class BoneData implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "varchar(255) comment '골다공증 검사 해당/비해당 여부에 관한 데이터'")
    private String boneDensityInspection;
    @Column(columnDefinition = "varchar(255) comment '골다공증 수치'")
    private String boneDensityValue;
    @Column(columnDefinition = "varchar(255) comment '골다공증 판정'")
    private String boneDensityResult;
    
    @Builder
    public BoneData(String boneDensityInspection, String boneDensityValue, String boneDensityResult) {
    	this.boneDensityInspection = boneDensityInspection;
    	this.boneDensityValue = boneDensityValue;
    	this.boneDensityResult = boneDensityResult;
    }

}
