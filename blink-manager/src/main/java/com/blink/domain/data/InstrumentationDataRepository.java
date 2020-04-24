package com.blink.domain.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("instrumentationDataRepositoryForInsert")
public interface InstrumentationDataRepository extends JpaRepository<InstrumentationData, Long> {

}
