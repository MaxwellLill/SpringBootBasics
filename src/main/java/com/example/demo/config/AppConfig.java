package com.example.demo.config;

import javax.sql.DataSource;

import com.example.demo.BaseDefaultClass;
import com.example.demo.data.Checkout;
import com.example.demo.data.CheckoutImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * TODO: Javadoc
 */
@Configuration
public class AppConfig {

    @Primary
    @Bean
    public Checkout createCheckout(){
        return new CheckoutImpl();
    }

    @Bean
    public Checkout checkout(){
        return new CheckoutImpl();
    }

    @Bean
    public Checkout checkoutFunctionNameChange(){
        return new CheckoutImpl();
    }

    @Bean(name = "BeanNameChanged")
    public Checkout createCheckout2(){
        return new CheckoutImpl();
    }

    @Bean
    public Checkout callAnotherBean(){
        return createCheckout();
    }

    @Bean
    public BaseDefaultClass createBaseDefaultClass(){
        return new BaseDefaultClass(createCheckout(), dataSource(), jdbcTemplate());
    }

    //Creating a DataSource bean and JdbcTemplate so that i can autowire them and use the JdbcTemplate Drivers to
    // access the H2 database.
    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();

        driverManagerDataSource.setDriverClassName("org.h2.Driver");
        //If you want it in memory (not persistent) use the following instaed of giving it a filePathName
//        driverManagerDataSource.setUrl("jdbc:h2:mem:testdb");
        driverManagerDataSource.setUrl("jdbc:h2:file:~/filePathName");
        driverManagerDataSource.setUsername("sa");
        driverManagerDataSource.setPassword("");

        return driverManagerDataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }


//    @Bean
//    public DataSource dataSource(){
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//
//        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
//        dataSource.setUsername("mysqluser");
//        dataSource.setPassword("mysqlpass");
//        dataSource.setUrl("jdbc:mysql://localhost:8080/spring_jpa");
//
//        return dataSource;
//    }
//
//    @Bean
//    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
//        JpaTransactionManager transactionManager = new JpaTransactionManager();
//        transactionManager.setEntityManagerFactory(emf);
//
//        return transactionManager;
//    }
//
//    @Bean
//    public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
//        return new PersistenceExceptionTranslationPostProcessor();
//    }
//
//    Properties additionalProperties() {
//        Properties properties = new Properties();
//        properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
//        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
//
//        return properties;
//    }
//
//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
//        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
//        em.setDataSource(dataSource());
//        em.setPackagesToScan(new String[] {"com.example.demo"});
//
//        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//        em.setJpaVendorAdapter(vendorAdapter);
//        em.setJpaProperties(additionalProperties());
//
//        return em;
//    }
}