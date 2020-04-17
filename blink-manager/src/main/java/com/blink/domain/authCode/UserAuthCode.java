package com.blink.domain.authCode;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.blink.domain.BaseTimeEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "web_auth_code")
public class UserAuthCode extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Integer authCode;

	@Column(nullable = false)
	private LocalDateTime expireDate;

	@Column(nullable = false)
	private String phoneNumber;

	@Column(nullable = false)
	private boolean authYn;

	@Column(nullable = false)
	private boolean signupYn;
	
	public void reSetAuthCode(Integer authCode) {
		this.authCode = authCode;
	}
	
	public void reSetExpireDate(LocalDateTime expireDate) {
		this.expireDate = expireDate;
	}
	
	public void setAuthYn(boolean authYn) {
		this.authYn = authYn;
	}
	
	public void setSignupYn(boolean signupYn) {
		this.signupYn = signupYn;
	}
	
	@Builder
	public UserAuthCode(Integer authCode, LocalDateTime expireDate, String phoneNumber, boolean authYn, boolean signupYn) {
		this.authCode = authCode;
		this.expireDate = expireDate;
		this.phoneNumber = phoneNumber;
		this.authYn = authYn;
		this.signupYn = signupYn;
	}
}
