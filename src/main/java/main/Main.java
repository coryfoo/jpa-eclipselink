package main;

import model.Account;
import model.User;
import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.instrument.classloading.LoadTimeWeaver;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import repository.AccountRepository;
import repository.UserRepository;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(basePackages = "repository")
@EnableTransactionManagement
@ComponentScan({ "model", "repository" })
@EnableLoadTimeWeaving
public class Main implements LoadTimeWeavingConfigurer {

    public LoadTimeWeaver getLoadTimeWeaver() {
        return new InstrumentationLoadTimeWeaver();
    }

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, LoadTimeWeaver weaver) throws SQLException {
        EclipseLinkJpaVendorAdapter vendorAdapter = new EclipseLinkJpaVendorAdapter();
        vendorAdapter.setDatabase(Database.H2);
        vendorAdapter.setGenerateDdl(false);
        vendorAdapter.setShowSql(true);

        final Properties props = new Properties();
        props.setProperty(PersistenceUnitProperties.TRANSACTION_TYPE, "RESOURCE_LOCAL");
        props.setProperty(PersistenceUnitProperties.NATIVE_SQL, "true");
        props.setProperty(PersistenceUnitProperties.WEAVING, "true");
        props.setProperty(PersistenceUnitProperties.DDL_GENERATION, "create-tables");
        props.setProperty(PersistenceUnitProperties.JDBC_DRIVER, org.h2.Driver.class.getName());

        final LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setJpaProperties(props);
        factory.setPackagesToScan("model", "repository");
        factory.setDataSource(dataSource);
        factory.setLoadTimeWeaver(weaver);

        return factory;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(emf);
        return txManager;
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Main.class);
        UserRepository userRepo = ctx.getBean(UserRepository.class);
        AccountRepository accountRepo = ctx.getBean(AccountRepository.class);

        Account account = new Account();
        account.setName("Foo Account");

        account = accountRepo.save(account);

        User user = new User();
        user.setAccount(account);

        userRepo.flush();
        accountRepo.flush();

        final long userID = userRepo.save(user).getId();

        new Thread(() -> {
            User u = userRepo.findById(userID);
            System.out.println(u);
        }).start();
    }
}
