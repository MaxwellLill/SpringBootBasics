package com.example.demo.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.repository.Query;

/**
 * TODO: Javadoc
 */
@Entity
@Table(name = "employees")
public class EmployeeImpl extends PersonImpl implements Employee {

    /*  Inherited
    protected String firstName;
    protected String lastName;
    protected String address;
     */
    private String employeeId;

    public EmployeeImpl(String firsName, String lastName, String address, String empId){
        super(firsName, lastName);
        this.address = address;
        this.employeeId = empId;
    }

    public void printAllEmployeeFields(){
        System.out.println("ID:" + employeeId + " " + firstName + " " + lastName);
        System.out.println(address);
    }

    @Override
    public String getEmployeeId() {
        return employeeId;
    }

    @Override
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    @Query()
    public String test(){
        return "test";
    }
}