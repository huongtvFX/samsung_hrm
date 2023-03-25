package dao;

import common.Constants;
import connection.Connect;
import model.Employees;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class EmployeesDao {
    public static Float CalculateIncomeTax(Float salary) {
        float incomeTax = 0;
        float taxableIncome = salary - 11000000;
        if (taxableIncome <= 0) incomeTax = 0;
        else if (taxableIncome > 0 && taxableIncome <= 5000000) {
            incomeTax = taxableIncome * 5 / 100;
        } else if (taxableIncome <= 10000000) {
            incomeTax = taxableIncome * 10 / 100;
        } else if (taxableIncome <= 18000000) {
            incomeTax = taxableIncome * 15 / 100;
        } else if (taxableIncome <= 32000000) {
            incomeTax = taxableIncome * 20 / 100;
        } else if (taxableIncome <= 52000000) {
            incomeTax = taxableIncome * 25 / 100;
        } else if (taxableIncome <= 80000000) {
            incomeTax = taxableIncome * 30 % 100;
        } else {
            incomeTax = taxableIncome * 35 % 100;
        }
        return incomeTax;
    }

    public static void create(Employees employees) {
        try {
            Connection conn = Connect.getConnection();
            String sql = Constants.EMPTY;
            if (employees.getDepartment_id() == null) {
                sql = String.format("INSERT IGNORE INTO employees VALUES ('%s','%s','%s','%s','%s','%s','%s','%s',NULL,'%f','%f')",
                        employees.getEmployees_id(), employees.getFull_name(), employees.getEthnicity(), employees.getGender(), employees.getAddress(),
                        employees.getDate_of_birth(), employees.getPhone(), employees.getEmail(), employees.getSalary(), employees.getIncome_tax());
            } else {
                sql = String.format("INSERT IGNORE INTO employees VALUES ('%s','%s','%s','%s','%s','%s','%s','%s','%s','%f','%f')",
                        employees.getEmployees_id(), employees.getFull_name(), employees.getEthnicity(), employees.getGender(), employees.getAddress(),
                        employees.getDate_of_birth(), employees.getPhone(), employees.getEmail(), employees.getDepartment_id(), employees.getSalary(), employees.getIncome_tax());
            }
            Statement state = conn.createStatement();
            int rs = state.executeUpdate(sql);
            System.out.println(rs == 0 ? Constants.CREATE_FAILED : Constants.CREATE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean EmailExists(String email) {
        boolean isExist = false;
        try {
            Connection conn = Connect.getConnection();
            String sql = String.format("SELECT * FROM employees WHERE email = '%s'", email);
            Statement state = conn.createStatement();
            ResultSet rs = state.executeQuery(sql);
            String email_DB = Constants.EMPTY;
            if (rs.next()) {
                email_DB = rs.getString("email");
            }
            isExist = email_DB.toUpperCase().equals(email.toUpperCase()) ? true : false;
            rs.close();
            state.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isExist;
    }

    public static UUID getIdByEmail(String email) {
        UUID emplyees_id = null;
        try {
            Connection conn = Connect.getConnection();
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

    public static void update(String email, String[] result) {
        try {
            UUID employees_id = getIdByEmail(email);
            Connection conn = Connect.getConnection();
            String sql = String.format("UPDATE employees SET full_name = '%s', ethnicity = '%s', gender = '%s', address = '%s', date_of_birth = '%s', phone = '%s', email = '%s', department_id = '%s', salary = '%s',income_tax = '%s' WHERE `employees_id` = '%s'",
                    result[0], result[1], result[2], result[3], result[4], result[5], result[6], result[7], result[8], result[9], employees_id);
            Statement stsm = conn.createStatement();
            int rs = stsm.executeUpdate(sql);
            System.out.println((rs == 0) ? Constants.UPDATE_FAILED : Constants.UPDATE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void delete(String email) {
        UUID employees_id = getIdByEmail(email);
        try {
            Connection conn = Connect.getConnection();
            String sql = String.format("DELETE FROM `employees` WHERE `employees_id` = '%s'", employees_id);
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

    public static List<Employees> getAll() {
        List<Employees> employeesList = new ArrayList<>();
        try {
            Connection conn = Connect.getConnection();
            String sql = "SELECT * FROM `employees`";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Employees e = new Employees();
                e.setFull_name(rs.getString("full_name"));
                e.setEthnicity(rs.getString("ethnicity"));
                e.setGender(rs.getString("gender"));
                e.setAddress(rs.getString("address"));
                e.setDate_of_birth(rs.getString("date_of_birth"));
                e.setPhone(rs.getString("phone"));
                e.setEmail(rs.getString("email"));
                if (rs.getString(Constants.DEPARTMENT_ID) == null) {
                    e.setDepartment_id(null);
                } else {
                    e.setDepartment_id(UUID.fromString(rs.getString(Constants.DEPARTMENT_ID)));
                }
                e.setSalary(Float.valueOf(rs.getString("salary")));
                e.setIncome_tax(Float.valueOf(rs.getString("income_tax")));
                employeesList.add(e);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employeesList;
    }

    public static Employees getEmployeesByEmail(String email) {
        Employees e = null;
        try {
            Connection conn = Connect.getConnection();
            Statement stsm = conn.createStatement();
            String sql = String.format("SELECT * FROM employees WHERE email = '%s'", email);
            ResultSet rs = stsm.executeQuery(sql);
            if (rs.next()) {
                e = new Employees();
                e.setEmployees_id(UUID.fromString(rs.getString("employees_id")));
                e.setFull_name(rs.getString("full_name"));
                e.setEthnicity(rs.getString("ethnicity"));
                e.setGender(rs.getString("gender"));
                e.setAddress(rs.getString("address"));
                e.setDate_of_birth(rs.getString("date_of_birth"));
                e.setPhone(rs.getString("phone"));
                e.setEmail(rs.getString("email"));
                if (rs.getString(Constants.DEPARTMENT_ID) == null) {
                    e.setDepartment_id(null);
                } else {
                    e.setDepartment_id(UUID.fromString(rs.getString(Constants.DEPARTMENT_ID)));
                }
                e.setSalary(Float.valueOf(rs.getString("salary")));
                e.setIncome_tax(Float.valueOf(rs.getString("income_tax")));
            }
            rs.close();
            stsm.close();
            conn.close();
        } catch (Exception ez) {
            ez.printStackTrace();
        }
        return e;
    }

    public static void removeEmpFromDepartment(String email) {
        Employees emp = getEmployeesByEmail(email);
        if (emp == null) {
            System.out.println("Does not exist staff want to remove!");
        } else {
            try {
                emp.setDepartment_id(null);
                Connection conn = Connect.getConnection();
                Statement state = conn.createStatement();
                String sql = String.format("UPDATE employees SET department_id = NULL WHERE `employees_id` = '%s'", emp.getEmployees_id());
                int rs = state.executeUpdate(sql);
                System.out.println((rs == 0) ? Constants.DELETE_EMP_TO_DEPARTMENT_FAILED : Constants.DELETE_EMP_TO_DEPARTMENT_SUCCESS);
                state.close();
                conn.close();
            } catch (Exception ez) {
                ez.printStackTrace();
            }
        }
    }

    public static void AddStaffToDepartment(String email) {
        Employees emp = getEmployeesByEmail(email);
        if (emp == null) {
            System.out.println("Does not exist staff want to add!");
        } else if (emp.getDepartment_id() == null) {
            System.out.println("This employee does not exist department!");
        } else {
            Scanner scanner = new Scanner(System.in);
            String department_name = DepartmentDao.GetDepartmentNameById(emp.getDepartment_id());
            List<String> dList = DepartmentDao.getListOfDepartments();
            System.out.println("The list of departments to be transferred:: ");
            int i = 1;
            for (String r : dList) {
                if (department_name.equals(r)) continue;
                else {
                    System.out.println(i + ". " + r);
                    i++;
                }
            }
            System.out.print("Please enter the name listed above: ");
            String nameDepartment = scanner.nextLine();
            UUID departmentId = DepartmentDao.getIdByName(nameDepartment);
            if (departmentId == null) {
                System.out.println("Department does not exist!");
            } else {
                emp.setDepartment_id(departmentId);
                try {
                    Connection conn = Connect.getConnection();
                    Statement state = conn.createStatement();
                    String sql = String.format("UPDATE employees SET department_id = '%s' WHERE `employees_id` = '%s'", emp.getDepartment_id(), emp.getEmployees_id());
                    int rs = state.executeUpdate(sql);
                    System.out.println((rs == 0) ? Constants.ADD_EMP_TO_DEPARTMENT_FAILED : Constants.ADD_EMP_TO_DEPARTMENT_SUCCESS);
                } catch (Exception ez) {
                    ez.printStackTrace();
                }
            }
        }
    }

    public static void ShowIncomeTax(String email) {
        Employees emp = getEmployeesByEmail(email);
        if (emp == null) {
            System.out.println("Does not exist staff!");
        } else {
            try {
                Connection conn = Connect.getConnection();
                Statement state = conn.createStatement();
                String sql = String.format("SELECT full_name, email, salary, income_tax FROM employees WHERE `employees_id` = '%s'", emp.getEmployees_id());
                ResultSet rs = state.executeQuery(sql);
                while (rs.next()) {
                    System.out.printf("%-10s %-20s %-20s %-20s %-20s ", "STT", "Full name", "Email", "Salary", "Tax");
                    System.out.println();
                    System.out.printf("%-10s %-20s %-20s %-20s %-20s \n", 1, emp.getFull_name(), emp.getEmail(), String.format("%.0f", emp.getSalary()), String.format("%.0f", emp.getIncome_tax()));
                }
                state.close();
                conn.close();
            } catch (Exception ez) {
                ez.printStackTrace();
            }
        }
    }
}
