package org.kofi.creditex.model;

/**
 * Принятие заявки
 */
public enum Acceptance {
    /**
     * Заявка принята
     */
    Accepted("Принята"),
    /**
     * Заявка отклонена
     */
    Rejected("Отклонена"),
    /**
     * Заявка в рассмотрении
     */
    InProcess("В рассмотрении"),
    /**
     * Заявка отменена клиентом
     */
    Aborted("Отменена");

    /**
     * Конструктор, присваивающий значение {@link #text}
     * @param text Текстовое значение переменной Enum
     */
    private Acceptance(final String text){
        this.text=text;
    }

    /**
     * Текстовое отображение переменной Enum
     */
    private final String text;

    /**
     * Перекрытие метода toString для отображения перечисления в шаблонах
     * @return Строка для отображения
     */
    @Override
    public String toString() {
        return text;
    }
}
