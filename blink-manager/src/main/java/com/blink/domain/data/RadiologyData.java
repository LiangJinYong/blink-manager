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
public class RadiologyData implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "varchar(255) comment '흉부엑스레이 촬영 결과 데이터 저장'")
    private String chestXRay;

    @Column(columnDefinition = "varchar(255) comment '흉부엑스레이 촬영 상세 결과 데이터 저장'")
    private String chestXRayResult;
    
    @Builder
    public RadiologyData(String chestXRay, String chestXRayResult) {
    	this.chestXRay = chestXRay;
    	this.chestXRayResult = chestXRayResult;
    }

}
