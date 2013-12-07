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
        product = new Product();
        product.setMinMoney(500000);
        product.setMaxMoney(2000000);
        product.setMinCommittee(Integer.MAX_VALUE);
        product.setName("product1");
        product.setPercent(50);
        product.setDebtPercent(1);
        product.setPrior(PriorRepayment.AvailableFineInterest);
        product.setPriorRepaymentPercent(10);
        product.setPriorRepaymentDebtLimit(0.5f);
        product.setType(ProductType.Annuity);

        application = new Application();
        application.setDuration(24);
        application.setProduct(product);
        application.setRequest(1000000);
        application.setApplicationDate(Dates.now(-70));

        credit = new Credit();
        credit.setOriginalMainDebt(application.getRequest());//сумма кредита
        credit.setCurrentMainDebt(application.getRequest());//основной долг (сколько осталось заплатить)
        credit.setDuration(application.getDuration());
        credit.setCurrentMoney(application.getRequest());//деньги (осталось от счёта)
        credit.setFine(0);//проценты
        credit.setProduct(product);
        credit.setStart(Dates.now(-30*2));
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
