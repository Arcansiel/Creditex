package org.kofi.creditex.service.base;

import java.util.*;

/**
 * Класс, реализующий логику расчётов кредитного калькулятора
 * <p>Применяется для построения планов платежей по кредитам</p>
 */
public class CreditCalcBase {
    /**
     *Сумма кредита
     */
    public long amount;

    /**
     *Ставка кредитования за 1 месяц (от 0 до 1 : 0 = 0% , 1 = 100%)
     */
    public double interest;

    /**
     *Срок кредитования в месяцах
     */
    public int creditPeriod;

    /**
     *Тип погашения кредита  {@link PaymentType}
     */
    public PaymentType paymentType;

    /**
     *Дата выдачи кредита
     */
    public Date creditDate;

    /**
     * Установить годовую процентную ставку
     * @param yearInterestPercents Годовая процентная ставка
     * @return Значение месячной ставки (0..1)
     */
    public double SetYearInterestPercents(double yearInterestPercents){
        return interest = yearInterestPercents / 1200d;// (i_year / 12) / 100
    }

    /**
     * Установить поле creditDate с обнулением часов, минут, секунд, миллисекунд
     * @param date Дата для установки
     * @return creditDate
     */
    private Date setDateOnly(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return creditDate = c.getTime();
    }

    /**
     * Параметризованный конструктор класса
     * @param amount Сумма кредита
     * @param yearInterestPercents Годовая процентная ставка
     * @param creditPeriod Срок кредитования в месяцах
     * @param paymentType Тип погашения кредита {@link PaymentType}
     * @param creditDate Дата выдачи кредита
     */
    public CreditCalcBase(long amount,
                          double yearInterestPercents, int creditPeriod,
                          PaymentType paymentType, Date creditDate){
        this.amount = amount;
        SetYearInterestPercents(yearInterestPercents);
        this.creditPeriod = creditPeriod;
        this.paymentType = paymentType;
        setDateOnly(creditDate);
    }

    /**
     * Конструктор без параметров
     */
    public CreditCalcBase(){
        setDateOnly(new Date());
    }

    /**
     * Создать объект класса Calendar на основе значения поля creditDate {@link Calendar}
     * @return Объект класса Calendar {@link Calendar}
     *
     */
    private Calendar GetInitialCalendar(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(creditDate);
        calendar.add(Calendar.DAY_OF_MONTH, +1);
        return calendar;
    }

    /**
     * Установка дат платежа
     * @param p платёж для установки дат
     * @param calendar календарь для получения дат
     * @param day_of_month оригинальный день месяца
     * @param date_correction установить последний день месяца
     * @return date_correction следующей итерации
     */
    private boolean SetDatesAndIncrementCalendar(PaymentInfo p, Calendar calendar, int day_of_month, boolean date_correction){
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

    /**
     * Коэффициент аннуитета
     * @return Коэффициент аннуитета
     */
    public double AnnuityFactor(){
        //double x = Math.pow(1d + interest, creditPeriod);
        //return (interest * x)/(x - 1d);
        double x1 = Math.pow(1d + interest, -creditPeriod);
        return interest / (1d - x1);
    }

    /**
     * аннуитетный платёж за месяц
     * @return аннуитетный платёж за месяц
     */
    public long AnnuityPayment(){
        return Math.round((double) amount * AnnuityFactor());
    }

    /**
     * размер процентов платежа при заданной сумме долга по кредиту
     * @param creditDebt текущий основной долг
     * @return размер процентов
     */
    private long Percents(long creditDebt){
        return Math.round((double)creditDebt * (interest));
    }

    /**
     * Суммарная задолженность по аннуитетному кредиту
     * @param annuityPayment размер аннуитетного платежа
     * @return Суммарная задолженность по аннуитетному кредиту
     */
    private long AnnuityTotalDebt(long annuityPayment){
         return annuityPayment * creditPeriod;
    }

    /**
     * Суммарная задолженность по аннуитентному кредиту
     * @return Суммарная задолженность по аннуитентному кредиту
     */
    public long AnnuityTotalDebt(){
        return AnnuityTotalDebt(AnnuityPayment());
    }

    /**
     * план аннуитетного погашения кредита {@link PaymentType}
     * @return план аннуитетного погашения кредита в виде массива платежей {@link PaymentInfo}
     */
    private PaymentInfo[] AnnuityPaymentPlan(){
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
        long sumd = 0, sump = 0;
        for(i = 0; i < (n); i++)
        {
            p = new PaymentInfo();
            p.orderNumber = i+1;

            date_correction = SetDatesAndIncrementCalendar(p, calendar, day_of_month, date_correction);

            p.totalPayment = payment;
            p.percentsPayment = Percents(currentCreditDebt);
            p.creditPayment = p.totalPayment - p.percentsPayment;

            sumd += p.creditPayment;
            sump += p.percentsPayment;

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
        p.creditPayment = p.creditPayment + (initialCreditDebt - sumd);//main debt correction
        p.percentsPayment = p.percentsPayment + (initialPercentsDebt - sump);//percents correction
        p.totalPayment = p.creditPayment + p.percentsPayment;//total correction

        p.totalDebt = 0;
        p.creditDebt = 0;
        p.percentsDebt = 0;

        p.totalPaid = initialTotalDebt;
        p.creditPaid = initialCreditDebt;
        p.percentsPaid = initialPercentsDebt;

        return plan;
    }


    /**
     * план аннуитетного погашения кредита {@link PaymentType}
     * @param out_debt out_debt[0] = долг+проценты, out_debt[1] = проценты
     * @return план аннуитетного погашения кредита в виде массива платежей {@link PaymentInfo}
     */
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

        p.creditPayment = p.creditPayment + (amount - (creditPayment * (long)n));//main debt correction
        p.totalPayment = p.creditPayment + p.percentsPayment;//total correction

        p.totalDebt = 0;
        p.creditDebt = 0;
        p.percentsDebt = 0;

        p.totalPaid = initialTotalDebt;
        p.creditPaid = initialCreditDebt;
        p.percentsPaid = initialPercentsDebt;

        return plan;
    }

    /**
     * Переплата по кредиту с периодической уплатой процентов
     * @return проценты по кредиту с периодической уплатой процентов
     */
    public long PercentPercentsDebt(){
        return Math.round((double)amount * interest * creditPeriod);
    }

    /**
     * Суммарная задолженность по кредиту с периодической уплатой процентов
     * @return Суммарная задолженность по кредиту с периодической уплатой процентов
     */
    public long PercentTotalDebt(){
        return amount + PercentPercentsDebt();
    }

    /**
     * план единовременного погашения кредита с периодической уплатой процентов {@link PaymentType}
     * @return план единовременного погашения кредита с периодической уплатой процентов в виде массива платежей {@link PaymentInfo}
     */
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
        long sump = 0;
        for(i = 0; i < (n); i++)
        {
            p = new PaymentInfo();
            p.orderNumber = i+1;

            date_correction = SetDatesAndIncrementCalendar(p, calendar, day_of_month, date_correction);

            sump += percentsPayment;
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
        p.percentsPayment = percentsPayment + (initialPercentsDebt - sump);//percents correction
        p.totalPayment = p.creditPayment + p.percentsPayment;//total correction

        p.totalDebt = 0;
        p.creditDebt = 0;
        p.percentsDebt = 0;

        p.totalPaid = initialTotalDebt;
        p.creditPaid = initialCreditDebt;
        p.percentsPaid = initialPercentsDebt;

        return plan;
    }

    /**
     * Построить план платежей и расчитать сумму проценты
     * @return план платежей и размер процентов {@link CreditCalcResult}
     */
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

    /**
     * максимальный платёж за месяц по данному кредиту
     * @return максимальный платёж за месяц по данному кредиту
     */
    public long MaxPayment(){
        switch (paymentType){
            case Annuity:{
                return AnnuityPayment();
            }
            case Residue:{
                return (amount / creditPeriod) + Percents(amount);
            }
            default:{
                return Percents(amount);
            }
        }
    }
}
