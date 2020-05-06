package com.blink.domain.examinatinPresent;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.blink.domain.BaseEntity;
import com.blink.domain.StatisticsInterface;
import com.blink.domain.hospital.Hospital;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(StatisticsDailyId.class)
@Entity
public class StatisticsDaily extends BaseEntity implements StatisticsInterface {

	@Id
	private LocalDate date;

	@Id
	@JoinColumn(name = "hospital_id", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT, name = "NONE"))
	@ManyToOne(fetch = FetchType.LAZY)
	private Hospital hospital;

	@Column(columnDefinition = "bigint(20) comment '검진 건수' default 0")
	private long examinedCount;

	@Column(columnDefinition = "bigint(20) comment '동의 숫자' default 0")
	private long agreeYCount;

	@Column(columnDefinition = "bigint(20) comment '비동의 숫자' default 0")
	private long agreeNCount;

	@Override
	public long getAgreeYCount() {
		return agreeYCount;
	}

	@Override
	public long getAgreeNCount() {
		return agreeNCount;
	}

	@Override
	public LocalDate getLocalDate() {
		return date;
	}

	@Override
	public long getExaminedCount() {
		return examinedCount;
	}

}
