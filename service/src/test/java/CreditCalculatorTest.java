import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kofi.creditex.Dates;
import org.kofi.creditex.service.CreditCalculator;
import org.kofi.creditex.service.UserService;
import org.kofi.creditex.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import org.kofi.creditex.model.*;

import java.util.List;

@ContextConfiguration(locations = "classpath:service-test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional(propagation = Propagation.REQUIRED)
public class CreditCalculatorTest {

    private Product product;
    private Application application;
    private Credit credit;

    @Before
    public void setUp() throws Exception {
        product = Product.builder().build()
                .minMoney(500000)
                .maxMoney(2000000)
                .minCommittee(Integer.MAX_VALUE)
                .name("product1")
                .percent(50)
                .debtPercent(1)
                .prior(PriorRepayment.AvailableFineInterest)
                .priorRepaymentPercent(10)
                .priorRepaymentDebtLimit(0.5f)
                .type(ProductType.Annuity);
        application = Application.builder().build()
                .duration(24)
                .product(product)
                .request(1000000);
        credit = Credit.builder().build()
                .originalMainDebt(application.request())//сумма кредита
                .currentMainDebt(application.request())//основной долг (сколько осталось заплатить)
                .duration(application.duration())
                .currentMoney(application.request())//деньги (осталось от счёта)
                .fine(0)//проценты
                .product(product)
                .start(Dates.now(-30*2));
    }

    @Test
    public void PaymentPlanTest(){

        System.out.println("----------PaymentPlanTest----------");

        int[] out = new int[3];
        List<Payment> plan;

        product.type(ProductType.Annuity);
        plan = CreditCalculator.PaymentPlan(credit,out);

        System.out.println(String.format("payment plan for %s",credit.product().type()));
        System.out.println(String.format("%d = %d + %d",out[2],out[0],out[1]));
        for(Payment p:plan){
            System.out.println(p.toString());
        }

        product.type(ProductType.Residue);
        plan = CreditCalculator.PaymentPlan(credit,out);

        System.out.println(String.format("payment plan for %s",credit.product().type()));
        System.out.println(String.format("%d = %d + %d",out[2],out[0],out[1]));
        for(Payment p:plan){
            System.out.println(p.toString());
        }

        product.type(ProductType.Percent);
        plan = CreditCalculator.PaymentPlan(credit,out);

        System.out.println(String.format("payment plan for %s",credit.product().type()));
        System.out.println(String.format("%d = %d + %d",out[2],out[0],out[1]));
        for(Payment p:plan){
            System.out.println(p.toString());
        }
    }



}
