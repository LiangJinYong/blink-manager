package com.blink.domain.user;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 사용자 정보 엔티티
 */
@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserToken implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @Column(nullable = false, name = "access_token", length = 64)
    private String accessToken;
	
    @Column(nullable = false, name = "refresh_token", length = 64)
    private String refreshToken;
    
    @Column(name = "push_token")
    private String pushToken;

    @JsonIgnore
    @Column(nullable = false, name = "expire_date")
    private LocalDateTime expireDate;
    
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name="none",value = ConstraintMode.NO_CONSTRAINT))
    private UserInfo userInfo;
    
	@Column(updatable = false)
	private LocalDateTime createdAt;
	
	@Column
	private LocalDateTime updatedAt;
    
	@PrePersist
    private void onPersist() {
            this.createdAt = this.updatedAt = LocalDateTime.now();
    }
	
    @PreUpdate
    private void onUpdate() {
            this.updatedAt = LocalDateTime.now();
    }
    
    public void updatePushToken(String pushToken) {
    	this.pushToken = pushToken;
    }
}
