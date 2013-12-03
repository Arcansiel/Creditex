
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kofi.creditex.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;
import org.kofi.creditex.creditcalc.*;

@ContextConfiguration(locations = "classpath:service-test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class CreditCalculatorTest {
    Logger logger = LoggerFactory.getLogger(CreditCalculatorTest.class);

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void SimpleTest(){
        ProductType type = ProductType.builder().build().value("annuity");
        Product product = Product.builder().build()
                .minMoney(500000)
                .maxMoney(2000000)
                .minCommittee(Integer.MAX_VALUE)
                .name("credit1")
                .percent(50)
                .debtPercent(1)
                .priorRepaymentPercent(10)
                .type(type);
        Application application = Application.builder().build()
                .duration(24)
                .product(product)
                .request(1000000);
        Credit credit = Credit.builder().build()
                .sum(application.request())//сумма кредита
                .debt(application.request())//основной долг (сколько осталось заплатить)
                .duration(application.duration())
                .money(application.request())//деньги (осталось от счёта)
                .percentSum(0)//долг по процентам в сумме (сколько осталось процентов)
                .product(product)
                .start(new java.sql.Date(new java.util.Date().getTime()));

        PaymentPlanSummary out = new PaymentPlanSummary();
        List<Payment> plan = CreditCalculator.PaymentPlan(credit,out);
        credit.percentSum(out.percents);

        /**
        System.out.println(String.format("%d = %d + %d",out.total,out.credit,out.percents));
        for(Payment p:plan){
            System.out.println(p.toString());
        }
        /**/
    }
}
