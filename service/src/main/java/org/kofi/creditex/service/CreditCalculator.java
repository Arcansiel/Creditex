package org.kofi.creditex.service;

import java.util.List;
import java.util.ArrayList;

import org.kofi.creditex.model.*;
import org.kofi.creditex.service.base.*;

public class CreditCalculator {

    //PAYMENT PLAN CALCULATION

    private static PaymentType ToPaymentType(ProductType productType){
        PaymentType paymentType;
        if(productType.equals(ProductType.Annuity)){
            paymentType = PaymentType.Annuity;
        }else if (productType.equals(ProductType.Residue)){
            paymentType = PaymentType.Residue;
        }else{
            paymentType = PaymentType.Percent;
        }
        return paymentType;
    }

    public static List<Payment> PaymentPlan(int percent, ProductType productType, int sum, int duration, java.sql.Date start, int[] out){

        CreditCalcBase calc = new CreditCalcBase(sum,percent,duration,ToPaymentType(productType),new java.util.Date(start.getTime()));

        CreditCalcResult result = calc.Calculate();
        PaymentInfo[] plan = result.paymentPlan;
        int n = plan.length;
        List<Payment> payments = new ArrayList<Payment>(n);
        PaymentInfo p;
        for(int i = 0; i < n; i++){
            p = plan[i];
            payments.add(
                    Payment.builder().build()
                            .number(p.orderNumber)
                            .requiredPayment((int) p.totalPayment)
                            .paymentStart(new java.sql.Date(p.firstDate.getTime()))
                            .paymentEnd(new java.sql.Date(p.lastDate.getTime()))
                            .paymentClosed(false)
            );
        }

        if(out != null){
            out[0] = (int)result.creditDebt;
            out[1] = (int)result.percentsDebt;
            out[2] = (int)result.totalDebt;
        }

        return payments;
    }

    public static List<Payment> PaymentPlan(Product product, int sum, int duration, java.sql.Date start, int[] out){
        return PaymentPlan(product.percent(), product.type(), sum, duration, start, out);
    }

    public static List<Payment> PaymentPlan(Application application, java.sql.Date date, int[] out){
        return PaymentPlan(application.product(), application.request(), application.duration(), date, out);
    }

    public static List<Payment> PaymentPlan(Credit credit, int[] out){
        return PaymentPlan(credit.product(), credit.originalMainDebt(), credit.duration(), credit.start(), out);
    }


    //PRIOR REPAYMENT AND DELAY FINE CALCULATION

    private static FineType ToFineType(PriorRepayment priorRepayment){
        FineType fineType;
        if(priorRepayment.equals(PriorRepayment.AvailableFineInterest)){
            fineType = FineType.Interest;
        }else if(priorRepayment.equals(PriorRepayment.AvailableFinePercentSum)){
            fineType = FineType.Percents;
        }else{
            fineType = FineType.None;
        }
        return fineType;
    }

    public static boolean IsAvailablePriorRepayment(Credit credit){
        if(credit.product().prior().equals(PriorRepayment.NotAvailable)){
            return false;
        }else{
            float debt_limit = credit.originalMainDebt() * credit.product().priorRepaymentDebtLimit();
            return credit.currentMainDebt() <= debt_limit;
        }
    }

    //return[0] - полная сумма платежа со штрафом, return[1] - сумма процентов, return[2] - сумма штрафа
    public static int[] PriorRepaymentSum(Credit credit){
        FineType fine_type = ToFineType(credit.product().prior());
        double fine_value = credit.product().priorRepaymentPercent();
        long[] r = CreditCalcBase.PriorRepayment(credit.currentMainDebt(),credit.product().percent(),fine_type,fine_value);
        int[] out = new int[3]; out[0] = (int)r[0]; out[1] = (int)r[1]; out[2] = (int)r[2];
        return out;
    }

    //платёж с учётом пени за задержку
    //return[0] - сумма платежа со штрафом, return[1] - сумма штрафа
    public static int[] PaymentSum(Payment payment, java.sql.Date now, float fine){
        java.util.Date deadline = new java.util.Date(payment.paymentEnd().getTime());
        long[] r = CreditCalcBase.DelayFine(payment.requiredPayment(), deadline, new java.util.Date(now.getTime()), fine);
        int[] out = new int[2]; out[0] = (int)r[0]; out[1] = (int)r[1];
        return out;
    }

    //платёж с учётом пени за задержку каждого платежа
    //return[0] - сумма платежей со штрафом, return[1] - общая сумма штрафа
    public static int[] TotalPaymentSum(Iterable<Payment> payments, java.sql.Date now, float fine){
        int[] out = new int[2]; out[0] = 0; out[1] = 0;
        for(Payment p:payments){
            if(!p.paymentClosed()){
                int[] r = PaymentSum(p, now, fine);
                out[0] += r[0]; out[1] += r[1];
            }
        }
        return out;
    }




}
