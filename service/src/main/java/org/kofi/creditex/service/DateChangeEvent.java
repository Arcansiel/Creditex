package org.kofi.creditex.service;

import lombok.Data;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.sql.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Constantine
 * Date: 09.12.13
 * Time: 19:01
 * To change this template use File | Settings | File Templates.
 */
public class DateChangeEvent extends ApplicationEvent {
    @Getter
    private final Date date;

    public DateChangeEvent(Object source,Date changedDate) {
        super(source);
        date = changedDate;
    }
}
