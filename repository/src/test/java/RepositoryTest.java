import com.mysema.query.types.Predicate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kofi.creditex.model.*;
import org.kofi.creditex.repository.CreditRepository;
import org.kofi.creditex.repository.PaymentRepository;
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
    private PaymentRepository paymentRepository;
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void Tester(){
    }
}
