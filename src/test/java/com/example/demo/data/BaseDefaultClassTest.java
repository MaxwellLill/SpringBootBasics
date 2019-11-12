package com.example.demo.data;

import java.util.List;

import javax.sql.DataSource;

import com.example.demo.BaseDefaultClass;
import com.example.demo.config.AppConfig;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/*
Some notes:
########################
The two code segments below achieve the same thing. The @RunWith (runner) will let you @Mock things in your code. Similarlly the
@Rule will let you @Mock things in your code.

    @RunWith(MockitoJUnitRunner.class)
    public class EmployeeImplTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();
########################
If you don't use the MockitoJUnit rule/runner you can still mock things by doing in the example:

Checkout checkout = mock(Checkout.class);

########################
If you want to use spring beans then you can use a SpringJUnit4ClassRunner This will let you use beans in a specified
config file. By using @ContextConfiguration(classes = {ConfigClassName.class}) it will specify which beans to load up.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class BaseDefaultClassTest {

//    @Autowired
    private BaseDefaultClass baseDefaultClass;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSource dataSource;

    //Will run once before all tests if the class is run. This could be good if you don't want to @Before EVERY test.
//    @BeforeClass
//    public void setupBeforeClass(){
//        Checkout checkout = mock(Checkout.class);
//        DataSource dataSource = mock(DataSource.class);
//        JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);
//        baseDefaultClass = new BaseDefaultClass(checkout, dataSource, jdbcTemplate);
//    }

    //Will run before each test that runs.
    @Before
    public void setup() {
        Checkout checkout = mock(Checkout.class);

//        DataSource dataSource = mock(DataSource.class);
//        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
//        driverManagerDataSource.setDriverClassName("org.h2.Driver");
//        //If you want it in memory (not persistent) use the following instaed of giving it a filePathName
////        driverManagerDataSource.setUrl("jdbc:h2:mem:testdb");
//        driverManagerDataSource.setUrl("jdbc:h2:file:~/filePathName");
//        driverManagerDataSource.setUsername("sa");
//        driverManagerDataSource.setPassword("");
//        dataSource = driverManagerDataSource;

//        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        baseDefaultClass = new BaseDefaultClass(checkout, dataSource, jdbcTemplate);
    }

    @Test
    public void shouldReturnListWithSpecificName() {
        //given
        List<Employee> employees = baseDefaultClass.getEmployeeWithEvenDatabaseId();

        String foundNameString = "";
        String serachName = "Max";
        boolean foundNameFlag = false;

        //when
        for(Employee employee : employees){
            if(employee.getFirstName().equals(serachName)){
                foundNameFlag = true;
            }
        }

        //then
//        assertThat(employees.size(), is(4));
        assertTrue(foundNameFlag);
    }

    @Test
    public void shouldReturnAllEmployeesWithEvenIds() {
        //given
        List<Employee> employees = baseDefaultClass.getEmployeeWithEvenDatabaseId();

        String foundNameString = "";
        String serachName = "Max";
        boolean foundOddDatabaseId = false;

        //when
        for(Employee employee : employees){
            if(employee.getDatabaseId() % 2 == 1){
                foundOddDatabaseId = true;
            }
        }

        //then
        assertFalse(foundOddDatabaseId);
    }
}