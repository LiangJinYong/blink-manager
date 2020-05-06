package com.blink.domain.user;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import javax.persistence.Tuple;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;

import com.blink.domain.pdf.PdfWeb;
import com.blink.enumeration.PdfProcessStatus;

public interface DataTablePdfWebRepository extends DataTablesRepository<PdfWeb, Long> {
	@Query("select p from PdfWeb p order by p.id desc")
    public List<PdfWeb> findList(Pageable pageable);


    @Query("select p from PdfWeb p where p.hospital.id = :hospitalDataId order by p.id desc")
    public List<PdfWeb> findList(Pageable pageable, Long hospitalDataId);

    @Query("select p from PdfWeb p where p.hospital.id = :hospitalDataId and p.status = :status order by p.id desc")
    public List<PdfWeb> findList(Pageable pageable, PdfProcessStatus status, Long hospitalDataId);

    @Query("select p from PdfWeb p where p.status = :status order by p.id desc")
    public List<PdfWeb> findList(Pageable pageable, PdfProcessStatus status);

    /**
     *
     * @param from
     * @param to
     * @return tuple (hospital_data_id:병원ID, cnt:PDF 업로드 갯수, done 완료갯수, wait 대기갯수(처리전, 에러))
     */
    @Query(value = "select hospital_id, count(*) cnt, sum(if(status = 2, 1, 0)) done, sum(if(status = 1 || status = 0, 1, 0)) wait  from pdf_web where created_at between :from and :to", nativeQuery = true)
    public Optional<Tuple> findStatisticsAdminDashboardResult(ZonedDateTime from, ZonedDateTime to);

    /**
     *
     * @param from
     * @param to
     * @return tuple (hospital_data_id:병원ID, cnt:PDF 업로드 갯수, done 완료갯수, wait 대기갯수(처리전, 에러))
     */
    @Query(value = "select hospital_id, count(*) cnt, sum(if(status = 2, 1, 0)) done, sum(if(status = 1 || status = 0, 1, 0)) wait  from pdf_web where created_at between :from and :to group by hospital_id", nativeQuery = true)
    public List<Tuple> findStatisticsAdminDashboardHospitalResult(ZonedDateTime from, ZonedDateTime to);

}
