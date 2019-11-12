package com.example.demo.data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

/**
 * TODO: Javadoc
 */
//@Entity
//@Table(name = "people")
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@MappedSuperclass
public class PersonImpl implements Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int databaseId;

    protected String firstName;
    protected String lastName;
    protected String address;

    public PersonImpl(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    @Override
    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    @Override
    public int getDatabaseId() {
        return databaseId;
    }

    @Override
    public void setDatabaseId(int databaseId) {
        this.databaseId = databaseId;
    }

    @Override
    public String getFullName(){
        return firstName + " " + lastName;
    }

}