package com.blink.domain.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.blink.domain.BaseTimeEntity;
import com.blink.enumeration.GenderType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 사용자 정보 엔티티
 */
@Data
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(
		name = "user_info", 
		indexes = { 
				@Index(name = "IDX_1", unique=false, columnList = "birthday, name, phone_number, gender")
		}	
)
public class UserInfo extends BaseTimeEntity {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	private int age;
	
	@Column(name="phone_number", columnDefinition = "varchar(20) comment '사용자 핸드폰 번호'")
	private String phoneNumber;
	
	@Column(name="email", columnDefinition = "varchar(64) comment '사용자 이메일 주소'")
	private String email;
	
	@JsonIgnore
	@Column(name="pin_code", columnDefinition = "varchar(64) comment '사용자 간편 비밀번호'")
	private String pinCode;
	
    @JsonIgnore
    @Column(name="auth_yn", nullable = false, columnDefinition="bit default 0 comment '사용자 인증 유무'")
    private Boolean authYN;
    
    @JsonIgnore
    @Column(name="signup_yn", nullable = false, columnDefinition="bit default 0 comment '사용자 가입 완료 유무'")
    private Boolean signupYN;
    
    @Column(name="birthday", columnDefinition = "varchar(7) comment '사용자 생년월일'")
    private String birthDay;
    
    @Enumerated(EnumType.STRING)
    @Column(name="gender", columnDefinition = "varchar(8) default 'UNKNOWN' comment '사용자 성별'")
    private GenderType gender;
    
    @Transient
	private String displayBirthDay;
    
    @Transient
    @JsonIgnore
	private long userDataId;
    
    public String getBirthDay() {
    	if(birthDay != null) {
    		this.displayBirthDay = GenderType.getGeneralBirthDay(birthDay);	
    	}
    	
    	return birthDay;
    }
}
