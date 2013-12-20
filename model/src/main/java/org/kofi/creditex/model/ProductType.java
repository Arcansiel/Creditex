package org.kofi.creditex.model;

/**
 * Тип кредитного продукта
 */
public enum ProductType {
    /**
     * Аннуитетный
     * <p>Выплата происходит <b>равными</b> платежами на протяжении всего периода.
     * Дороже, чем дифференцировнный, но дешевле, чем кредит с единовременным возвратом
     * основного долга.</p>
     */
    Annuity("Аннуитетный"),
    /**
     * Кредит с единовременным возвратом основного долга
     * <p>Во время срока кредита выплачивается <b>только процентная составляющая долга</b>.
     * Основной долг возвращается <b>единовременно при окончании срока кредита</b>.
     * Самый дорогой кредит из всех.</p>
     */
    Percent("С выплатой основного долга в конце кредита"),
    /**
     * Дифференцированный
     * <p>Сумма выплат по основному долгу <b>всегда одинакова</b>.
     * Меняется сумма по процентной составляющей.
     * Чем ближе мы к завершению кредита, тем суммарный платёж будет меньше.
     * Самый дешёвый кредит из всех.</p>
     */
    Residue("Диференцированный");
    /**
     * Текстовое представление перечисления
     */
    private final String text;

    /**
     * Конструктор, присваивающий текстовое представление перечисления
     * @param text Текстовое значение перечисления
     */
    private ProductType(final String text){
        this.text = text;
    }

    /**
     * Возврат текстового значения перечисления
     * @return Текстовое значение перечисления
     */
    @Override
    public String toString() {
        return text;
    }
}
