package service;

import dao.AccountDao;
import dao.DepartmentDao;
import model.Account;
import model.Department;

public class DepartmentService {
    public static void create(Department department) {
        DepartmentDao.createDepartment(department);
    }
}
