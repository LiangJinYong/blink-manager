package com.blink.domain.user;

import java.time.ZonedDateTime;
import java.util.List;

import javax.persistence.Tuple;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DataTableUserExaminationMetadataRepository
		extends DataTablesRepository<UserExaminationMetadata, Long> {
	/**
	 * 기간내 병원별 동의 여부 통계
	 * 
	 * @param from
	 * @param to
	 * @return
	 */
	@Query(value = "select date(agree_date) dt, count(id) cnt, hospital_data_id, sum(if(agree_yn = 0, 1, 0)) y, sum(if(agree_yn = 1, 1, 0)) n from user_examination_metadata m where agree_yn in (0, 1) and agree_date between :from and :to  group by hospital_data_id, dt", nativeQuery = true)
	List<Tuple> findStatisticsDailyWithHospital(@Param("from") ZonedDateTime from, @Param("to") ZonedDateTime to);

	/**
	 * 기간내 병원별 검진결과 입력수 통계 조회
	 * 
	 * @param from
	 * @param to
	 * @return
	 */
	@Query(value = "select count(id) cnt, hospital_data_id from user_examination_metadata m where created_at between :from and :to  group by hospital_data_id", nativeQuery = true)
	List<Tuple> findExaminedDateCountWithHospital(@Param("from") ZonedDateTime from, @Param("to") ZonedDateTime to);

	@Query(value = "select if(age_group_o >= 90, '90대 이상', concat(age_group_o, '대')) `group`, cnt from (\n"
			+ "  select floor(if(MONTH(u.birthday) * 100 + DAY(u.birthday) > MONTH(curdate()) * 100 + DAY(curdate()), year(curdate()) - year(u.birthday), year(curdate()) - year(u.birthday) - 1) / 10) * 10 as age_group_o,\n"
			+ "  count(u.id) as cnt\n" + "  from user_examination_metadata m\n"
			+ "  inner join user_data u on m.user_data_id = u.id\n" + "  where hospital_data_id = :hospitalDataId \n"
			+ "  group by age_group_o\n" + ") as t\n" + " group by `group`;", nativeQuery = true)
	List<Tuple> findAgeGroupChartResult(@Param("hospitalDataId") Long hospitalDataId);

	@Query(value = "select concat(u.gender) as `group`, count(u.id) as cnt\n" + "   from user_examination_metadata m\n"
			+ "   inner join user_data u on m.user_data_id = u.id\n" + "   where hospital_data_id = :hospitalDataId \n"
			+ "   group by `group`;", nativeQuery = true)
	List<Tuple> findGenderGroupChartResult(@Param("hospitalDataId") Long hospitalDataId);

	@Query(value = "select concat(md.inspection_type) as `group`, count(u.id) as cnt\n"
			+ "from user_examination_metadata m\n" + "inner join user_data u on m.user_data_id = u.id\n"
			+ "inner join user_examination_metadata_detail md on m.id = md.user_examination_metadata_id\n"
			+ "where hospital_data_id = :hospitalDataId \n" + "group by `group`;", nativeQuery = true)
	List<Tuple> findInspectionTypeGroupChartResult(@Param("hospitalDataId") Long hospitalDataId);
}
