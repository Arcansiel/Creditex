
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kofi.creditex.service.UserService;
import org.kofi.creditex.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration(locations = "classpath:service-test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional(propagation = Propagation.REQUIRED)
public class CreditCalculatorTest {
    @Autowired
    private UserDetailsService userService;
    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void SimpleTest(){
        userService.loadUserByUsername("SomeName");

    }


}
