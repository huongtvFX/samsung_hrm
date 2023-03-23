package dao;

import connection.MySQLConnection;
import model.Department;
import model.Employees;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmployeesDao {
    public static List<String> getNameDepartment() {
        List<String> departmentNameList = new ArrayList<>();
        try {
            Connection conn = MySQLConnection.getConnection();
            Statement stsm = conn.createStatement();
            String sql = "SELECT department_name FROM department";
            ResultSet rs = stsm.executeQuery(sql);
            while (rs.next()) {
                departmentNameList.add(rs.getString("department_name"));
            }
            rs.close();
            stsm.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return departmentNameList;
    }
    public static Float personalIncomeTax(Float salary) {
        if (!EmployeesDao.checkSalary(String.valueOf(salary))) {
            System.out.println("Lương nhập sai!");
        } else {
            float incomeTax = 0;
            float taxableIncome = salary - 11000000;
            if (taxableIncome > 0 && taxableIncome <= 5000000) {
                incomeTax = taxableIncome * 5 / 100;
            } else if (taxableIncome > 5000000 && taxableIncome <= 10000000) {
                incomeTax = taxableIncome * 10 / 100;
            } else if (taxableIncome > 10000000 && taxableIncome <= 18000000) {
                incomeTax = taxableIncome * 15 / 100;
            } else if (taxableIncome > 18000000 && taxableIncome <= 32000000) {
                incomeTax = taxableIncome * 20 / 100;
            } else if (taxableIncome > 32000000 && taxableIncome <= 52000000) {
                incomeTax = taxableIncome * 25 / 100;
            } else if (taxableIncome > 52000000 && taxableIncome <= 80000000) {
                incomeTax = taxableIncome * 30 % 100;
            } else {
                incomeTax = taxableIncome * 35 % 100;
            }
            return incomeTax;
        }
        return salary;
    }
    public static void createEmp(Employees employees) {
        if (!AccountDao.isEmail(employees.getEmail())) {
            System.out.println("Email không đúng định dạng!");
        }else if(EmployeesDao.isCheckEmail(employees.getEmail())){
            System.out.println("Email đã tồn tại!");
        }
        else if (!DepartmentDao.checkPhone(employees.getPhone())) {
            System.out.println("Phone không đúng định dạng!");
        } else if (!isDateOfBirth(employees.getDate_of_birth())) {
            System.out.println("Ngày sinh không đúng định dạng!");
        } else {
            try {
                Connection conn = MySQLConnection.getConnection();
                String sql = String.format("INSERT IGNORE INTO employees VALUES ('%s','%s','%s','%s','%s','%s','%s','%s','%s','%f','%f')",
                        employees.getEmployees_id(), employees.getFull_name(), employees.getEthnicity(), employees.getGender(), employees.getAddress(),
                        employees.getDate_of_birth(), employees.getPhone(), employees.getEmail(), employees.getDepartment_id(), employees.getSalary(), employees.getIncome_tax());
                Statement stsm = conn.createStatement();
                int rs = stsm.executeUpdate(sql);
                System.out.println(rs == 0 ? "Không thể tạo Nhân viên!" : "Tạo nhân viên thành công!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    static boolean isDateOfBirth(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        sdf.setLenient(false);
        boolean isCheck = false;
        try {
            Date d1 = sdf.parse(date);
            isCheck = true;
        } catch (Exception e) {
            isCheck = false;
        }
        return isCheck;
    }
    public static boolean isCheckEmail(String emailEpl) {
        boolean isEmployees = false;
        try{
            Connection conn = MySQLConnection.getConnection();
            String sql = String.format("SELECT * FROM employees WHERE email = '%s'",emailEpl);
            Statement stsm = conn.createStatement();
            ResultSet rs = stsm.executeQuery(sql);
            String nameEmail = "";
            if (rs.next()){
                nameEmail = rs.getString("email");
            }
            isEmployees = nameEmail.toUpperCase().equals(emailEpl.toUpperCase()) ? true : false;
            rs.close();
            stsm.close();
            conn.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return isEmployees;
    }
    public static UUID getIdEmp(String email){
        UUID emplyees_id = null;
        try {
            Connection conn = MySQLConnection.getConnection();
            Statement stsm = conn.createStatement();
            String sql_get_id = String.format("SELECT employees_id FROM employees WHERE email = '%s'", email);
            ResultSet rs = stsm.executeQuery(sql_get_id);
            if (rs.next()) {
                emplyees_id = UUID.fromString(rs.getString("employees_id"));
            }
            rs.close();
            stsm.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return emplyees_id;
    }
    public static void updateEmp(String[] result){
        try {
            if (!EmployeesDao.isCheckEmail(result[0])) {
                System.out.println("Không tồn tại Employees " + result[0]);
            }
            else if (!AccountDao.isEmail(result[7])) {
                System.out.println("Email không đúng định dạng!");
            }else if(EmployeesDao.isCheckEmail(result[7])){
                System.out.println("Email đã tồn tại!");
            }
            else if (!DepartmentDao.checkPhone(result[6])) {
                System.out.println("Phone không đúng định dạng!");
            } else if (!isDateOfBirth(result[5])) {
                System.out.println("Ngày sinh không đúng định dạng!");
            }
            else {
                UUID employees_id = getIdEmp(result[0]);
                Connection conn = MySQLConnection.getConnection();
                String sql = String.format("UPDATE employees SET full_name = '%s', ethnicity = '%s', gender = '%s', address = '%s', date_of_birth = '%s', phone = '%s', email = '%s', department_id = '%s', salary = '%s',income_tax = '%s' WHERE `employees_id` = '%s'",
                        result[1],result[2],result[3],result[4],result[5],result[6],result[7],result[8],result[9],result[10],employees_id);
                Statement stsm = conn.createStatement();
                int rs = stsm.executeUpdate(sql);
                System.out.println((rs == 0) ? "Cập nhật nhân viên không thành công!" : "Cập nhật nhân viên thành công!");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

    }
    static boolean checkSalary(String salary) {
        boolean ischeckSalary = false;
        float f = 0;
        if (salary.matches("^[\\p{Nd}]+[.][\\p{Nd}Ee]+[Ff]$")) {
            salary = salary.substring(0, salary.length() - 1);
            f = Float.parseFloat(salary);
        }
        if (f <= Float.MAX_VALUE) {
            return true;
        }
        return ischeckSalary;
    }

}
