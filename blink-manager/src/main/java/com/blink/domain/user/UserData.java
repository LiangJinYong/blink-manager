package com.blink.domain.user;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.blink.domain.BaseTimeEntity;
import com.blink.enumeration.Gender;
import com.blink.enumeration.Nationality;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class UserData extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(columnDefinition = "varchar(32) not null comment '이름'", nullable = false)
	private String name;

	@Column(columnDefinition = "date not null comment '생년월일'", nullable = false)
	private LocalDate birthday;

	@Column(columnDefinition = "tinyint comment '성별'", nullable = false)
	@Enumerated(EnumType.ORDINAL)
	private Gender gender;

	@Column(columnDefinition = "tinyint comment '국적 0:native 1:foreigner'")
	@Enumerated(EnumType.ORDINAL)
	private Nationality nationality;

	@Column(columnDefinition = "varchar(12) comment '전화번호'")
	private String phone;

	@Column(name = "delete_yn", nullable = false, columnDefinition = "bit default 0 comment '삭제 여부'")
	private boolean deleteYN;

	@Column(nullable = false, columnDefinition = "varchar(7) comment '주민등록번호 앞자리 + 성별'")
	private String ssnPartial;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "userData")
	private List<UserExaminationMetadata> userExaminationMetadatas;

	@Builder
	public UserData(String name, Nationality nationality, String phone, LocalDate birthday, Gender gender,
			String ssnPartial) {
		this.name = name;
		this.nationality = nationality;
		this.phone = phone;
		this.birthday = birthday;
		this.gender = gender;
		this.ssnPartial = ssnPartial;
	}
	
	public void update(String name, LocalDate birthday, Gender gender, Nationality nationality) {
		this.name = name;
		this.birthday = birthday;
		this.gender = gender;
		this.nationality = nationality;
	}
	
	public void updateForUserExamination(String name, LocalDate birthday, Gender gender, String ssnPartial, String phone, Nationality nationality) {
		this.name = name;
		this.birthday = birthday;
		this.gender = gender;
		this.ssnPartial = ssnPartial;
		this.phone = phone;
		this.nationality = nationality;
	}
}
