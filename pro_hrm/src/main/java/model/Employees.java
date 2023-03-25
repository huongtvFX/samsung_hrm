package model;

import java.util.UUID;

public class Employees {
    private UUID employees_id;
    private String full_name;
    private String ethnicity;
    private String gender;
    private String address;
    private String date_of_birth;
    private String phone;
    private String email;
    private UUID department_id;
    private Float salary;
    private Float income_tax;

    public Employees() {
    }

    public Employees(UUID employees_id, String full_name, String ethnicity, String gender, String address, String date_of_birth, String phone, String email, UUID department_id, Float salary, Float income_tax) {
        this.employees_id = employees_id;
        this.full_name = full_name;
        this.ethnicity = ethnicity;
        this.gender = gender;
        this.address = address;
        this.date_of_birth = date_of_birth;
        this.phone = phone;
        this.email = email;
        this.department_id = department_id;
        this.salary = salary;
        this.income_tax = income_tax;
    }

    public UUID getEmployees_id() {
        return employees_id;
    }

    public void setEmployees_id(UUID employees_id) {
        this.employees_id = employees_id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getEthnicity() {
        return ethnicity;
    }

    public void setEthnicity(String ethnicity) {
        this.ethnicity = ethnicity;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UUID getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(UUID department_id) {
        this.department_id = department_id;
    }

    public Float getSalary() {
        return salary;
    }

    public void setSalary(Float salary) {
        this.salary = salary;
    }

    public Float getIncome_tax() {
        return income_tax;
    }

    public void setIncome_tax(Float income_tax) {
        this.income_tax = income_tax;
    }
}
