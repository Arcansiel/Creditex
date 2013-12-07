import com.mysema.query.types.Predicate;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kofi.creditex.model.*;
import org.kofi.creditex.repository.AuthoritiesRepository;
import org.kofi.creditex.repository.CreditRepository;
import org.kofi.creditex.repository.PaymentRepository;
import org.kofi.creditex.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.Assert.*;

import java.util.List;


@ContextConfiguration(locations = "classpath:repo-test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional(propagation = Propagation.REQUIRED)
public class RepositoryTest {
    Logger logger = LoggerFactory.getLogger(RepositoryTest.class);
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private AuthoritiesRepository authoritiesRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder encoder;
    @Before
    @Rollback(value = false)
    public void setUp() throws Exception {
//        Authority initialAuthority = new Authority().setAuthority("ROLE_CLIENT");
//        authoritiesRepository.save(initialAuthority);
    }
    @Ignore
    @Test
    public void Tester(){

        UserData userData = new UserData();
        userData
                .setFirst("name")
                .setLast("name")
                .setPatronymic("name")
                .setPassportSeries("name")
                .setPassportNumber(1)
                .setWorkName("name")
                .setWorkPosition("name")
                .setWorkIncome(1);
        Predicate query = QAuthority.authority1.authority.eq("ROLE_CLIENT");
        Authority auth1 = authoritiesRepository.findOne(query);
        Authority authority = authoritiesRepository.findByAuthority("ROLE_CLIENT");
        if (authority!=null)
            logger.info("Authority:"+authority.toString());
        else
            logger.info("Crap, it is null");
        if (auth1!=null){
            logger.info("Auth1:"+auth1.toString());
        }
        else
            logger.info("Crap, it is null too");
        User user = new User();
        user
                .setAccountNonExpired(true)
                .setAccountNonLocked(true)
                .setCredentialsNonExpired(true)
                .setEnabled(true)
                .setUsername("name")
                .setPassword("name")
                .setUserData(userData)
                .setAuthority(authority);
        userRepository.save(user);
    }
    @Test
    public void testCannotReproduceHashFromConfigFile() {
        String password = "cl";
        String encoded = encoder.encode(password);

        String old = "$2a$10$Qds3EgJAXH1P5evUqVgpMOqGCL5o1wd0howjtFY5/3gEVXQEgKu3K";

        // But both what's in the XML and what we generated match the password.
        assertTrue(encoder.matches(password, encoded));
        assertTrue(encoder.matches(password,old));
        System.out.println(encoded);
    }

    @Test
    public void testAnotherRunWillAlsoYieldDifferentHashes() {
        String password = "koala";
        String encoded = encoder.encode(password);
        String encoded2 = encoder.encode(password);
        assertTrue(encoded2 != encoded);

        System.out.println(encoded);
        System.out.println(encoded2);
    }
}
