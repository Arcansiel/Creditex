package org.kofi.creditex.creditcalc.base;

import java.util.Date;

//информация одного платежа по кредиту
public class PaymentInfo {
    public int orderNumber;//порядковый номер платежа

    public Date firstDate;//начало платежа (первый день, когда платёж можно осуществить)
    public Date lastDate;//до этого дня можно осуществить платёж без штрафных санкций

    public long totalPayment;//общая сумма платежа
    public long creditPayment;//оплата по кредиту
    public long percentsPayment;//оплата процентов

    public long totalDebt;//общий долг (после текущего платежа)
    public long creditDebt;//долг по кредиту (после текущего платежа)
    public long percentsDebt;//долг по процентам (после текущего платежа)

    public long totalPaid;//всего уплачено (вместе с текущим платежом)
    public long creditPaid;//всего уплачено по кредиту (вместе с текущим платежом)
    public long percentsPaid;//всего уплачено процентов (вместе с текущим платежом)


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
