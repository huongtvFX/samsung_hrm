package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class EmployeesTest {
    @Test
    @DisplayName("Thực hiện test hàm khởi tạo không có tham số")
    public void TestConstructorNonParameter() {
        Employees emp = new Employees();
        Assertions.assertEquals(emp.getEmployees_id(), null);
        Assertions.assertEquals(emp.getFull_name(), null);
        Assertions.assertEquals(emp.getEthnicity(), null);
        Assertions.assertEquals(emp.getGender(), null);
        Assertions.assertEquals(emp.getAddress(), null);
        Assertions.assertEquals(emp.getDate_of_birth(), null);
        Assertions.assertEquals(emp.getPhone(), null);
        Assertions.assertEquals(emp.getEmail(), null);
        Assertions.assertEquals(emp.getDepartment_id(), null);
        Assertions.assertEquals(emp.getSalary(), null);
        Assertions.assertEquals(emp.getIncome_tax(), null);
    }

    @Test
    @DisplayName("Thực hiện test hàm khởi tạo có tham số")
    public void TestConstructorParameter() {
        Employees emp = new Employees(UUID.fromString("74f5c2f1-1146-438f-aa0c-769d49cf62e1"), "Trần Văn Hướng", "Kinh", "Nam", "Hà Nội", "02/13/2000", "0399029938", "tvh@samsungsds.com", null, 15000000F, 200000F);
        Assertions.assertEquals(emp.getEmployees_id(), UUID.fromString("74f5c2f1-1146-438f-aa0c-769d49cf62e1"));
        Assertions.assertEquals(emp.getFull_name(), "Trần Văn Hướng");
        Assertions.assertEquals(emp.getEthnicity(), "Kinh");
        Assertions.assertEquals(emp.getGender(), "Nam");
        Assertions.assertEquals(emp.getAddress(), "Hà Nội");
        Assertions.assertEquals(emp.getDate_of_birth(), "02/13/2000");
        Assertions.assertEquals(emp.getPhone(), "0399029938");
        Assertions.assertEquals(emp.getEmail(), "tvh@samsungsds.com");
        Assertions.assertEquals(emp.getDepartment_id(), null);
        Assertions.assertEquals(emp.getSalary(), 15000000F);
        Assertions.assertEquals(emp.getIncome_tax(), 200000F);
    }

    @Test
    @DisplayName("Thực hiện test getter, setter")
    public void TestGetSet() {
        Employees emp = new Employees();

        emp.setEmployees_id(UUID.fromString("74f5c2f1-1146-438f-aa0c-769d49cf62e1"));
        emp.setFull_name("Trần Văn Hướng");
        emp.setEthnicity("Kinh");
        emp.setGender("Nam");
        emp.setAddress("Hà Nội");
        emp.setDate_of_birth("02/13/2000");
        emp.setPhone("0399029938");
        emp.setEmail("tvh@samsungsds.com");
        emp.setDepartment_id(null);
        emp.setSalary(15000000F);
        emp.setIncome_tax(200000F);

        Assertions.assertEquals(emp.getEmployees_id(), UUID.fromString("74f5c2f1-1146-438f-aa0c-769d49cf62e1"));
        Assertions.assertEquals(emp.getFull_name(), "Trần Văn Hướng");
        Assertions.assertEquals(emp.getEthnicity(), "Kinh");
        Assertions.assertEquals(emp.getGender(), "Nam");
        Assertions.assertEquals(emp.getAddress(), "Hà Nội");
        Assertions.assertEquals(emp.getDate_of_birth(), "02/13/2000");
        Assertions.assertEquals(emp.getPhone(), "0399029938");
        Assertions.assertEquals(emp.getEmail(), "tvh@samsungsds.com");
        Assertions.assertEquals(emp.getDepartment_id(), null);
        Assertions.assertEquals(emp.getSalary(), 15000000F);
        Assertions.assertEquals(emp.getIncome_tax(), 200000F);
    }
}