package org.kofi.creditex.service;

import org.springframework.context.ApplicationListener;

public interface NewDayService extends ApplicationListener<DateChangeEvent> {
    void MarkExpired();
    void AddMainFine();
    void AddPercentFine();
    void MarkUnreturned();
    @Override
    void onApplicationEvent(DateChangeEvent dateChangeEvent);

    void PayBill();

    void PayFine();
}
