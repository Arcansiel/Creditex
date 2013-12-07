import org.junit.Before;
import org.junit.Ignore;
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
        product = Product.builder()
                .minMoney(500000)
                .maxMoney(2000000)
                .minCommittee(Integer.MAX_VALUE)
                .name("product1")
                .percent(50)
                .debtPercent(1)
                .prior(PriorRepayment.AvailableFineInterest)
                .priorRepaymentPercent(10)
                .priorRepaymentDebtLimit(0.5f)
                .type(ProductType.Annuity)
                .build();
        application = new Application();
        application.setDuration(24);
        application.setProduct(product);
        application.setRequest(1000000);

        credit = Credit.builder()
                .originalMainDebt(application.getRequest())//сумма кредита
                .currentMainDebt(application.getRequest())//основной долг (сколько осталось заплатить)
                .duration(application.getDuration())
                .currentMoney(application.getRequest())//деньги (осталось от счёта)
                .fine(0)//проценты
                .product(product)
                .start(Dates.now(-30*2))
                .build();
    }

    @Test
    public void PaymentPlanTest(){

        System.out.println("----------PaymentPlanTest----------");

        int[] out = new int[3];
        List<Payment> plan;

        product.setType(ProductType.Annuity);
        plan = CreditCalculator.PaymentPlan(credit,out);

        System.out.println(String.format("payment plan for %s",credit.getProduct().getType()));
        System.out.println(String.format("%d = %d + %d",out[2],out[0],out[1]));
        for(Payment p:plan){
            System.out.println(p.toString());
        }

        product.setType(ProductType.Residue);
        plan = CreditCalculator.PaymentPlan(credit,out);

        System.out.println(String.format("payment plan for %s",credit.getProduct().getType()));
        System.out.println(String.format("%d = %d + %d",out[2],out[0],out[1]));
        for(Payment p:plan){
            System.out.println(p.toString());
        }

        product.setType(ProductType.Percent);
        plan = CreditCalculator.PaymentPlan(credit,out);

        System.out.println(String.format("payment plan for %s",credit.getProduct().getType()));
        System.out.println(String.format("%d = %d + %d",out[2],out[0],out[1]));
        for(Payment p:plan){
            System.out.println(p.toString());
        }
    }



}
