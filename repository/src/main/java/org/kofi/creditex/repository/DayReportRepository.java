package org.kofi.creditex.repository;

import org.kofi.creditex.model.DayReport;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import java.util.List;


public interface DayReportRepository extends JpaRepository<DayReport, Long>, QueryDslPredicateExecutor<DayReport> {
}
