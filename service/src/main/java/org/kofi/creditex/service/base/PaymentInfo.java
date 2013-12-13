package org.kofi.creditex.service.base;

import java.util.Date;

/**
 * информация об одном платеже по кредиту
 */
public class PaymentInfo {

    /**
     *порядковый номер платежа (1..)
     */
    public int orderNumber;

    /**
     *начало платежа (первый день, когда платёж можно осуществить)
     */
    public Date firstDate;

    /**
     *до этого дня можно осуществить платёж без штрафных санкций
     */
    public Date lastDate;

    /**
     *общая сумма платежа (долг+проценты)
     */
    public long totalPayment;

    /**
     *оплата по основному долгу
     */
    public long creditPayment;

    /**
     *оплата по процентам
     */
    public long percentsPayment;

    /**
     *общий долг (основной долг + проценты)(после текущего платежа)
     */
    public long totalDebt;

    /**
     *основной долг (после текущего платежа)
     */
    public long creditDebt;

    /**
     *задолженность по процентам (после текущего платежа)
     */
    public long percentsDebt;

    /**
     *всего уплачено (долг+проценты)(вместе с текущим платежом)
     */
    public long totalPaid;

    /**
     *всего уплачено по основному долгу (вместе с текущим платежом)
     */
    public long creditPaid;

    /**
     *всего уплачено процентов (вместе с текущим платежом)
     */
    public long percentsPaid;

    /**
     * строка со значениями полей объекта
     * @return строка со значениями полей объекта
     */
    public String toString()
    {
         java.text.SimpleDateFormat f = new java.text.SimpleDateFormat("EE dd MMM yyyy");
         return
                "["+(orderNumber+1)+"] "
                +"firstDate=" + f.format(firstDate) + "; "
                +"lastDate=" + f.format(lastDate) + "; "
                +"totalPayment=" + totalPayment + "; "
                +"creditPayment=" + creditPayment + "; "
                +"percentsPayment=" + percentsPayment + "; "
                +"totalDebt=" + totalDebt + "; "
                +"creditDebt=" + creditDebt + "; "
                +"percentsDebt=" + percentsDebt + "; "
                +"totalPaid=" + totalPaid + "; "
                +"creditPaid=" + creditPaid + "; "
                +"percentsPaid=" + percentsPaid;
    }
}
