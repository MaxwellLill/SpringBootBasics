package com.example.demo.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * TODO: Javadoc
 * This is just a Basic Bi**h class. Was used so that i can test database Primary Keys with @GeneratedValue.
 * As the behaviour i was experiencing wasn't what i expected and it was to do with Inheritance.
 */
@Entity
public class databaseTest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int databaseId;
    private String name;

    public int getDatabaseId() {
        return databaseId;
    }

    public void setDatabaseId(int databaseId) {
        this.databaseId = databaseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}