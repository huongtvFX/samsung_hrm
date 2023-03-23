package service;

import dao.DepartmentDao;
import dao.EmployeesDao;
import model.Department;
import model.Employees;

public class EmployeesService {
    public static void create(Employees employees) {
        EmployeesDao.createEmp(employees);
    }
    public static void update(String[]updateEmp) {
        EmployeesDao.updateEmp(updateEmp);
    }
}
