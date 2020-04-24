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
public class CommentData implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "varchar(255) comment '건강검진 이후 최종 종합 판정 소견'")
    private String finalInterpretation;
    @Column(columnDefinition = "varchar(255) comment '의심질환 항목들'")
    private String suspiciousDisease;
    @Column(columnDefinition = "varchar(255) comment '유질환 항목들'")
    private String existingDisease;
    @Column(columnDefinition = "varchar(255) comment '생활습관관리에 관한 항목들'")
    private String lifeHabitManagement;
    @Column(columnDefinition = "varchar(255) comment '기타 코멘트 문장들'")
    private String otherComments;
    @Column(columnDefinition = "varchar(255) comment '생활습관관리에 관한 항목들'")
    private String howLifeHabitManagement;
    
    @Builder
    public CommentData(String finalInterpretation, String suspiciousDisease, String existingDisease, String howLifeHabitManagement, String otherComments) {
    	this.finalInterpretation = finalInterpretation;
    	this.suspiciousDisease = suspiciousDisease;
    	this.existingDisease = existingDisease;
    	this.howLifeHabitManagement = howLifeHabitManagement;
    	this.otherComments = otherComments;
    }
}
