package com.blink.domain.data;

import java.io.Serializable;

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
public class SurveyData implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String smokerCategory;
    private String levelOfNicotineDependence;
    private String drinkingCategory;
    private String exerciseStatus;
    private String nutritionStatus;
    private String obesityStatus;
    private String pastHistoryOrPHx;
    private String drugTreatment;
    private String lifeStyle;
    
    private String pastDiseaseHistory;
    private String nowDrugTreatment;
    private String recommendationsForLifeStyle;
    
    @Builder
    public SurveyData(String pastDiseaseHistory, String nowDrugTreatment, String recommendationsForLifeStyle) {
    	this.pastDiseaseHistory = pastDiseaseHistory;
    	this.nowDrugTreatment = nowDrugTreatment;
    	this.recommendationsForLifeStyle = recommendationsForLifeStyle;
    }
}
