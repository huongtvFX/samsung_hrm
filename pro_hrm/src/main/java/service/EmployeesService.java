package service;

import dao.EmployeesDao;
import model.Employees;

import java.util.List;

public class EmployeesService {
    public static void create(Employees employees) {
        EmployeesDao.create(employees);
    }

    public static void update(String email, String[] updateEmp) {
        EmployeesDao.update(email, updateEmp);
    }

    public static void delete(String email) {
        EmployeesDao.delete(email);
    }

    public static List<Employees> getAll() {
        List<Employees> list = EmployeesDao.getAll();
        return list;
    }

    public static Employees getEmployeesByEmail(String email) {
        Employees e = EmployeesDao.getEmployeesByEmail(email);
        return e;
    }

    public static void removeEmpFromDepartment(String email) {
        EmployeesDao.removeEmpFromDepartment(email);
    }

    public static void AddStaffToDepartment(String email) {
        EmployeesDao.AddStaffToDepartment(email);
    }

    public static void ShowIncomeTax(String email) {
        EmployeesDao.ShowIncomeTax(email);
    }
}
