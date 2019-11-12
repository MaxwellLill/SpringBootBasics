package com.example.demo.data;

import com.example.demo.data.Employee;

/**
 * TODO: Javadoc
 */
public interface Checkout {
    void print();

    String getBusinssName();

    void setBusinssName(String businssName);

    int getEmployees();

    void setEmployees(int employees);

    void addEmployee(Employee employee);

    public void printEmployees();
}
