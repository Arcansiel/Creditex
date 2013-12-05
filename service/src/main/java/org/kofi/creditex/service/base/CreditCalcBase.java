package org.kofi.creditex.service.base;

import java.util.*;

public class CreditCalcBase {
    public long amount;//сумма кредита
    public double interest;//ставка за месяц (не в %, а безразмерная величина)
    public int creditPeriod;//период кредитования в месяцах
    public PaymentType paymentType;//Аннуитентный, По факт. остатку, Единовременный...
    public Date creditDate;//дата выдачи кредита

    //задать годовую процентную ставку (в процентах)
    public double SetYearInterestPercents(double yearInterestPercents){
        return interest = yearInterestPercents / 1200d;// (i_year / 12) / 100
    }

    public Date setDateOnly(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return creditDate = c.getTime();
    }

    public CreditCalcBase(long amount,
                          double yearInterestPercents, int creditPeriod,
                          PaymentType paymentType, Date creditDate){
        this.amount = amount;
        SetYearInterestPercents(yearInterestPercents);
        this.creditPeriod = creditPeriod;
        this.paymentType = paymentType;
        setDateOnly(creditDate);
    }

    public CreditCalcBase(){
        setDateOnly(new Date());
    }

    Calendar GetInitialCalendar(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(creditDate);
        calendar.add(Calendar.DAY_OF_MONTH, +1);
        return calendar;
    }

    boolean SetDatesAndIncrementCalendar(PaymentInfo p, Calendar calendar, int day_of_month, boolean date_correction){
        if(date_correction){
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        }else{
            calendar.set(Calendar.DAY_OF_MONTH, day_of_month);
        }
        p.firstDate = calendar.getTime();
        calendar.add(Calendar.MONTH, +1);
        date_correction = calendar.getActualMaximum(Calendar.DAY_OF_MONTH) < day_of_month;
        if(date_correction){
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        }else{
            calendar.set(Calendar.DAY_OF_MONTH, day_of_month);
        }
        p.lastDate = calendar.getTime();
        return date_correction;
    }

    //коэффициент аннуитента
    double AnnuityFactor(){
        //double x = Math.pow(1d + interest, creditPeriod);
        //return (interest * x)/(x - 1d);
        double x1 = Math.pow(1d + interest, -creditPeriod);
        return interest / (1d - x1);
    }

    //аннуитентный платёж за месяц
    long AnnuityPayment(){
        return Math.round((double) amount * AnnuityFactor());
    }

    //размер процентов платежа при заданной сумме долга по кредиту
    long Percents(long creditDebt){
        return Math.round((double)creditDebt * (interest));
    }

    //Суммарная задолженность по аннуитентному кредиту
    long AnnuityTotalDebt(long annuityPayment){
         return annuityPayment * creditPeriod;
    }

    //Суммарная задолженность по аннуитентному кредиту
    public long AnnuityTotalDebt(){
        return AnnuityTotalDebt(AnnuityPayment());
    }

    //план аннуитентного погашения кредита
    PaymentInfo[] AnnuityPaymentPlan(){
        long payment = AnnuityPayment();//сумма одного платежа

        long initialTotalDebt = AnnuityTotalDebt(payment);//суммарная задолженность
        long initialCreditDebt = amount;//задолженность по сумме кредита
        long initialPercentsDebt = initialTotalDebt - initialCreditDebt;//задолженность по процентам

        long currentTotalDebt = initialTotalDebt;//текущая суммарная задолженность
        long currentCreditDebt = initialCreditDebt;//текущая задолженность по сумме кредита
        long currentPercentsDebt = initialPercentsDebt;//текущая задолженность по процентам

        Calendar calendar = GetInitialCalendar();
        int day_of_month = calendar.get(Calendar.DAY_OF_MONTH);
        boolean date_correction = false;

        int n = creditPeriod;
        PaymentInfo[] plan = new PaymentInfo[n];
        PaymentInfo p;
        int i;
        for(i = 0; i < (n); i++)
        {
            p = new PaymentInfo();
            p.orderNumber = i+1;

            date_correction = SetDatesAndIncrementCalendar(p, calendar, day_of_month, date_correction);

            p.totalPayment = payment;
            p.percentsPayment = Percents(currentCreditDebt);
            p.creditPayment = p.totalPayment - p.percentsPayment;

            p.totalDebt = (currentTotalDebt -= p.totalPayment);
            p.creditDebt = (currentCreditDebt -= p.creditPayment);
            p.percentsDebt = (currentPercentsDebt -= p.percentsPayment);

            p.totalPaid = initialTotalDebt - currentTotalDebt;
            p.creditPaid = initialCreditDebt - currentCreditDebt;
            p.percentsPaid = initialPercentsDebt - currentPercentsDebt;

            plan[i] = p;
        }
        //last
        i = n-1;
        p = plan[i];

        p.totalDebt = 0;
        p.creditDebt = 0;
        p.percentsDebt = 0;

        p.totalPaid = initialTotalDebt;
        p.creditPaid = initialCreditDebt;
        p.percentsPaid = initialPercentsDebt;

        return plan;
    }



    //план дифференцированного погашения кредита
    PaymentInfo[] ResiduePaymentPlan(long[] out_debt){

        long creditPayment = Math.round((double) amount / creditPeriod);

        long initialCreditDebt = amount;//задолженность по сумме кредита

        long currentCreditDebt = initialCreditDebt;//текущая задолженность по сумме кредита

        Calendar calendar = GetInitialCalendar();
        int day_of_month = calendar.get(Calendar.DAY_OF_MONTH);
        boolean date_correction = false;

        long initialPercentsDebt = 0;//задолженность по процентам

        int n = creditPeriod;
        PaymentInfo[] plan = new PaymentInfo[n];
        PaymentInfo p;
        int i;
        for(i = 0; i < (n); i++)
        {
            p = new PaymentInfo();
            p.orderNumber = i+1;

            date_correction = SetDatesAndIncrementCalendar(p, calendar, day_of_month, date_correction);

            p.creditPayment = creditPayment;
            p.percentsPayment = Percents(currentCreditDebt);
            p.totalPayment = p.creditPayment + p.percentsPayment;

            initialPercentsDebt += p.percentsPayment;

            p.creditDebt = (currentCreditDebt -= p.creditPayment);

            p.creditPaid = initialCreditDebt - currentCreditDebt;

            plan[i] = p;
        }

        long initialTotalDebt = amount + initialPercentsDebt;//суммарная задолженность

        if(out_debt != null && out_debt.length > 0){
            out_debt[0] = initialTotalDebt;
            if(out_debt.length > 1){
                out_debt[1] = initialPercentsDebt;
            }
        }

        long currentTotalDebt = initialTotalDebt;//текущая суммарная задолженность
        long currentPercentsDebt = initialPercentsDebt;//текущая задолженность по процентам

        for(i = 0; i < (n); i++)
        {
            p = plan[i];
            p.totalDebt = (currentTotalDebt -= p.totalPayment);
            p.percentsDebt = (currentPercentsDebt -= p.percentsPayment);

            p.totalPaid = initialTotalDebt - currentTotalDebt;
            p.percentsPaid = initialPercentsDebt - currentPercentsDebt;
        }

        //last
        i = n-1;
        p = plan[i];

        p.totalDebt = 0;
        p.creditDebt = 0;
        p.percentsDebt = 0;

        p.totalPaid = initialTotalDebt;
        p.creditPaid = initialCreditDebt;
        p.percentsPaid = initialPercentsDebt;

        return plan;
    }

    //Переплата по кредиту с периодической уплатой процентов
    long PercentPercentsDebt(){
        return Math.round((double)amount * interest * creditPeriod);
    }

    //Суммарная задолженность по кредиту с периодической уплатой процентов
    public long PercentTotalDebt(){
        return amount + PercentPercentsDebt();
    }

    //план единовременного погашения кредита с периодической уплатой процентов
    PaymentInfo[] PercentPaymentPlan(){
        long percentsPayment = Percents(amount);//сумма одного платежа

        long initialTotalDebt = PercentTotalDebt();//суммарная задолженность
        long initialCreditDebt = amount;//задолженность по сумме кредита
        long initialPercentsDebt = initialTotalDebt - initialCreditDebt;//задолженность по процентам

        long currentTotalDebt = initialTotalDebt;//текущая суммарная задолженность
        long currentPercentsDebt = initialPercentsDebt;//текущая задолженность по процентам

        Calendar calendar = GetInitialCalendar();
        int day_of_month = calendar.get(Calendar.DAY_OF_MONTH);
        boolean date_correction = false;

        int n = creditPeriod;
        PaymentInfo[] plan = new PaymentInfo[n];
        PaymentInfo p;
        int i;
        for(i = 0; i < (n); i++)
        {
            p = new PaymentInfo();
            p.orderNumber = i+1;

            date_correction = SetDatesAndIncrementCalendar(p, calendar, day_of_month, date_correction);

            p.totalPayment = p.percentsPayment = percentsPayment;
            p.creditPayment = 0;

            p.totalDebt = (currentTotalDebt -= p.totalPayment);
            p.percentsDebt = (currentPercentsDebt -= p.percentsPayment);
            p.creditDebt = initialCreditDebt;

            p.totalPaid = initialTotalDebt - currentTotalDebt;
            p.percentsPaid = initialPercentsDebt - currentPercentsDebt;
            p.creditPaid = 0;

            plan[i] = p;
        }
        //last
        i = n-1;
        p = plan[i];

        p.creditPayment = initialCreditDebt;
        p.totalPayment = p.creditPayment + p.percentsPayment;

        p.totalDebt = 0;
        p.creditDebt = 0;
        p.percentsDebt = 0;

        p.totalPaid = initialTotalDebt;
        p.creditPaid = initialCreditDebt;
        p.percentsPaid = initialPercentsDebt;

        return plan;
    }

    public CreditCalcResult Calculate()
    {
        CreditCalcResult r = new CreditCalcResult();
        r.creditDebt = amount;//задолженность по сумме кредита

        switch(paymentType)
        {
            case Annuity:
            {
                r.totalDebt = AnnuityTotalDebt();//суммарная задолженность
                r.paymentPlan = AnnuityPaymentPlan();
                break;
            }
            case Residue:
            {
                long[] out = new long[1];
                r.paymentPlan = ResiduePaymentPlan(out);
                r.totalDebt = out[0];//суммарная задолженность
                break;
            }
            case Percent:
            {
                r.totalDebt = PercentTotalDebt();
                r.paymentPlan = PercentPaymentPlan();
                break;
            }
        }

        r.percentsDebt = r.totalDebt - r.creditDebt;//задолженность по процентам

        return r;
    }

    //Досрочное погашение кредита
    //debt - задолженность по кредиту
    //year_interest - годовая процентная ставка (в %)
    //fine_type - тип штрафа (на сумму процентов или на процентную ставку)
    //Interest - штраф на процентную ставку, Percents - штраф на сумму процентов, None - без штрафа
    //fine_value - значение для расчёта штрафа (в %)
    //return[0] - полная сумма платежа со штрафом, return[1] - сумма процентов, return[2] - сумма штрафа
    public static long[] PriorRepayment(long debt, double year_interest, FineType fine_type, double fine_value){
        long[] result = new long[3];
        double p = (double)debt * (year_interest / 1200d);//проценты
        result[1] = Math.round(p);
        switch (fine_type){
            case Interest:{//штраф на процентную ставку
                result[2] = Math.round((double)debt * (fine_value / 1200d));
                break;
            }
            case Percents:{//штраф на сумму процентов
                result[2] = Math.round(p * (fine_value / 100d));
                break;
            }
            default:{
                //без штрафа
                result[2] = 0;
                break;
            }
        }
        result[0] = debt + result[1] + result[2];
        return result;
    }

    //пеня за просроченный платёж
    //payment - сумма просроченного платежа
    //deadline, now - if(now >= deadline -> просрочен)
    //fine_value - размер штрафа за 1 день просрочки в % от суммы платежа
    //return[0] - сумма платежа со штрафом, return[1] - сумма штрафа
    public static long[] DelayFine(long payment, Date deadline, Date now, double fine_value){
        long[] result = new long[2];
        long d = now.getTime() - deadline.getTime();
        if(d >= 0){
            long late = (d / (1000 * 3600 * 24)) + 1;
            result[1] = Math.round((double)payment * (fine_value / 100) * late);//сумма штрафа
        }else{
            result[1] = 0;
        }
        result[0] = payment + result[1];
        return result;
    }
}
