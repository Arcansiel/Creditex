package org.kofi.creditex.model;

/**
 * Тип уведомления
 */
public enum NotificationType {
    /**
     * Кредит не возвращён
     */
    Unreturned("Невозвращённый кредит"),
    /**
     * Просрочен один ил более платежей
     */
    Expired("Просроченный платёж");

    /**
     * Текстовое представление
     */
    private final String text;

    /**
     * Конструктор для установки значения
     * @param text Текстовое значение
     */
    private NotificationType(final String text){
        this.text=text;
    }

    /**
     * Возвращение текстового представления
     * @return Текстовое представление
     */
    @Override
    public String toString() {
        return text;
    }
}
