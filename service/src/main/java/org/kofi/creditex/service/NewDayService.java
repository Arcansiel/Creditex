package org.kofi.creditex.service;

import org.springframework.context.ApplicationListener;

/**
 * Created with IntelliJ IDEA.
 * User: Constantine
 * Date: 09.12.13
 * Time: 20:15
 * To change this template use File | Settings | File Templates.
 */
public interface NewDayService extends ApplicationListener<DateChangeEvent> {
    void MarkExpired();

    void AddFine();

    @Override
    void onApplicationEvent(DateChangeEvent dateChangeEvent);
}
