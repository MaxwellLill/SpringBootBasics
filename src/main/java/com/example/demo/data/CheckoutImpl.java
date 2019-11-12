package com.example.demo.data;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

/**
 * TODO: Javadoc
 */
@Component
public class CheckoutImpl implements Checkout {
    private String businessName;
    private int employeesCount;
    private ArrayList<Employee> employees = new ArrayList<Employee>();

    public CheckoutImpl(){
        businessName = "Test Business Name";
        employeesCount = 10;
    }

    public CheckoutImpl(int newEmployeesCount){
        businessName = "Test Business Name";
        employeesCount = newEmployeesCount;
    }

    @Override
    public void addEmployee(Employee employee){
        employees.add(employee);
    }

    @Override
    public void printEmployees(){
        for(int i = 0; i < employees.size(); i++){
            System.out.println("Employee name: " + employees.get(i).getFirstName());
        }
    }

    @Override
    public void print(){
        System.out.println(businessName + " has num employees: " + employeesCount);
    }

    @Override
    public String getBusinssName() {
        return businessName;
    }

    @Override
    public void setBusinssName(String businssName) {
        this.businessName = businssName;
    }

    @Override
    public int getEmployees() {
        return employeesCount;
    }

    @Override
    public void setEmployees(int employeesCount) {
        this.employeesCount = employeesCount;
    }
}