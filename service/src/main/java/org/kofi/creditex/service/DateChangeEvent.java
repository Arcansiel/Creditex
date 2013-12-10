package org.kofi.creditex.service;

import lombok.Data;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.sql.Date;

public class DateChangeEvent extends ApplicationEvent {
    /**
     * Текущая дата (на момент изменения)
     */
    @Getter
    private final Date date;

    /**
     * Установка изменённой даты
     * @param source объект, вызвавший событие
     * @param changedDate изменённая дата
     */
    public DateChangeEvent(Object source,Date changedDate) {
        super(source);
        date = changedDate;
    }
}
