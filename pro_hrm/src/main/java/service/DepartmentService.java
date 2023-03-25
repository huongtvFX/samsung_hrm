package service;

import dao.DepartmentDao;
import model.Department;

import java.util.List;

public class DepartmentService {
    public static void create(Department department) {
        DepartmentDao.create(department);
    }

    public static void update(String name, String[] updateDepart) {
        DepartmentDao.update(name, updateDepart);
    }

    public static void delete(String departmentName) {
        DepartmentDao.delete(departmentName);
    }

    public static List<Department> getAll() {
        List<Department> list = DepartmentDao.getAll();
        return list;
    }

    public static List<Department> search(String departmentName) {
        List<Department> departmentList = DepartmentDao.search(departmentName);
        return departmentList;
    }
}
