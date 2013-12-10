package org.kofi.creditex.service.base;

/**
 * Класс для получения результата работы кредитного калькулятора\
 * {@link CreditCalcBase}
 */
public class CreditCalcResult {
    /**
     * основной долг + проценты
     * (totalDebt = creditDebt + percentsDebt)
     */
    public long totalDebt;

    /**
     *основной долг
     */
    public long creditDebt;

    /**
     *сумма процентов по кредиту
     */
    public long percentsDebt;

    /**
     *план платежей по кредиту{@link PaymentInfo}
     */
    public PaymentInfo[] paymentPlan;

    /**
     * @return строка со значениями полей (без paymentPlan)
     */
    public String toString()
    {
        return
                "totalDebt=" + totalDebt + "; "
                +"creditDebt=" + creditDebt + "; "
                +"percentsDebt=" + percentsDebt;
    }
}
