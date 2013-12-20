package org.kofi.creditex.model;

/**
 * Тип операции
 */
public enum OperationType {
    /**
     * Снятие денег со счёта
     */
    Withdrawal("Снятие денег"),
    /**
     * Внесение денег на счёт
     */
    Deposit("Внесение денег");
    /**
     * Текстовое предстваление перечисления
     */
    private final String text;

    /**
     * Конструктор, присваивающий текстовое представление перечисления
     * @param text Текстовое представление перечисления
     */
    private OperationType(final String text){
        this.text = text;
    }

    /**
     * Перекрытие метода toString. Возвращает осмысленное значение перечисления
     * @return Текстовое представление перечисления
     */
    @Override
    public String toString() {
        return text;
    }
}
