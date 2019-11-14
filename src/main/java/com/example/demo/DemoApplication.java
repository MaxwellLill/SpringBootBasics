package com.example.demo;

import java.sql.SQLException;

import com.example.demo.config.AppConfig;
import com.example.demo.data.Checkout;
import com.example.demo.data.CheckoutImpl;
import com.example.demo.data.Employee;
import com.example.demo.data.EmployeeImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;

@SpringBootApplication
@EntityScan("com.example.demo")
@ComponentScan("com.example.demo")
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

		//#######################################			Get the bean for checkout and call print();
		System.out.println("bean");
		Checkout co;

		System.out.println("@Primary call");
		co = context.getBean(CheckoutImpl.class);
		co.print();

		System.out.println("@Bean Call specific function");
		co = context.getBean("checkout", CheckoutImpl.class);	//Using bean that return ChecoutImpl to find the right object
		co.print();

		System.out.println("Function Name Change");
		co = context.getBean("checkoutFunctionNameChange", CheckoutImpl.class);	//Using the name of the bean function and returnType to find the object
		co.print();

		System.out.println("@Bean(name =)");
		co = (Checkout)context.getBean("BeanNameChanged");		//Using the name of the Bean and returnType to find the object
		co.print();

		System.out.println("Create a Bean from Another Bean");
		co = (Checkout)context.getBean("callAnotherBean");
		co.print();

//		System.out.println("Parametised");
//		co = context.getBean(CheckoutImpl.class, "checkoutParameterised", 20);	//FAILING
//		co.print();


		//#######################################			Use a wrapper class to create a checkout and call print();
		System.out.println("\nBaseDefaultClass");
		//BaseDefaultClass bdc = new BaseDefaultClass();
		BaseDefaultClass bdc = context.getBean(BaseDefaultClass.class);
		bdc.runBean();


		//#######################################            Experimenting with beans and non-bean initialisiation
		System.out.println("\nEmployee stuff");
		bdc.runMoreBeanExamples(context);


		//#######################################           Testing HOW to use jdbcTemplate to the h2 database.
		System.out.println("\nDatabase testing");
		try{
            bdc.runDatabaseExample();
        }
		catch(SQLException e){
		    System.err.println(e);
		    System.out.println("\nError Dealing with the single Database statements.");
        }


        //#######################################           Testing the syntax and use of batch prepared statements.
        System.out.println("\nBatch Database Testing");
		try{
            bdc.runDatabaseListAdd();
        }
		catch(SQLException e){
            System.out.println("\nError Dealing with the Batch statements.");
        }


        //#######################################           Testing ways to get the primary key automatically rather than getting a count.
        System.out.println("\nPrimary Key Tests");
        try{
            bdc.runDatabasePrimaryKeyExamples();
        }
        catch(SQLException e){
            System.err.println(e);
            System.out.println("\nError handling the auto primary key tests.");
        }
        catch(DataAccessException e){
            System.err.println(e);
            System.out.println("\nError handling the auto primary key tests.");
        }

/*
        //#######################################           Testing that limitations and expectations of an SQL statement
        System.out.println("\nSQL statement parameter tests");
        bdc.runParameterTests();
 */

		//#######################################           Testing the use of Lambda's
//		From what i can gather you can use a lambda if you have a functional interface that can accept the format of
//		the given lambda.
		System.out.println("\nLambda Tests");
		//Create a lambda using the functional interface in BaseDefaultclass
		//First
		BaseDefaultClass.LambdaFunctionalInterface firstLambda = (String input) -> {
			System.out.println(input);
			return "outputting given variable: " + input;
		};
		//Second
		BaseDefaultClass.LambdaFunctionalInterface secondLambda = (String input) -> {
			System.out.println(input);
			return "new output for given variable: " + input;
		};
		//Third
		BaseDefaultClass.LambdaFunctionalInterface thirdLambda = (String input) -> {
			System.out.println(input);
			input = input + "{Some additional String should go here}";
			return "Changing the output again: " + input;
		};

		//Calling each lambda
		bdc.runLambdaExample(firstLambda);
		bdc.runLambdaExample(secondLambda);


		//Calling a function that has a nice testing ground for breakpoints.
		System.out.println("Debugging Example Start");
		bdc.exampleDebuggingFunction("POJS plain old java string", 3);
		System.out.println("Debugging Example End");
	}
}
