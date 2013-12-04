
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kofi.creditex.DateProvider;
import org.kofi.creditex.model.*;
import org.kofi.creditex.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;
import org.kofi.creditex.creditcalc.*;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration(locations = "classpath:service-test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional(propagation = Propagation.REQUIRED)
public class CreditCalculatorTest {

    @Autowired
    private ProductRepository productRepository;
    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void SimpleTest(){
    }
}
