package org.kofi.creditex.creditcalc.base;

public class CreditCalcResult {
    public long totalDebt;//суммарная задолженность
    public long creditDebt;//задолженность по сумме кредита
    public long percentsDebt;//задолженность по процентам
    public PaymentInfo[] paymentPlan;//план погашения кредита

    public String toString()
    {
        return
                "totalDebt=" + totalDebt + "; "
                +"creditDebt=" + creditDebt + "; "
                +"percentsDebt=" + percentsDebt;
    }
}
