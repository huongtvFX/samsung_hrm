package dao;

import connection.MySQLConnection;
import model.Account;
import model.Department;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DepartmentDao {
    public static boolean checkDepartmentName(String departmentNameCompare){
        boolean check = false;
        try {
            Connection conn = MySQLConnection.getConnection();
            String sql = String.format("SELECT department_name FROM department");
            Statement stsm = conn.createStatement();
            ResultSet rs = stsm.executeQuery(sql);
            String department_name = "";
            while (rs.next()){
                department_name = rs.getString("department_name");
                if(department_name.equals(departmentNameCompare)){
                    check = true;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  check;
    }
    public static void createDepartment(Department department) {
        try {
            if(checkDepartmentName(department.getDepartment_name())){
                System.out.println("Tên phòng ban đã tồn tại!");
            }
            else if(!checkPhone(department.getPhone())){
                System.out.println("Số điện thoại không đúng định dạng!");
            }
            else{
                Connection conn = MySQLConnection.getConnection();
                String sql = String.format("INSERT IGNORE INTO department VALUES ('%s','%s','%s')",
                        department.getDepartment_id(), department.getDepartment_name(),department.getPhone());
                Statement stsm = conn.createStatement();
                int rs = stsm.executeUpdate(sql);
                System.out.println(rs == 0 ? "Không thể tạo phòng ban!" : "Tạo phòng ban thành công!");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static boolean isCheckDepartment(String departmentName) {
        boolean isDepartment = false;
        try{
            Connection conn = MySQLConnection.getConnection();
            String sql = String.format("SELECT * FROM department WHERE department_name = '%s'",departmentName);
            Statement stsm = conn.createStatement();
            ResultSet rs = stsm.executeQuery(sql);
            String name = "";
            if (rs.next()){
                name = rs.getString("department_name");
            }
            isDepartment = name.toUpperCase().equals(departmentName.toUpperCase()) ? true : false;
            rs.close();
            stsm.close();
            conn.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return isDepartment;
    }
    public static UUID getIdDepartment(String departmentName){
        UUID department_id = null;
        try {
            Connection conn = MySQLConnection.getConnection();
            Statement stsm = conn.createStatement();
            String sql_get_id = String.format("SELECT department_id FROM department WHERE department_name = '%s'", departmentName);
            ResultSet rs = stsm.executeQuery(sql_get_id);

            if (rs.next()) {
                department_id = UUID.fromString(rs.getString("department_id"));
            }
            rs.close();
            stsm.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return department_id;
    }
    public static void updateDepartment(String result[]){
        try {
            if (!DepartmentDao.isCheckDepartment(result[0])) {
                System.out.println("Không tồn tại Department " + result[0]);
            }
            else {
                UUID department_id = getIdDepartment(result[0]);
                Connection conn = MySQLConnection.getConnection();
                String sql = String.format("UPDATE department SET department_name = '%s', phone = '%s' WHERE `department_id` = '%s'",
                        result[1],result[2],department_id);
                Statement stsm = conn.createStatement();
                int rs = stsm.executeUpdate(sql);
                System.out.println((rs == 0) ? "Cập nhật phòng ban không thành công!" : "Cập nhật phòng ban thành công!");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static void deleteDepartment(String departmentName){
        UUID department_id = getIdDepartment(departmentName);
        try {
            Connection conn = MySQLConnection.getConnection();
            String sql = String.format("DELETE FROM `department` WHERE `department_id` = '%s'", department_id);;
            Statement stsm = conn.createStatement();
            int rs = stsm.executeUpdate(sql);
            System.out.println((rs == 0) ? "Delete unsuccessful!" : "Delete successful!");
            stsm.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static List<Department> getAllDepartment() {
            List<Department> departmentList = new ArrayList<>();
        try {
            Connection conn = MySQLConnection.getConnection();
            String sql = "SELECT * FROM `department`";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Department department = new Department();
                department.setDepartment_name(rs.getString("department_name"));
                department.setPhone(rs.getString("phone"));
                departmentList.add(department);
            }
            rs.close();
            stmt.close();
            conn.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return departmentList;
    }
    public static List<Department> searchDepartment(String departmentName) {
        List<Department> list = new ArrayList<>();
        try {
            Connection conn = MySQLConnection.getConnection();
            String sql = "SELECT * FROM `department`";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String name = rs.getString("department_name");
                if(name.toLowerCase().contains(departmentName.toLowerCase())){
                    Department department = new Department();
                    department.setDepartment_name(rs.getString("department_name"));
                    department.setPhone(rs.getString("phone"));
                    list.add(department);
                }
            }
            rs.close();
            stmt.close();
            conn.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public static boolean checkPhone(String phone){
        String regex = "(84|0[2|3|5|7|8|9])+([0-9]{8})\\b";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

}
