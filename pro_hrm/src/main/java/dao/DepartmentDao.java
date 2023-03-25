package dao;

import common.Constants;
import connection.Connect;
import model.Department;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DepartmentDao {
    public static boolean existDepartment(String departmentNameCompare) {
        boolean check = false;
        try {
            Connection conn = Connect.getConnection();
            String sql = String.format("SELECT department_name FROM department");
            Statement stsm = conn.createStatement();
            ResultSet rs = stsm.executeQuery(sql);
            String department_name = "";
            while (rs.next()) {
                department_name = rs.getString(Constants.DEPARTMENT_NAME);
                if (department_name.equals(departmentNameCompare)) {
                    check = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check;
    }

    public static void create(Department department) {
        try {
            if (existDepartment(department.getDepartment_name())) {
                System.out.println("Department name already exists!");
            } else if (!Constants.isPhone(department.getPhone())) {
                System.out.println("Invalid phone number!");
            } else {
                Connection conn = Connect.getConnection();
                String sql = String.format("INSERT IGNORE INTO department VALUES ('%s','%s','%s')",
                        department.getDepartment_id(), department.getDepartment_name(), department.getPhone());
                Statement stsm = conn.createStatement();
                int rs = stsm.executeUpdate(sql);
                System.out.println(rs == 0 ? Constants.CREATE_FAILED : Constants.CREATE_SUCCESS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean checkDepartmentNameBeforeUpdate(String departmentName) {
        boolean isDepartment = false;
        try {
            Connection conn = Connect.getConnection();
            String sql = String.format("SELECT * FROM department WHERE department_name = '%s'", departmentName);
            Statement stsm = conn.createStatement();
            ResultSet rs = stsm.executeQuery(sql);
            String name = Constants.EMPTY;
            if (rs.next()) {
                name = rs.getString(Constants.DEPARTMENT_NAME);
            }
            isDepartment = name.toUpperCase().equals(departmentName.toUpperCase()) ? true : false;
            rs.close();
            stsm.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isDepartment;
    }

    public static UUID getIdByName(String departmentName) {
        UUID department_id = null;
        try {
            Connection conn = Connect.getConnection();
            Statement state = conn.createStatement();
            String sql_get_id = String.format("SELECT department_id FROM department WHERE department_name = '%s'", departmentName);
            ResultSet rs = state.executeQuery(sql_get_id);

            if (rs.next()) {
                department_id = UUID.fromString(rs.getString(Constants.DEPARTMENT_ID));
            }
            rs.close();
            state.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return department_id;
    }

    public static void update(String name, String result[]) {
        try {
            UUID department_id = getIdByName(name);
            Connection conn = Connect.getConnection();
            String sql = String.format("UPDATE department SET department_name = '%s', phone = '%s' WHERE `department_id` = '%s'",
                    result[0], result[1], department_id);
            Statement stsm = conn.createStatement();
            int rs = stsm.executeUpdate(sql);
            System.out.println((rs == 0) ? Constants.UPDATE_FAILED : Constants.UPDATE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void delete(String departmentName) {
        UUID department_id = getIdByName(departmentName);
        try {
            Connection conn = Connect.getConnection();
            String sql = String.format("DELETE FROM `department` WHERE `department_id` = '%s'", department_id);
            ;
            Statement stsm = conn.createStatement();
            int rs = stsm.executeUpdate(sql);
            System.out.println((rs == 0) ? Constants.DELETE_FAILED : Constants.DELETE_SUCCESS);
            stsm.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Department> getAll() {
        List<Department> departmentList = new ArrayList<>();
        try {
            Connection conn = Connect.getConnection();
            String sql = "SELECT * FROM `department`";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Department department = new Department();
                department.setDepartment_name(rs.getString(Constants.DEPARTMENT_NAME));
                department.setPhone(rs.getString("phone"));
                departmentList.add(department);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return departmentList;
    }

    public static List<Department> search(String departmentName) {
        List<Department> list = new ArrayList<>();
        try {
            Connection conn = Connect.getConnection();
            String sql = "SELECT * FROM `department`";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String name = rs.getString(Constants.DEPARTMENT_NAME);
                if (name.toLowerCase().contains(departmentName.toLowerCase())) {
                    Department department = new Department();
                    department.setDepartment_name(rs.getString(Constants.DEPARTMENT_NAME));
                    department.setPhone(rs.getString("phone"));
                    list.add(department);
                }
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<String> getListOfDepartments() {
        List<String> departmentNameList = new ArrayList<>();
        try {
            Connection conn = Connect.getConnection();
            Statement state = conn.createStatement();
            String sql = "SELECT department_name FROM department";
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                departmentNameList.add(rs.getString(Constants.DEPARTMENT_NAME));
            }
            rs.close();
            state.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return departmentNameList;
    }

    public static String GetDepartmentNameById(UUID department_id) {
        String department_name = Constants.EMPTY;
        try {
            Connection conn = Connect.getConnection();
            String sql = String.format("SELECT department_name FROM `department` WHERE `department_id` = '%s'", department_id);
            ;
            Statement state = conn.createStatement();
            ResultSet rs = state.executeQuery(sql);
            if (rs.next()) {
                department_name = rs.getString(Constants.DEPARTMENT_NAME);
            }
            rs.close();
            state.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return department_name;
    }
}
