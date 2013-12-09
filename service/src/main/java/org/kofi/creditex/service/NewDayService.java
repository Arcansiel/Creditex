package org.kofi.creditex.service;

import org.springframework.context.ApplicationListener;

public interface NewDayService extends ApplicationListener<DateChangeEvent> {
    void MarkExpired();

    void AddMainFine();

    void AddPercentFine();

    @Override
    void onApplicationEvent(DateChangeEvent dateChangeEvent);
}
