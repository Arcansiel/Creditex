package org.kofi.creditex.service;
import org.kofi.creditex.model.*;

import java.sql.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import org.kofi.creditex.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CreditRepository creditRepository;

    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    PriorRepaymentApplicationRepository priorRepository;

    @Autowired
    ProlongationApplicationRepository prolongationRepository;

    @Autowired
    CreditexDateProvider creditexDateProvider;

    @Autowired
    AuthoritiesRepository authoritiesRepository;

    @Override
    public List<Map<String,Object>> GetAllProductsStatistics(long[] out) {
        List<Map<String,Object>> statistics = new ArrayList<Map<String,Object>>((int)productRepository.count());
        Map<String,Object> item;
        long active = 0, inactive = 0;
        for(Product p:productRepository.findAll()){
            item = new HashMap<String,Object>();
            item.put("product",p);
            if(p.isActive()){
                active++;
            }else{
                inactive++;
            }
            statistics.add(item);
        }
        Product product;
        long countActiveCredits, countClosedCredits;
        long countApplications, countRejectedApplications, countAbortedApplications, countInProcessApplications;
        for(Map<String,Object> map:statistics){
            product = (Product)map.get("product");
            countActiveCredits = creditRepository.count(
                    QCredit.credit.product.eq(product).and(QCredit.credit.running.isTrue())
            );
            countClosedCredits = creditRepository.count(
                    QCredit.credit.product.eq(product).and(QCredit.credit.running.isFalse())
            );
            map.put("countCredits",countActiveCredits + countClosedCredits);
            map.put("countActiveCredits",countActiveCredits);
            map.put("countClosedCredits",countClosedCredits);
            countRejectedApplications = applicationRepository.count(
                    QApplication.application.product.eq(product)
                            .and(QApplication.application.acceptance.eq(Acceptance.Rejected))
            );
            countAbortedApplications = applicationRepository.count(
                    QApplication.application.product.eq(product)
                            .and(QApplication.application.acceptance.eq(Acceptance.Aborted))
            );
            countInProcessApplications = applicationRepository.count(
                    QApplication.application.product.eq(product)
                            .and(QApplication.application.acceptance.eq(Acceptance.InProcess))
            );
            countApplications = applicationRepository.count(
                    QApplication.application.product.eq(product)
            );
            map.put("countApplications",countApplications);
            map.put("countRejectedApplications",countRejectedApplications);
            map.put("countAbortedApplications",countAbortedApplications);
            map.put("countInProcessApplications",countInProcessApplications);
        }
        if(out != null && out.length > 0){
            out[0] = active + inactive;
            if(out.length > 1){
                out[1] = active;
                if(out.length > 2){
                    out[2] = inactive;
                }
            }
        }
        return statistics;
    }

    @Override
    public Map<String,Object> GetCreditsStatistics() {
        Map<String,Object> map = new HashMap<String,Object>(16);
        long countAll, countActive;
        map.put("countAll",countAll = creditRepository.count());
        map.put("countActive",countActive = creditRepository.count(QCredit.credit.running.isTrue()));
        map.put("countInActive", countAll - countActive);
        map.put("countExpired", creditRepository.count(
            QCredit.credit.mainFine.gt(0)
        ));
        Date now = creditexDateProvider.getCurrentSqlDate();
        map.put("countUnreturned", creditRepository.count(
                QCredit.credit.currentMainDebt.gt(0)
                .and(QCredit.credit.creditEnd.lt(now))
        ));
        return map;
    }

    @Override
    public Map<String,Object> GetClientsStatistics() {
        Authority clientAuthority = authoritiesRepository.findByAuthority("ROLE_CLIENT");
        Map<String,Object> map = new HashMap<String,Object>(16);
        long countAll, countEnabled;
        map.put("countAll",countAll = userRepository.count(QUser.user.authority.eq(clientAuthority)));
        map.put("countEnabled",countEnabled = userRepository.count(
                QUser.user.authority.eq(clientAuthority).and(QUser.user.enabled.isTrue()))
        );
        map.put("countDisabled", countAll - countEnabled);
        long countDebtors;
        map.put("countDebtors",
                countDebtors = userRepository.count(
                        QUser.user.authority.eq(clientAuthority)
                                .and(QUser.user.credits.any().running.isTrue())
                ));
        map.put("countFree", countAll - countDebtors);
        map.put("countPriors", userRepository.count(
                QUser.user.priorApplicationsTo.isNotEmpty()
        ));
        map.put("countPriorsAccepted", userRepository.count(
                QUser.user.priorApplicationsTo.any().acceptance.eq(Acceptance.Accepted)
        ));
        map.put("countProlongations", userRepository.count(
                QUser.user.prolongationApplicationsTo.isNotEmpty()
        ));
        map.put("countProlongationsAccepted", userRepository.count(
                QUser.user.prolongationApplicationsTo.any().acceptance.eq(Acceptance.Accepted)
        ));
        return map;
    }

    @Override
    public Map<String,Object> GetApplicationsStatistics() {
        Map<String,Object> map = new HashMap<String,Object>(16);
        map.put("countAll", applicationRepository.count());
        map.put("countAccepted", applicationRepository.count(QApplication.application.acceptance.eq(Acceptance.Accepted)));
        map.put("countRejected", applicationRepository.count(QApplication.application.acceptance.eq(Acceptance.Rejected)));
        map.put("countAborted", applicationRepository.count(QApplication.application.acceptance.eq(Acceptance.Aborted)));
        map.put("countInProcess", applicationRepository.count(QApplication.application.acceptance.eq(Acceptance.InProcess)));
        return map;
    }

    @Override
    public Map<String, Object> GetPriorsStatistics() {
        Map<String,Object> map = new HashMap<String,Object>(16);
        map.put("countAll", priorRepository.count());
        map.put("countAccepted", priorRepository.count(QPriorRepaymentApplication.priorRepaymentApplication.acceptance.eq(Acceptance.Accepted)));
        map.put("countRejected", priorRepository.count(QPriorRepaymentApplication.priorRepaymentApplication.acceptance.eq(Acceptance.Rejected)));
        map.put("countAborted", priorRepository.count(QPriorRepaymentApplication.priorRepaymentApplication.acceptance.eq(Acceptance.Aborted)));
        map.put("countInProcess", priorRepository.count(QPriorRepaymentApplication.priorRepaymentApplication.acceptance.eq(Acceptance.InProcess)));
        return map;
    }

    @Override
    public Map<String, Object> GetProlongationsStatistics() {
        Map<String,Object> map = new HashMap<String,Object>(16);
        map.put("countAll", prolongationRepository.count());
        map.put("countAccepted", prolongationRepository.count(QProlongationApplication.prolongationApplication.acceptance.eq(Acceptance.Accepted)));
        map.put("countRejected", prolongationRepository.count(QProlongationApplication.prolongationApplication.acceptance.eq(Acceptance.Rejected)));
        map.put("countAborted", prolongationRepository.count(QProlongationApplication.prolongationApplication.acceptance.eq(Acceptance.Aborted)));
        map.put("countInProcess", prolongationRepository.count(QProlongationApplication.prolongationApplication.acceptance.eq(Acceptance.InProcess)));
        return map;
    }

    @Override
    public Map<String,Object> GetPaymentsStatistics() {
        Map<String,Object> map = new HashMap<String,Object>(16);
        map.put("countAll", paymentRepository.count());
        map.put("countClosedNotExpired", paymentRepository.count(QPayment.payment.paymentClosed.isTrue()
                .and(QPayment.payment.paymentExpired.isFalse())));
        map.put("countClosedExpired", paymentRepository.count(QPayment.payment.paymentClosed.isTrue()
                .and(QPayment.payment.paymentExpired.isTrue())));
        map.put("countNotClosedNotExpired", paymentRepository.count(QPayment.payment.paymentClosed.isFalse()
                .and(QPayment.payment.paymentExpired.isFalse())));
        map.put("countNotClosedExpired", paymentRepository.count(QPayment.payment.paymentClosed.isFalse()
                .and(QPayment.payment.paymentExpired.isTrue())));
        return map;
    }
}
