import com.mysema.query.types.Predicate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kofi.creditex.model.*;
import org.kofi.creditex.repository.CreditRepository;
import org.kofi.creditex.repository.OperationTypeRepository;
import org.kofi.creditex.repository.PaymentRepository;
import org.kofi.creditex.repository.UserDetailsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@ContextConfiguration(locations = "classpath:repo-test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional(propagation = Propagation.REQUIRED)
public class RepositoryTest {
    Logger logger = LoggerFactory.getLogger(RepositoryTest.class);
    @Autowired
    private OperationTypeRepository operationTypeRepository;

    @Autowired
    private PaymentRepository paymentRepository;
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void Tester(){
        OperationType type = OperationType.builder().value("This is element").build();

        operationTypeRepository.save(type);
    }

    /**
     @Autowired
     private UserDetailsRepository userDetailsRepository;
     @Autowired
     private CreditRepository creditRepository;

    @Test
    public void Test2(){
        ProductType productType = ProductType.builder().build().value("annuity");
        PriorRepayment priorRepayment = PriorRepayment.builder().build().value("na");
        Product product = Product.builder().build()
                .name("product1")
                .type(productType)
                .prior(priorRepayment)
                .debtPercent(1)
                .minMoney(1000000)
                .maxMoney(5000000)
                .minCommittee(2000000)
                .percent(50)
                .priorRepaymentPercent(10)
                .priorRepaymentDebtLimit(0.5f);

        UserDetails details = UserDetails.builder().build().first("user1");
        UserCredentials user = UserCredentials.builder().build().username("user1").userDetails(details);

        Credit credit = Credit.builder().build()
                .sum(1000000)
                .debt(10000)
                .userCredentials(user)
                .product(product);



    }
    /**/
}
