package org.kofi.creditex.service;

import java.util.List;
import java.util.ArrayList;

import org.kofi.creditex.model.*;
import org.kofi.creditex.service.base.*;

/**
 * Класс, реализующий логику кредитного калькулятора
 * <p>Работа осуществляется с объектами классов модели<p/>
 * {@link Product}
 * {@link PaymentType}
 * {@link Payment}
 * {@link Application}
 * {@link Credit}
 */
public class CreditCalculator {

    //PAYMENT PLAN CALCULATION

    /**
     * Конвертирует значение {@link ProductType} в значение {@link PaymentType}
     * @param productType {@link ProductType}
     * @return {@link PaymentType}
     */
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

    /**
     * Строит план платежей по заданным параметрам кредита
     * @param percent годовая процентная ставка
     * @param productType тип погашения кредита {@link ProductType}
     * @param sum сумма кредита
     * @param duration длительность кредитования в месяцах
     * @param start дата выдачи кредита
     * @param out [0] - основной долг (=sum), [1] - проценты, [2] - общий долг (основной долг + проценты)
     * @return План платежей в виде списка {@link Payment}
     */
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

    /**
     * Строит план платежей по заданным параметрам кредита
     * @param product кредитный продукт (содержит постоянные параметры кредита) {@link Product}
     * @param sum сумма кредита
     * @param duration длительность кредитования в месяцах
     * @param start дата выдачи кредита
     * @param out [0] - основной долг (=sum), [1] - проценты, [2] - общий долг (основной долг + проценты)
     * @return План платежей в виде списка {@link Payment}
     */
    public static List<Payment> PaymentPlan(Product product, int sum, int duration, java.sql.Date start, int[] out){
        return PaymentPlan(product.getPercent(), product.getType(), sum, duration, start, out);
    }

    /**
     * Строит план платежей по заданным параметрам кредита
     * @param application заявка на кредит, содержащая все необходимые для расчёта параметры {@link Application}
     * @param start дата выдачи кредита
     * @param out [0] - основной долг (=sum), [1] - проценты, [2] - общий долг (основной долг + проценты)
     * @return План платежей в виде списка {@link Payment}
     */
    public static List<Payment> PaymentPlan(Application application, java.sql.Date start, int[] out){
        return PaymentPlan(application.getProduct(), application.getRequest(), application.getDuration(), start, out);
    }

    /**
     * Строит план платежей по заданным параметрам кредита
     * @param credit {@link Credit} содержащий все необходимые для расчёта параметры
     * @param out [0] - основной долг (=sum), [1] - проценты, [2] - общий долг (основной долг + проценты)
     * @return План платежей в виде списка {@link Payment}
     */
    public static List<Payment> PaymentPlan(Credit credit, int[] out){
        return PaymentPlan(credit.getProduct(), credit.getOriginalMainDebt(), credit.getDuration(), credit.getCreditStart(), out);
    }


    /**
     * Проверка кредитного продукта на соответствие заданным параметрам
     * @param product кредитный продукт для проверки {@link Product}
     * @param sum_required требуемая сумма кредита
     * @param duration длительность кредитования
     * @param max_payment максимально возможный месячный платёж
     * @return true - продукт подходит, false - продукт не подходит
     */
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


    /**
     * Подбор кредитных продуктов для выбора подходящих по заданным условиям
     * @param products исходный набор кредитных продуктов {@link Product}
     * @param sum_required требуемая сумма кредита
     * @param duration длительность кредитования
     * @param max_payment максимально возможный месячный платёж
     * @return Список подходящих по параметрам кредитных продуктов
     */
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
