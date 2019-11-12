package com.example.demo.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 * TODO: Javadoc
 */
public class EmployeeMapper implements RowMapper<Employee> {
    public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
        String employeeId = rs.getString("employee_id");
        String firstName = rs.getString("first_name");
        String lastName = rs.getString("last_name");
        String address = rs.getString("address");
        int database_id = rs.getInt("database_id");

        Employee employee = new EmployeeImpl(firstName, lastName, address, employeeId);
        employee.setDatabaseId(database_id);
        return employee;
    }
}