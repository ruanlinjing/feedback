package com.umasuo.report.infrastructure.repository;

import com.umasuo.report.domain.model.DeviceReport;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Davis on 17/6/15.
 */
public interface DeviceReportRepository extends JpaRepository<DeviceReport, String> {

  /**
   * Gets report by date.
   *
   * @param developerId the developer id
   * @param startDate the start date
   * @param endDate the end date
   * @return the report by date
   */
  @Query("select r from DeviceReport r where r.developerId = ?1 and r.localDate >= ?2 and r.localDate <= ?3 order by r.localDate")
  List<DeviceReport> getReportByDate(String developerId, String startDate, String endDate);
}
