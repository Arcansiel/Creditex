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
        return PaymentPlan(credit.getProduct(), credit.getOriginalMainDebt(), credit.getDuration(), credit.getCreditStart(), out);
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
