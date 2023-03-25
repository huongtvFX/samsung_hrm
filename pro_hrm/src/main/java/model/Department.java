package model;

import java.util.UUID;

public class Department {
    private UUID department_id;
    private String department_name;
    private String phone;

    public Department() {
    }

    public Department(UUID department_id, String department_name, String phone) {
        this.department_id = department_id;
        this.department_name = department_name;
        this.phone = phone;
    }

    public UUID getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(UUID department_id) {
        this.department_id = department_id;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
