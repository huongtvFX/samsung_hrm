package service;

import dao.AccountDao;
import dao.DepartmentDao;
import model.Account;
import model.Department;

import java.util.List;

public class DepartmentService {
    public static void create(Department department) {
        DepartmentDao.createDepartment(department);
    }
    public static void update(String[]updateDepart) {
        DepartmentDao.updateDepartment(updateDepart);
    }
    public static void delete(String departmentName){
        DepartmentDao.deleteDepartment(departmentName);
    }
    public static List<Department> getAll(){
        List<Department> list = DepartmentDao.getAllDepartment();
        return list;
    }
    public static List<Department> search(String departmentName){
        List<Department> departmentList = DepartmentDao.searchDepartment(departmentName);
        return departmentList;
    }
}
