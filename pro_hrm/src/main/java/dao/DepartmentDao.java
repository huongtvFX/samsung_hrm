package dao;

import connection.MySQLConnection;
import model.Department;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
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
    public static boolean checkPhone(String phone){
        String regex = "(84|0[2|3|5|7|8|9])+([0-9]{8})\\b";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }
}
