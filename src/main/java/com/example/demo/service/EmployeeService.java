package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.data.Employee;
import com.example.demo.data.EmployeeImpl;
import com.example.demo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * TODO: Javadoc
 */
public class EmployeeService{

    public void print(){
        System.out.println("EmployeeService print()");
    }

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void testFunction(){
        jdbcTemplate.execute("SELECT * FROM employee");
    }

//    @Autowired
//    private EmployeeRepository employeeRepository;
//
//    public List<Employee> getAllEmployees(){
//        List<Employee> employeeList = new ArrayList<Employee>();
//        System.out.println(employeeRepository.findAll());
//        //employeeRepository.findAll().forEach(employee -> employeeList.add(employee));
//        return employeeList;
//    }
//
//    public Employee getEmployeeById(int id) {
//        return employeeRepository.findById(id).get();
//    }
//
//    public void saveOrUpdate(EmployeeImpl employee) {
//        employeeRepository.save(employee);
//    }
//
//    public void delete(int id) {
//        employeeRepository.deleteById(id);
//    }
}