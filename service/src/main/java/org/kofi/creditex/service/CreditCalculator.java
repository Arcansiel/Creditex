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
            Payment payment = new Payment();
            payment.setNumber(p.orderNumber);
            payment.setRequiredPayment((int) p.totalPayment);
            payment.setPercents((int) p.percentsPayment);
            payment.setPaymentStart(new java.sql.Date(p.firstDate.getTime()));
            payment.setPaymentEnd(new java.sql.Date(p.lastDate.getTime()));
            payment.setPaymentClosed(false);
            payments.add(payment);
        }

        if(out != null){
            out[0] = (int)result.creditDebt;
            out[1] = (int)result.percentsDebt;
            out[2] = (int)result.totalDebt;
        }

        return payments;
    }

    public static List<Payment> PaymentPlan(Product product, int sum, int duration, java.sql.Date start, int[] out){
        return PaymentPlan(product.getPercent(), product.getType(), sum, duration, start, out);
    }

    public static List<Payment> PaymentPlan(Application application, java.sql.Date date, int[] out){
        return PaymentPlan(application.getProduct(), application.getRequest(), application.getDuration(), date, out);
    }

    public static List<Payment> PaymentPlan(Credit credit, int[] out){
        return PaymentPlan(credit.getProduct(), credit.getOriginalMainDebt(), credit.getDuration(), credit.getStart(), out);
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
        if(credit.getProduct().getPrior().equals(PriorRepayment.NotAvailable)){
            return false;
        }else{
            float debt_limit = credit.getOriginalMainDebt() * credit.getProduct().getPriorRepaymentDebtLimit();
            return credit.getCurrentMainDebt() <= debt_limit;
        }
    }

    //return[0] - полная сумма платежа со штрафом, return[1] - сумма процентов, return[2] - сумма штрафа
    public static int[] PriorRepaymentSum(Credit credit){
        FineType fine_type = ToFineType(credit.getProduct().getPrior());
        double fine_value = credit.getProduct().getPriorRepaymentPercent();
        long[] r = CreditCalcBase.PriorRepayment(credit.getCurrentMainDebt(),credit.getProduct().getPercent(),fine_type,fine_value);
        int[] out = new int[3]; out[0] = (int)r[0]; out[1] = (int)r[1]; out[2] = (int)r[2];
        return out;
    }

    //платёж с учётом пени за задержку
    //return[0] - сумма платежа со штрафом, return[1] - сумма штрафа
    public static int[] PaymentSum(Payment payment, java.sql.Date now, float fine){
        java.util.Date deadline = new java.util.Date(payment.getPaymentEnd().getTime());
        long[] r = CreditCalcBase.DelayFine(payment.getRequiredPayment(), deadline, new java.util.Date(now.getTime()), fine);
        int[] out = new int[2]; out[0] = (int)r[0]; out[1] = (int)r[1];
        return out;
    }

    //платёж с учётом пени за задержку каждого платежа
    //return[0] - сумма платежей со штрафом, return[1] - общая сумма штрафа
    public static int[] TotalPaymentSum(Iterable<Payment> payments, java.sql.Date now, float fine){
        int[] out = new int[2]; out[0] = 0; out[1] = 0;
        for(Payment p:payments){
            if(!p.isPaymentClosed()){
                int[] r = PaymentSum(p, now, fine);
                out[0] += r[0]; out[1] += r[1];
            }
        }
        return out;
    }


    private static boolean ValidRequiredProduct(Product product,
                                          int sum_required, int duration, int max_payment){
        if(sum_required < product.getMinMoney()
                || sum_required > product.getMaxMoney()){
            return false;
        }

        if(duration < product.getMinDuration()
                || duration > product.getMaxDuration()){
            return false;
        }

        CreditCalcBase calc = new CreditCalcBase(
                sum_required,
                product.getPercent(),
                duration,
                ToPaymentType(product.getType()),
                new java.util.Date());

        long max_p = calc.MaxPayment();
        if(max_payment < max_p){
            return false;
        }

        return true;
    }

    public static List<Product> RequireProducts(Iterable<Product> products,
                                                int sum_required, int duration, int max_payment){
        List<Product> required = new ArrayList<Product>();
        for(Product p:products){
            if(ValidRequiredProduct(p, sum_required, duration, max_payment)){
                required.add(p);
            }
        }
        return required;
    }

}
