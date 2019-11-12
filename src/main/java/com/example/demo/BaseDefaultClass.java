package com.example.demo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

import com.example.demo.data.Checkout;
import com.example.demo.data.Employee;
import com.example.demo.data.EmployeeImpl;
import com.example.demo.data.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * TODO: Javadoc
 */
@Component
public class BaseDefaultClass {

    private final Checkout checkout;
    //The following pertains to database experiments
    private final JdbcTemplate jdbcTemplate;
    private final DataSource dataSource;

    //Convert to a setter method for this.
    //Using a constructor with autowired because i want to force these variables to be initialised.
    @Autowired
    public BaseDefaultClass(Checkout checkout, DataSource dataSource, JdbcTemplate jdbcTemplate) {
        this.checkout = checkout;
        this.dataSource = dataSource;
        this.jdbcTemplate = jdbcTemplate;
    }

    public void runBean(){
        System.out.println("runBean()");
        checkout.print();

        //Change the Employee count
        checkout.setEmployees(30);
        checkout.print();

    }

    public void runMoreBeanExamples(ApplicationContext context){
        System.out.println("runMoreBeanTests()");
        /*  Proving i can pass context around as a parameter
        Checkout co = (Checkout)context.getBean("beanChanged");
        co.print();*/

        checkout.addEmployee(new EmployeeImpl("Shahid", "Barton", "90 Address Rd", "001"));
        checkout.addEmployee(new EmployeeImpl("Jovan", "Wilson", "80 Address Rd", "002"));
        checkout.addEmployee(new EmployeeImpl("Nelly", "Holding", "70 Address Rd", "003"));
        checkout.addEmployee(new EmployeeImpl("Yash", "Summers", "60 Address Rd", "004"));
        checkout.printEmployees();
    }

    //Functions for database tests. Familiarising myself with the SQL
    public void runDatabaseExample() throws SQLException{
        System.out.println("runDatabaseTest()");

        //Perform a simple SELECT to get the total number of listed employees.
        String selectQuery = "SELECT COUNT(*) FROM employees";
        int count = jdbcTemplate.queryForObject(selectQuery, Integer.class);
        System.out.println("Number of employees: " + count);

        //Insert an employee instance into the database
        String insertQuery = "INSERT INTO employees (first_name, last_name, address, employee_id) VALUES ('Kareena', 'Willis', '60 Address Rd', '004')";
        jdbcTemplate.execute(insertQuery);      //Another way to execute the statement
//        jdbcTemplate.update(insertQuery);     //Another way to execute the statement

        //The following is a prepared statement example although i think it could be better.
        //Open a connection. Create the SQL prepared statement with Bind Variables.
        Connection connection = dataSource.getConnection();
        String preparedQuery = "INSERT INTO employees (database_id, address, first_name, last_name) VALUES (?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(preparedQuery);

        preparedStatement.setString(1, "Single Prepared Address");
        preparedStatement.setString(2, "Prepared First Name");
        preparedStatement.setString(3, "Prepared Last Name");
        preparedStatement.setString(4, "Prepared employeeId");

        //Execute Prepared SQL Statement
        preparedStatement.execute();

        //Redundant Code. (It's not redundant just an alternative to doing prepared queries
//        jdbcTemplate.execute(preparedQuery, new PreparedStatementCallback<Boolean>() {
//            @Override
//            public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
//
//                ps.setLong(1,5);
//                ps.setString(2, "Address");
//                ps.setString(3, "first");
//                ps.setString(4, "last");
//
//                return ps.execute();
//            }
//        });

        //Perform a simple SELECT to get the total number of listed employees.
        count = jdbcTemplate.queryForObject(selectQuery, Integer.class);
        System.out.println("Number of employees: " + count);

        System.out.println("End of runDatabaseTest()");
    }

    //Inserts a List of employees into the database
    public void runDatabaseListAdd() throws SQLException{
        System.out.println("runDatabaseListAdd()");

        //Nothing significant just creating an array of 3 employees that would represent a N sized list of employees.
        List<Employee> employees = new ArrayList<Employee>();
        employees.add(new EmployeeImpl("Max", "Lill", "90 Address Rd", "001"));
        employees.add(new EmployeeImpl("Lance", "Goodfellow", "80 Address Rd", "002"));
        employees.add(new EmployeeImpl("Franco", "Loi", "70 Address Rd", "003"));

        //A simple SELECT query to get the size of the employee list.
        String selectQuery = "SELECT COUNT(*) FROM employees";
        int count = jdbcTemplate.queryForObject(selectQuery, Integer.class) + 1;
        System.out.println("Number of employees: " + (count-1));

        //Open a connection. Create the SQL prepared statement with Bind Variables.
        Connection connection = dataSource.getConnection();
        String batchPreparedQuery = "INSERT INTO employees VALUES (?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(batchPreparedQuery);

        //Assign values to the Bind variables and add the prepared statement to the batch for processing afterwards.
        for(Employee emp : employees){
            //data, address, first, last, emp
            preparedStatement.setLong(1,count);
            preparedStatement.setString(2, emp.getAddress());
            preparedStatement.setString(3, emp.getFirstName());
            preparedStatement.setString(4, emp.getLastName());
            preparedStatement.setString(5, emp.getEmployeeId());

            //Add the line to the batch
            preparedStatement.addBatch();

            //Increment the counter
            count++;
        }

        //Execute the batch of SQL statements
        preparedStatement.executeBatch();

        //Do a SELECT to confirm that the changes have in fact taken place. (Or you can go look using the h2 console)
        count = jdbcTemplate.queryForObject(selectQuery, Integer.class);
        System.out.println("Number of employees: " + count);

        //Close off connection
        connection.close();
    }

    //Had massive issues with the primary key. Because i labelled PersonImpl as an @Entity. This meant that i couldn't
    // insert a row without specifying the Primary Key.
    public void runDatabasePrimaryKeyExamples() throws SQLException, DataAccessException {
        System.out.println("runDatabasePrimaryKeyTests()");

        //Single INSERT statement
        System.out.println("Performing Primary Key Tests Using a Single Insert");
        String selectQuery = "SELECT COUNT(*) FROM employees";
        int count = jdbcTemplate.queryForObject(selectQuery, Integer.class);
        System.out.println("Number of employee rows: " + count);

        //Insert an employee instance into the database that does not contain a database_id in the INSERT statement
        String insertQuery = "INSERT INTO employees (first_name, last_name, address, employee_id) VALUES ('Kelis', 'Garrison', '60 Address Rd', '004')";
        jdbcTemplate.update(insertQuery);

        System.out.println("Performing Primary Key Tests Using prepared Statements and BATCH");
        count = jdbcTemplate.queryForObject(selectQuery, Integer.class);
        System.out.println("Number of employee rows: " + count);

        //Nothing significant just creating an array of 3 employees that would represent a N sized list of employees.
        List<Employee> employees = new ArrayList<Employee>();
        employees.add(new EmployeeImpl("Jia", "Calhoun", "120 Address Rd", "013"));
        employees.add(new EmployeeImpl("Nicolle", "Meyers", "130 Address Rd", "014"));
        employees.add(new EmployeeImpl("Bridie", "Levy", "140 Address Rd", "012"));
        employees.add(new EmployeeImpl("Fenton", "Arroyo", "150 Address Rd", "015"));

        //Open a connection. Create the SQL prepared statement with Bind Variables.
        Connection connection = dataSource.getConnection();
        String batchPreparedQuery = "INSERT INTO employees (first_name, last_name, address, employee_id) VALUES (?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(batchPreparedQuery);

        //Assign values to the Bind variables and add the prepared statement to the batch for processing afterwards.
        for(Employee emp : employees){
            //data, address, first, last, emp
            preparedStatement.setString(1, emp.getFirstName());
            preparedStatement.setString(2, emp.getLastName());
            preparedStatement.setString(3, emp.getAddress());
            preparedStatement.setString(4, emp.getEmployeeId());

            //Add the line to the batch
            preparedStatement.addBatch();
        }

        //Execute the batch of SQL statements
        preparedStatement.executeBatch();

        //Do a SELECT to confirm that the changes have in fact taken place. (Or you can go look using the h2 console)
        count = jdbcTemplate.queryForObject(selectQuery, Integer.class);
        System.out.println("Number of employee rows: " + count);

        //Close off connection
        connection.close();
    }
    /*          "database_Test" was a single class with 2 fields that was used to test INSERT with no PK in the SQL
        String selectQuery = "SELECT COUNT(*) FROM database_Test";
        int count = jdbcTemplate.queryForObject(selectQuery, Integer.class);
        System.out.println("Number of databaseTest rows: " + count);
        String insertQuery = "INSERT INTO database_Test (name) VALUES ('test')";
        jdbcTemplate.execute(insertQuery);
        count = jdbcTemplate.queryForObject(selectQuery, Integer.class);
        System.out.println("Number of databaseTest rows: " + count);
        */

    public void runParameterExamples(){
        System.out.println("runParameterTests");

        System.out.println("Output removed. Concept tested and behaviour is unfortunately if no fields are specified THEN PK is expected.");

        //No parameters listed so what's it going to expect? Hopefully the PK / database_id won't be required.
        //INCORRECT. IF YOU DON'T SPECIFY THE FIELDS IT'LL DEFAULT TO EXPECTING ALL WHICH INCLUDES database_id
        String insertQuery = "INSERT INTO employees VALUES ('ParameterTestAddress', 'ParameterTestFirst', 'ParameterTestLast', 'ParameterTestEID')";
        jdbcTemplate.update(insertQuery);
    }

    //A function that involves some logic to create a test.
    public List<Employee> getEmployeeWithEvenDatabaseId(){
        System.out.println("getEmployeeWithEvenDatabaseId()");
        List<Employee> employees = new ArrayList<Employee>();

        //Select all rows that have an even database_id
//        String query = "SELECT * FROM employees";
        String query = "SELECT * FROM employees WHERE (database_id % 2) = 0";

        //Return a list of Employee Objects
        employees = jdbcTemplate.query(query, new EmployeeMapper());

        return employees;
    }

    //This function will be used to give examples of and show how lambda's can be used
    public List<Employee> getAllEmployeesFromDatabase() {
        System.out.println("getAllEmployeesFromDatabase()");

        //Load all the employee's for testing.
        List<Employee> employees = new ArrayList<Employee>();
        String query = "SELECT * FROM employees";
        employees = jdbcTemplate.query(query, new EmployeeMapper());
        return employees;
    }

    //Code that pertains to Lambda's
    interface LambdaFunctionalInterface{
        String outputStringOperation(String input);
    }

    public void callFunction(LambdaFunctionalInterface test, String input){
        System.out.println(test.outputStringOperation(input));
    }

    public void runLambdaExample(LambdaFunctionalInterface lambdaFunction) {
        System.out.println("runLambdaTest()");
        List<Employee> employees = getAllEmployeesFromDatabase();

        //Print the full name from the employee list.
        employees.forEach(e -> {
            System.out.println(e.getFullName());
        });

        String inputString = "{This is the input String variable}";

        System.out.println(lambdaFunction.outputStringOperation(inputString));
    }
}