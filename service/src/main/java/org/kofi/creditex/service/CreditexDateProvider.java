package org.kofi.creditex.service;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.LocalDate;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Data
@Slf4j
public class CreditexDateProvider implements ApplicationEventPublisherAware{
    private LocalDate currentDate = LocalDate.now();
    ApplicationEventPublisher applicationEventPublisher;

    public Date getCurrentSqlDate(){
        return new java.sql.Date(currentDate.toDate().getTime());
    }
    @Scheduled(initialDelay = 6000, fixedDelay = 60000)
    public void IncreaseDate(){
        currentDate = currentDate.plusDays(1);
        CreditexDateProvider.log.warn(currentDate.toString("dd/MM/yyyy"));
        applicationEventPublisher.publishEvent(new DateChangeEvent(this, getCurrentSqlDate()));
    }
}
