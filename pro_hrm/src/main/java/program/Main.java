package program;

import common.Constants;
import dao.AccountDao;
import dao.DepartmentDao;
import dao.EmployeesDao;
import model.Account;
import model.Department;
import model.Employees;
import service.AccountService;
import service.DepartmentService;
import service.EmployeesService;

import java.util.*;

import static pre_processing.preProcess.*;

public class Main {
    private static AccountService accountService = new AccountService();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean isLogin = false;
        while (true || !isLogin) {
            String n = GeneralMenu();
            switch (n) {
                case "1":
                    Account result = EnterLogin();
                    isLogin = accountService.login(result);
                    System.out.println(isLogin ? Constants.LOGIN_SUCCESS : Constants.LOGIN_FAILED);
                    while (isLogin) {
                        if (accountService.ConfirmPermission(result).toLowerCase().equals(Constants.ADMIN)) {
                            String m = MenuAdmin();
                            switch (m) {
                                case "1":
                                    Account account = createAccount();
                                    AccountService.create(account);
                                    break;
                                case "2":
                                    System.out.print("Enter username (email) to update: ");
                                    String username = scanner.nextLine();
                                    if (!AccountDao.existAccount(username)) {
                                        System.out.println("Account does not exist!");
                                    } else {
                                        String[] info = EnterUpdateAccount();
                                        AccountService.update(username, info);
                                    }
                                    break;
                                case "3":
                                    System.out.print("Enter the account name you want to delete: ");
                                    AccountService.delete(scanner.nextLine());
                                    break;
                                case "4":
                                    List<Account> lstAccount = AccountService.getAll();
                                    ShowAccount(lstAccount);
                                    break;
                                case "5":
                                    Department department = createDepartment();
                                    DepartmentService.create(department);
                                    break;
                                case "6":
                                    System.out.print("Enter the department you want to update: ");
                                    String department_name = scanner.nextLine();

                                    if (!DepartmentDao.checkDepartmentNameBeforeUpdate(department_name)) {
                                        System.out.println("There is no department " + department_name);
                                    } else {
                                        String[] updateDepart = EnterUpdateDepartment();
                                        DepartmentService.update(department_name, updateDepart);
                                    }
                                    break;
                                case "7":
                                    System.out.print("Enter the name of the department you want to delete: ");
                                    DepartmentService.delete(scanner.nextLine());
                                    break;
                                case "8":
                                    List<Department> list = DepartmentService.getAll();
                                    ShowDepartment(list);
                                    break;
                                case "9":
                                    System.out.print("Enter the name of the department you want to search: ");
                                    String name = scanner.nextLine();
                                    if (!DepartmentDao.checkDepartmentNameBeforeUpdate(name)) {
                                        System.out.println("There is no department " + name);
                                    } else {
                                        List<Department> lstDepartment = DepartmentService.search(name);
                                        ShowDepartment(lstDepartment);
                                    }
                                    break;
                                case "10":
                                    Employees employees = createEmployees();
                                    if (EmployeesDao.EmailExists(employees.getEmail())) {
                                        System.out.println("Email already exists!");
                                    } else {
                                        EmployeesService.create(employees);
                                    }
                                    break;
                                case "11":
                                    System.out.print("Enter your email to get the information you need to update: ");
                                    String emailUpdate = scanner.nextLine();
                                    if (!EmployeesDao.EmailExists(emailUpdate)) {
                                        System.out.println("No employee " + emailUpdate);
                                    } else {
                                        String[] updateEmp = EnterUpdateEmployees();
                                        EmployeesService.update(emailUpdate, updateEmp);
                                    }
                                    break;
                                case "12":
                                    System.out.print("Enter email to delete employee: ");
                                    String email = scanner.nextLine();
                                    if (!EmployeesDao.EmailExists(email)) {
                                        System.out.println("There is no employee with email " + email);
                                    } else {
                                        EmployeesService.delete(email);
                                    }
                                    break;
                                case "13":
                                    List<Employees> listEmp = EmployeesService.getAll();
                                    ShowEmployees(listEmp);
                                    break;
                                case "14":
                                    System.out.print("Enter email to search for employees: ");
                                    String emailSearch = scanner.nextLine();
                                    if (!EmployeesDao.EmailExists(emailSearch)) {
                                        System.out.println("There is no employee with email " + emailSearch);
                                    } else {
                                        Employees e = EmployeesService.getEmployeesByEmail(emailSearch);
                                        List<Employees> employeesList = new ArrayList<>();
                                        employeesList.add(e);
                                        ShowEmployees(employeesList);
                                    }
                                    break;
                                case "15":
                                    System.out.print("Enter the employee's email you want to remove from the department:");
                                    String emailDel = scanner.nextLine();
                                    EmployeesService.removeEmpFromDepartment(emailDel);
                                    break;
                                case "16":
                                    System.out.print("Enter the employee's email you want to add to the department: ");
                                    String emailAdd = scanner.nextLine();
                                    EmployeesService.AddStaffToDepartment(emailAdd);
                                    break;
                                case "17":
                                    System.out.print("Enter the email of the employee who wants to calculate personal income tax: ");
                                    String emailEmp = scanner.nextLine();
                                    EmployeesService.ShowIncomeTax(emailEmp);
                                    break;
                                case "0":
                                    isLogin = false;
                                    break;
                                default:
                                    break;
                            }
                        } else {
                            System.out.println("No system access!");
                            System.exit(0);
                        }
                    }
                    break;
                case "0":
                    System.exit(0);
                default:
                    break;
            }
        }
    }
}