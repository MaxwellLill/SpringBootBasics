package com.example.demo.repository;

import com.example.demo.data.EmployeeImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * TODO: Javadoc
 */
@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeImpl, Integer> {
}