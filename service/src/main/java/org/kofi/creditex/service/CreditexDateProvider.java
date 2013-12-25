package org.kofi.creditex.service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import javax.annotation.PostConstruct;
import java.sql.Date;

@Data
@Slf4j
@Service
@DependsOn("dayReportService")
public class CreditexDateProvider implements ApplicationEventPublisherAware{
    @Autowired
    private DayReportService dayReportService;
    private LocalDate currentDate = LocalDate.now();
    ApplicationEventPublisher applicationEventPublisher;

    public Date getCurrentSqlDate(){
        return new java.sql.Date(currentDate.toDate().getTime());
    }
    public void IncreaseDate(){
        currentDate = currentDate.plusDays(1);
        CreditexDateProvider.log.warn(currentDate.toString("dd/MM/yyyy"));
        applicationEventPublisher.publishEvent(new DateChangeEvent(this, getCurrentSqlDate()));
    }

    public Date transformDate(LocalDate date){
        return new java.sql.Date(date.toDate().getTime());
    }

    @PostConstruct
    private void Initialization(){
        currentDate = dayReportService.FindLatestReport().getDayDate().plusDays(1);
    }


}
