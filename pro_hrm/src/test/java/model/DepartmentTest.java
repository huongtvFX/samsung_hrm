package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class DepartmentTest {
    @Test
    @DisplayName("Thực hiện test hàm khởi tạo không có tham số")
    public void TestConstructorNonParameter() {
        UUID department_id = null;
        String department_name = null;
        String phone = null;
        Department department = new Department();
        Assertions.assertEquals(department.getDepartment_id(), department_id);
        Assertions.assertEquals(department.getDepartment_name(), department_name);
        Assertions.assertEquals(department.getPhone(), phone);
    }

    @Test
    @DisplayName("Thực hiện test setter")
    public void TestConstructorParameter() {
        Department department = new Department(UUID.fromString("5349bbb6-1a94-411f-b8aa-328ed73d00db"), "HCNS","0398727881");
        Assertions.assertEquals(department.getDepartment_id(), UUID.fromString("5349bbb6-1a94-411f-b8aa-328ed73d00db"));
        Assertions.assertEquals(department.getDepartment_name(), "HCNS");
        Assertions.assertEquals(department.getPhone(), "0398727881");
    }

    @Test
    @DisplayName("Thực hiện test setter")
    public void TestSetterGetter() {
        Department department = new Department();
        department.setDepartment_id(UUID.fromString("5349bbb6-1a94-411f-b8aa-328ed73d00db"));
        department.setDepartment_name("HCNS");
        department.setPhone("0398727881");
        Assertions.assertEquals(department.getDepartment_id(), UUID.fromString("5349bbb6-1a94-411f-b8aa-328ed73d00db"));
        Assertions.assertEquals(department.getDepartment_name(), "HCNS");
        Assertions.assertEquals(department.getPhone(), "0398727881");
    }

}