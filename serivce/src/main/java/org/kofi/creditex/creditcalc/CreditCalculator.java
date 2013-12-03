package org.kofi.creditex.creditcalc;

import java.util.*;
import org.kofi.creditex.model.*;
import org.kofi.creditex.creditcalc.base.*;

public class CreditCalculator {

    //PAYMENT PLAN CALCULATION

    public static PaymentType ToPaymentType(String productType){
        PaymentType paymentType;
        String t = productType.toLowerCase();
        if(t.equals("annuity")){
            paymentType = PaymentType.Annuity;
        }else if (t.equals("residue")){
            paymentType = PaymentType.Residue;
        }else{
            paymentType = PaymentType.Percent;
        }
        return paymentType;
    }

    public static List<Payment> PaymentPlan(int percent, String productType, int sum, int duration, Date start, PaymentPlanSummary out){

        CreditCalcBase calc = new CreditCalcBase(sum,percent,duration,ToPaymentType(productType),start);

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
                        .sum((int)p.totalPayment)
                        .start(new java.sql.Date(p.firstDate.getTime()))
                        .end(new java.sql.Date(p.lastDate.getTime()))
                        .closed(false)
            );
        }

        if(out != null){
            out.total = (int)result.totalDebt;
            out.credit = (int)result.creditDebt;
            out.percents = (int)result.percentsDebt;
        }

        return payments;
    }

    public static List<Payment> PaymentPlan(Product product, int sum, int duration, Date start, PaymentPlanSummary out){
        return PaymentPlan(product.percent(), product.type().value(), sum, duration, start, out);
    }

    public static List<Payment> PaymentPlan(Application application, Date date, PaymentPlanSummary out){
         return PaymentPlan(application.product(), application.request(), application.duration(), date, out);
    }

    public static List<Payment> PaymentPlan(Credit credit, PaymentPlanSummary out){
        return PaymentPlan(credit.product(), credit.sum(), credit.duration(), credit.start(), out);
    }


    //PRIOR REPAYMENT AND FINE CALCULATION
    /*
    public static boolean IsAvailablePriorRepayment(Credit credit, Date now){
        //TODO check values of credit.product().prior()
        String prior = credit.product().prior().value().toLowerCase();


        return false;
    }


    public static void PriorRepaymentAmount(Credit credit){
        //List<Payment> payments = credit.payments();

        FineType fine_type;
        double fine_value;

        CreditCalcBase.PriorRepayment(credit.debt(),credit.product().percent(),fine_type,fine_value);

    }

    public static void PaymentFine(Payment payment, Date now){

    }


    public static void TotalPaymentFine(Credit credit, Date now){

    }
    */



}
