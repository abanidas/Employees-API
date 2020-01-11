package com.abani.rest.employees.model;

public class DummyEmployee {
    private String email;
    private String name;
    private String designation;
    private String phone;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Employee parseEmployee(){
        Employee employee = new Employee();
        employee.setEmail(email);
        employee.setDesignation(designation);
        employee.setName(name);
        employee.setPhone(phone);
        return employee;
    }
}
