package repository;

import main.Main;
import model.Account;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

@ContextConfiguration(classes = Main.class)
public class UserRepositoryTest extends AbstractTransactionalTestNGSpringContextTests {

    @Test
    @Transactional(propagation = Propagation.NEVER)
    public void testCreateUser() {
        Account account = new Account();
        account.setName("My Account");

        account = accountRepo.saveAndFlush(account);

        User user = new User();
        user.setAccount(account);
        user.setEmail("foo@bar.com");
        user = userRepo.saveAndFlush(user);

        //User user2 = userRepo.findOne(new UserPK(user.id, account.id));
        User user2 = userRepo.findById(user.getId());
        assertNotNull(user2, "Expected User object to not be null");
        assertEquals(user2.getBeanVersion(), user.getBeanVersion());

        user.setEmail("nope");
        user = userRepo.saveAndFlush(user);

        //user2 = userRepo.findOne(new UserPK(user.id, account.id));
        user2 = userRepo.findById(user.getId());
        assertNotNull(user2, "Expected User object to not be null");
        assertEquals(user2.getEmail(), user.getEmail());
        assertEquals(user2.getBeanVersion(), user.getBeanVersion());
    }

    @Autowired
    AccountRepository accountRepo;

    @Autowired
    UserRepository userRepo;

}
