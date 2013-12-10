package org.kofi.creditex.service;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.kofi.creditex.model.Credit;
import org.kofi.creditex.model.Payment;
import org.kofi.creditex.model.QCredit;
import org.kofi.creditex.model.QPayment;
import org.kofi.creditex.repository.CreditRepository;
import org.kofi.creditex.repository.PaymentRepository;
import org.kofi.creditex.web.model.CreditForm;
import org.kofi.creditex.web.model.PaymentTableForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nullable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Сервис для работы с кредитами
 */
@Service
@Transactional
public class CreditServiceImpl implements CreditService{
    /**
     * Конвертер даты в текстовую строку
     */
    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    /**
     * Преобразование Boolean в текстовую строку
     */
    Function<Boolean,String> booleanTransform = new Function<Boolean, String>() {
        @Nullable
        @Override
        public String apply(@Nullable Boolean v) {
            assert v!=null;
            return v?"Да":"Нет";
        }
    };
    /**
     * Преобразование платежа в форму платежа
     */
    Function<Payment,PaymentTableForm> paymentTransform = new Function<Payment, PaymentTableForm>() {
        @Nullable
        @Override
        public PaymentTableForm apply(@Nullable Payment payment) {
            assert payment != null;
            return new PaymentTableForm()
                    .setId(payment.getId())
                    .setNumber(payment.getNumber())
                    .setPayment(payment.getRequiredPayment())
                    .setStart(df.format(payment.getPaymentStart()))
                    .setEnd(df.format(payment.getPaymentEnd()))
                    .setClosed(booleanTransform.apply(payment.isPaymentClosed()))
                    .setExpired(booleanTransform.apply(payment.isPaymentExpired()));
        }
    };
    /**
     * Функция преобразования списка платежей
     */
    Function<List<Payment>, List<PaymentTableForm>> paymentListTransform = new Function<List<Payment>, List<PaymentTableForm>>() {
        @Nullable
        @Override
        public List<PaymentTableForm> apply(@Nullable List<Payment> payments) {
            List<PaymentTableForm> result = new ArrayList<PaymentTableForm>();
            assert payments != null;
            for(Payment p:payments){
                result.add(paymentTransform.apply(p));
            }
            return result;  //To change body of implemented methods use File | Settings | File Templates.
        }
    };
    /**
     * Функция преобразования кредита в форму кредита
     */
    Function<Credit, CreditForm> creditTransform = new Function<Credit, CreditForm>() {
        @Nullable
        @Override
        public CreditForm apply(@Nullable Credit credit) {
            assert credit != null;
            return new CreditForm()
                    .setId(credit.getId())
                    .setStart(df.format(credit.getCreditStart()))
                    .setDuration(credit.getDuration())
                    .setCurrentMainDebt(credit.getCurrentMainDebt())
                    .setFine(credit.getMainFine())
                    .setCurrentMoney(credit.getCurrentMoney())
                    .setOriginalMainDebt(credit.getOriginalMainDebt())
                    .setProductName(credit.getProduct().getName())
                    .setProductId(credit.getProduct().getId())
                    .setPayments(paymentListTransform.apply(credit.getPayments()));
        }
    };
    @Autowired
    private CreditRepository creditRepository;

    /**
     * Получить кредит по ID
     * @param id Значение Id требуемого кредита
     * @return Кредит
     */
    @Override
    public Credit GetCreditById(int id) {
        return creditRepository.findOne(id);
    }

    /**
     * Получить форму кредита по ID
     * @param id Значение Id требуемого кредита
     * @return Форма кредита
     */
    @Override
    public CreditForm GetCreditFormById(int id) {
        return creditTransform.apply(GetCreditById(id));
    }
}
