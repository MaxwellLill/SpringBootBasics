package com.example.demo.controller;

import com.example.demo.data.Employee;
import com.example.demo.data.EmployeeImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;

/**
 * TODO: Javadoc
 * Breakdown of the file
 * 4 methods on Basic @RequestMapping stuff.
 * 3 methods on Annotation variations.
 * 4 methods on I/O involving the annotations.
 * 3 methods using the above in a practical example.
 */
@Controller
//@RestController           //Use this annotation to implicitly add the @ResponseBody annotation to each of the routing methods in the class. As well as others but this is the most obvious.
public class AppController {
    /*
    Some background notes.
    defaultVariableOutput.html = Is a file that you can use to send variables straight to a html page.
    form.html = form gets input from the user and submits the variables. These pertain to an Employee
    greeting.html = Takes a single variables and is paired with some text.
    index.html = is a straight up blank page. Consider it a default page.
    mappingExampleForm.html = This is just a form that can be used to inititiate a GET/POST request
     */

    //#######################################################       BASELINE ROUTING (I THINK)
    @RequestMapping(value = "/requestMappingExample", method = RequestMethod.GET)
    public String requestMappingExample(){
        System.out.println("requestMappingExample");

        return "index.html";
    }

    //ModelAndView example
    @RequestMapping(value = "/requestMappingExample2", method = RequestMethod.GET)
    public ModelAndView requestMappingExample2(){
        System.out.println("requestMappingExample2");

        ModelAndView model = new ModelAndView("index.html");
        return model;
    }

    //Notice the different syntax with the strings for the 'value' and 'model'.
    @RequestMapping(value = "routingSyntaxExample", method = RequestMethod.GET)     //Can use POST, DELETE, PUT etc...
    public ModelAndView routingSyntaxExample(){
        System.out.println("routingSyntaxExample");

        ModelAndView model = new ModelAndView("index");
        return model;
    }

    //Use @ResponseBody to build the response. Can use it to dump JSON onto the html page (Bad wording ik but idk the correct phrasing yet)
    @RequestMapping(value = "responseBodyExample", method = RequestMethod.GET)     //Can use POST, DELETE, PUT etc...
    @ResponseBody
    public String responseBodyExample(){
        System.out.println("responseBodyExample");

        return "Dump an Object here if  you want. Just don't forget to change the return type.";
    }

    //#####################################             SIMPLIFYING ANNOTATIONS
    //Just so we can route to each of the mappingExamples. Pointless if you use Postman
    @GetMapping(value = "mappingExampleForm")
    public String postMappingInitialiser(){
        System.out.println("mappingExampleForm");
        return "mappingExampleForm";
    }

    //Method = GET
    @GetMapping(value = "getMappingExample")
    @ResponseBody
    public String getMappingExample(){
        System.out.println("getMappingExample");

        return "No Response Body Because it's a GET look in the URL";
    }

    //Method = POST
    @PostMapping(value = "postMappingExample")
    @ResponseBody
    public String postMappingExample(@RequestBody String responseBody){
        System.out.println("postMappingExample");

        //Show the requestBody output
        System.out.println(responseBody);

        return responseBody;
    }

    //#####################################             GETTING AND USING VARIABLES

    //Demonstrate how to use variables in HTML page. Probably not going to be worthwhile but still interesting.
    @GetMapping(value = "/outputToUser")
    public String outputToUser(Model model){
        System.out.println("outputToUser");

        model.addAttribute("var1", "This is variable 1");
        return "defaultVariableOutput";
    }

    //Demonstrating an alternative to variable usage
    @GetMapping(value = "/outputToUser2")
    public ModelAndView outputToUser2(){
        System.out.println("outputToUser2");

        //Using ModelAndView
        ModelAndView model = new ModelAndView("defaultVariableOutput");
        model.addObject("var1","This is variable 1");
        return model;
    }

    //Using Both at the same time.
    @GetMapping(value = "/outputToUser3")
    public ModelAndView outputToUser3(Model model){
        System.out.println("outputToUser2");

        //Using Model
        model.addAttribute("var1", "This is variable 1");

        //Using ModelAndView
        ModelAndView modelView = new ModelAndView("defaultVariableOutput");
        modelView.addObject("var2","This is variable 2");
        return modelView;
    }

    //This is testing input.Copy the below including '?' into the url along with inputFrom User.
    // ?inputFromUser=myTestInput
    @GetMapping(value = "inputFromUser")
    public ModelAndView inputFromUser(@RequestParam("inputFromUser") String input){
        System.out.println("inputFromUser");

        System.out.println(input);

        //Using ModelAndView
        ModelAndView modelView = new ModelAndView("defaultVariableOutput");
        modelView.addObject("var1", input);
        return modelView;
    }

    //#########################################             PRACTICAL EXAMPLE

    //Demonstrating the responseBody of an employee Object
    @GetMapping(value = "employeeResponseBody")
    @ResponseBody
    public Employee employeeResponseBody(){
        System.out.println("employeeResponseBody");

        //Create new employee
        Employee employee = new EmployeeImpl("Max","Lill","90 Address Rd","01");
        return employee;
    }

    //Route the form address
    @GetMapping(value = "/form.html")
    public ModelAndView formDisplay(){
        System.out.println("/form.html");

        //Create a new ModelAndView to label the next page to move to.
        ModelAndView model = new ModelAndView("form");
        return model;
    }

    //Route the response from the form.html <form> output
    @PostMapping(value = "/submitNewEmployee")
//    public String setNewEmployeeInfo(@RequestBody Employee employee, Model model){
    public String setNewEmployeeInfo(
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("address") String address,
            Model model
    ){
        System.out.println("submitNewEmployee");    //@RequestParam is used to get fields from the <form>.

        //Show that we've actually got the variables.
        Employee employee = new EmployeeImpl(firstName, lastName, address, "01");
        System.out.println(employee.getFirstName());

        //Add the employee's full name
        model.addAttribute("name", employee.getFirstName() + " " + employee.getLastName());
        return "greeting";
    }

    //################################      FAILED
//    //Experiment was to see if i could get an Employee Object Straight out of the response but it didn't work
//    //Route the response from the form.html <form> output
//    @PostMapping(value = "/submitNewEmployee")
//    public String setNewEmployeeInfo(@RequestBody Employee employee, Model model){
//        System.out.println("submitNewEmployee");    //@RequestParam is used to get fields from the <form>.
//
//        //Show that we've actually got the variables.
//        System.out.println(employee.getFirstName());
//
//        //Add the employee's full name
//        model.addAttribute(employee.getFirstName() + " " + employee.getLastName());
//        return "greeting";
//    }

    //#########################################             Database Example

    @Autowired
    JdbcTemplate jdbcTemplate;

    //Route the form address
    @GetMapping(value = "/employeeCreationForm")
    public ModelAndView employeeCreationForm(){
        System.out.println("/employeeCreationForm");

        //Create a new ModelAndView to label the next page to move to.
        ModelAndView model = new ModelAndView("employeeCreationForm");
        return model;
    }

    @PostMapping(value = "/employeeCreation")
    public String employeeCreation(
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("address") String address,
            @RequestParam("employeeId") String employeeId,
            Model model
    ){
        System.out.println("/employeeCreation");

        String selectQuery = "SELECT COUNT(*) FROM employees";
        int database_id = jdbcTemplate.queryForObject(selectQuery, Integer.class) + 1;

        String successful;
        try{
            //Insert an employee instance into the database
            String insertQuery = "INSERT INTO employees VALUES ("+ database_id + ", '" + address + "', '" + firstName + "', '" + lastName + "', '" + employeeId + "')";
            jdbcTemplate.execute(insertQuery);

            successful = "Employee was created successfully.";
        }
        catch(DataAccessException e){
            System.err.println(e);
            successful = "Saving the employee failed. Please try again";
        }

        model.addAttribute("name",successful);
        return "employeeCreationComplete";
    }
}