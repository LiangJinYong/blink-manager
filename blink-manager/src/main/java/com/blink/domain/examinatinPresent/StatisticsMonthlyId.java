package com.blink.domain.examinatinPresent;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Embeddable
public class StatisticsMonthlyId implements Serializable {
	private LocalDate date;

    private Long hospital;
}
