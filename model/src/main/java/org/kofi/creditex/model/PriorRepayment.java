package org.kofi.creditex.model;

/**
 * Возможно ли досрочное погашение кредита
 */
public enum PriorRepayment {
    /**
     * Невозможно
     */
    NotAvailable("Недоступно"),
    /**
     * Возможно без штрафа
     */
    Available("Доступно"),
    /**
     * Возможно с увеличением процентной ставки в качестве штрафа
     */
    AvailableFineInterest("Доступно со штрафом на процентную ставку"),
    /**
     * Возможно со штрафом на оставшиеся проценты по кредиту
     */
    AvailableFinePercentSum("Доступно со штрафом на оставшиеся проценты");
    /**
     * Текстовое представление перечисления
     */
    private final String text;

    /**
     * Конструктор, присваивающий текстовое значение перечисления
     * @param text Текстовое значение перечисления
     */
    private PriorRepayment(final String text){
        this.text = text;
    }

    /**
     * Возвращает текстовое значение перечисления
     * @return Текстовое значение перечсления
     */
    @Override
    public String toString() {
        return text;
    }
}
