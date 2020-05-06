package com.blink.domain;

import java.time.LocalDate;

public interface StatisticsInterface {
	public LocalDate getLocalDate();

    public long getExaminedCount();
    public long getAgreeYCount();
    public long getAgreeNCount();
}
