package com.example.demo.controller;

import java.util.ArrayList;

import com.example.demo.data.EmployeeImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO: Javadoc
 */
@RestController
public class postmanController {

    //Give a complete object via raw JSON.
    @PostMapping(value = "completeEmployeeObjectResponse")
    public String completeEmployeeObjectResponse(@RequestBody EmployeeImpl emp){
        System.out.println("completeEmployeeObjectResponse");

        emp.printAllEmployeeFields();
        return "Success Employee Object Successfully Created (Probably)";
    }
    /*
{
    "firstName": "Max",
    "lastName": "Lill",
    "address": "90 Address Rd",
    "employeeId": "01"
}
     */

    //Too few fields in JSON Body
    @PostMapping(value = "incompleteEmployeeObjectReseponse")
    public String incompleteEmployeeObjectReseponse(@RequestBody EmployeeImpl emp){
        System.out.println("incompleteEmployeeObjectReseponse");

        emp.printAllEmployeeFields();
        return "Success Employee Object Successfully Created (Probably)";
    }
    /*
{
    "firstName": "Max",
    "lastName": "Lill",
    "employeeId": "01"
}
     */

    //Too many fields in JSON Body. (Also represents incorrectly labelled fields)
    @PostMapping(value = "overCompleteEmployeeObjectReseponse")
    public String overCompleteEmployeeObjectReseponse(@RequestBody EmployeeImpl emp){
        System.out.println("overCompleteEmployeeObjectReseponse");

        emp.printAllEmployeeFields();
        return "Success Employee Object Successfully Created (Probably)";
    }
    /*
{
	"firstName":"Max",
	"lastName":"Lill",
	"address":"90 Address Rd",
	"employeeId":"01",
	"departmentId":"Mathematics"
}
     */

    //Array JSON Body
    @PostMapping(value = "arrayEmployeeResponse")
    public String arrayEmployeeResponse(@RequestBody ArrayList<EmployeeImpl> employees){
        System.out.println("arrayEmployeeResponse");

        for(EmployeeImpl emp : employees){
            emp.printAllEmployeeFields();
        }
        return "arrayEmployeeResponse Success";
    }
    /*
[
    {
        "firstName": "Max",
        "lastName": "Lill",
        "address": "90 Address Rd",
        "employeeId": "01",
        "departmentId": "Mathematics"
    },
    {
        "firstName": "Lance",
        "lastName": "G",
        "address": "90 Address Rd",
        "employeeId": "01",
        "departmentId": "Mathematics"
    }
]
     */

    //Response Body
    @PostMapping(value = "arrayResponseBody")
    public String arrayResponseBody(){
        System.out.println("arrayResponseBody");

        return "arrayEmployeeResponse Success";
    }
}