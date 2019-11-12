package com.example.demo.data;

/**
 * TODO: Javadoc
 */
public interface Person {
    String getFirstName();

    String getLastName();

    String getAddress();

    int getDatabaseId();

    void setDatabaseId(int databaseId);

    void setFirstName(String firstName);

    void setLastName(String lastName);

    void setAddress(String address);

    String getFullName();
}
