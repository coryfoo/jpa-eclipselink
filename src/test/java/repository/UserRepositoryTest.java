package repository;

import main.Main;
import model.Account;
import model.User;
import model.UserPK;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;

@ContextConfiguration(classes = Main.class)
public class UserRepositoryTest extends AbstractTransactionalTestNGSpringContextTests {

    @Test
    @Transactional(propagation = Propagation.NEVER)
    public void testCreateUser() {
        Account account = new Account();
        account.name = "My Account";
        account.id = 1L;

        account = accountRepo.saveAndFlush(account);

        User user = new User();
        user.id = 1L;
        user.account = account;
        user.email = "foo@bar.com";

        user = userRepo.saveAndFlush(user);

        assertNotNull(userRepo.findOne(new UserPK(user.id, account.id)), "Expected User object to not be null");
    }

    @Autowired
    AccountRepository accountRepo;

    @Autowired
    UserRepository userRepo;

}
