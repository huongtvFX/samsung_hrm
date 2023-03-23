import dao.AccountDao;
import dao.DepartmentDao;
import dao.EmployeesDao;
import model.Account;
import model.Department;
import model.Employees;
import service.AccountService;
import service.DepartmentService;
import service.EmployeesService;

import java.text.SimpleDateFormat;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class Main {
    private static AccountService accountService = new AccountService();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean isLogin = false;
        while (true || !isLogin) {
            String n = Menu1();
            switch (n) {
                case "1":
                    String[] result = enterInfoAccount();
                    isLogin = accountService.login(result);
                    System.out.println(isLogin ? "Login successful!" : "Login unsuccessful!");
                    if (accountService.ConfirmPermission(result).toLowerCase().equals("admin")) {
                        while (isLogin) {
                            String m = Menu2();
                            switch (m) {
                                case "1" :
                                    Account account = createAccount();
                                    AccountService.create(account);
                                    break;
                                case "2":
                                    String[] info = EnterInfoUpdateAccount();
                                    AccountService.update(info);
                                    break;
                                case "3":
                                    System.out.print("Nhập username cần xóa: ");
                                    AccountService.delete(scanner.nextLine());
                                    break;
                                case "4":
                                    List<Account> lstAccount = AccountService.getAll();
                                    ShowInfoAccount(lstAccount);
                                    break;
                                case "5":
                                    Department department = createDepartment();
                                    DepartmentService.create(department);
                                    break;
                                case "6":
                                    String[] updateDepart = EnterInfoUpdateDepartment();
                                    DepartmentService.update(updateDepart);
                                    break;
                                case "7":
                                    System.out.print("Nhập department cần xóa: ");
                                    DepartmentService.delete(scanner.nextLine());
                                    break;
                                case "8":
                                    List<Department> list = DepartmentService.getAll();
                                    ShowInfoDepartment(list);
                                    break;
                                case "9":
                                    System.out.print("Nhập tên phòng ban muốn tìm: ");
                                    String name = scanner.nextLine();
                                    List<Department> lstDepartment = DepartmentService.search(name);
                                    ShowInfoDepartment(lstDepartment);
                                    break;
                                case "10":
                                    Employees employees = createEmployees();
                                    EmployeesService.create(employees);
                                    break;
                                case "11":
                                    String[] updateEmp = EnterInfoUpdateEmployees();
                                    EmployeesService.update(updateEmp);
                                    break;
                                case "0":
                                    isLogin = false;
                                    break;
                                default:
                                    break;
                            }
                        }

                    } else {
                        while (isLogin) {
                            String m = Menu3();
                            switch (m) {
                                case "1":
                                    break;
                                case "0":
                                    isLogin = false;
                                    break;
                                default:
                                    break;
                            }
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

    //region Menu related function
    public static String Menu1() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("+---------------------------+------------------------+---------------------------+");
        System.out.println("|                               PERSONAL MANAGEMENT                              |");
        System.out.println("+---------------------------+------------------------+---------------------------+");
        System.out.println("| 1. Sign in                                                                     |");
        System.out.println("| 0. Exit                                                                        |");
        System.out.println("+---------------------------+------------------------+---------------------------+");
        System.out.print("Please choose function: ");
        return scanner.nextLine();
    }

    public static String Menu2() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("+---------------------------+------------------------+---------------------------+");
        System.out.println("|                             DEPARTMENT MANAGEMENT                              |");
        System.out.println("+---------------------------+------------------------+---------------------------+");
        System.out.println("| 1. Create Account                                                              |");
        System.out.println("| 2. Update Account                                                              |");
        System.out.println("| 3. Delete Account                                                              |");
        System.out.println("| 4. Information Account                                                         |");
        System.out.println("| 5. Create Department                                                           |");
        System.out.println("| 6. Update Department                                                           |");
        System.out.println("| 7. Delete Department                                                           |");
        System.out.println("| 8. Information Department                                                      |");
        System.out.println("| 9. Search Department                                                           |");
        System.out.println("| 10. Create Employees                                                           |");
        System.out.println("| 11. Update Employees                                                           |");
        System.out.println("| 0. Exit                                                                        |");
        System.out.println("+---------------------------+------------------------+---------------------------+");
        System.out.print("Please choose function: ");
        return scanner.nextLine();
    }

    public static String Menu3() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("+---------------------------+------------------------+---------------------------+");
        System.out.println("|                  PERSONAL USER                     |");
        System.out.println("+---------------------------+------------------------+---------------------------+");
        System.out.println("| 1. Information Account                                                         |");
        System.out.println("| 0. Log out                                                                     |");
        System.out.println("+---------------------------+------------------------+---------------------------+");
        System.out.print("Please choose function: ");
        return scanner.nextLine();
    }
    //endregion

    //region Account related function
    public static String[] enterInfoAccount() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        String passwordHash = getMd5(password);
        String[] result = new String[2];
        result[0] = username;
        result[1] = passwordHash;
        return result;
    }

    public static String getMd5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            byte[] messageDigest = md.digest(input.getBytes());

            BigInteger no = new BigInteger(1, messageDigest);

            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static Account createAccount() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        String passwordHash = getMd5(password);
        return new Account(UUID.randomUUID(), username, passwordHash, "user");
    }

    public static String[] EnterInfoUpdateAccount() {
        String[] result = new String[4];
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter username: ");
        result[0] = scanner.nextLine();
        System.out.print("Enter usernameNew: ");
        result[1] = scanner.nextLine();
        System.out.print("Enter passwordNew: ");
        String passwordNew = scanner.nextLine();
        result[2] = getMd5(passwordNew);
        System.out.print("Enter permissionNew: ");
        result[3] = scanner.nextLine();
        return result;
    }

    public static void ShowInfoAccount(List<Account> accountList) {
        System.out.println("+---------------------------+------------------------+---------------------------+");
        System.out.println("|                               Information Account                              |");
        System.out.println("+---------------------------+------------------------+---------------------------+");
        System.out.printf("%-10s %-40s %-30s", "STT", "Tên tài khoản", "|", "Quyền sử dụng");
        System.out.println();
        System.out.println("+--------------------------------------------------------------------------------+");
        for (int i = 0; i < accountList.size(); i++) {
            Account account = accountList.get(i);
            System.out.printf("%-10s %-40s %-30s\n", (i + 1), account.getUsername(), account.getPermission());
        }
        System.out.println("+---------------------------+------------------------+---------------------------+");
    }

    //endregion
    public static Department createDepartment() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập tên phòng ban: ");
        String departmentName = scanner.nextLine();
        System.out.print("Nhập số điện thoại: ");
        String phone = scanner.nextLine();
        return new Department(UUID.randomUUID(), departmentName.toUpperCase(), phone);
    }

    public static String[] EnterInfoUpdateDepartment() {
        String[] result = new String[3];
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Department name: ");
        result[0] = scanner.nextLine();
        System.out.print("Enter Department nameNew: ");
        result[1] = scanner.nextLine();
        result[1] = result[1].toUpperCase();
        System.out.print("Enter phoneNew: ");
        result[2] = scanner.nextLine();
        return result;
    }

    public static void ShowInfoDepartment(List<Department> departmentList) {
        System.out.printf("%-20s %-40s %-20s", "STT", "Tên tài phòng ban", "Số điện thoại");
        System.out.println();
        for (int i = 0; i < departmentList.size(); i++) {
            Department department = departmentList.get(i);
            System.out.printf("%-20s %-40s %-20s\n", (i + 1), department.getDepartment_name(), department.getPhone());
        }
    }

    public static Employees createEmployees() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter fullName: ");
        String fullName = scanner.nextLine();
        System.out.print("Enter ethnicity: ");
        String ethnicity = scanner.nextLine();
        System.out.print("Enter gender (Nam/ Nữ/ Khác): ");
        String gender = scanner.nextLine();
        System.out.print("Enter address: ");
        String address = scanner.nextLine();
        System.out.print("Enter dateOfBirth (mm/dd/yyyy): ");
        String dateOfBirth = scanner.nextLine();
        System.out.print("Enter phone: ");
        String phone = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        List<String> result = EmployeesDao.getNameDepartment();
        System.out.println("Danh sách các phòng ban: ");
        int i = 1;
        for (String r : result) {
            System.out.println(i + ". " + r);
            i++;
        }
        System.out.print("Vui lòng nhập tên có tên trong danh sách trên: ");
        String nameDepartment = scanner.nextLine();
        UUID departmentId = DepartmentDao.getIdDepartment(nameDepartment);
        System.out.print("Enter salary: ");
        Float salary = Float.valueOf(scanner.nextLine());
        Float incomeTax = EmployeesDao.personalIncomeTax(salary);
        return new Employees(UUID.randomUUID(), fullName, ethnicity, gender, address, dateOfBirth, phone, email, departmentId, salary, incomeTax);
    }
    public static String[] EnterInfoUpdateEmployees() {
        String[] result = new String[11];
        Scanner scanner = new Scanner(System.in);
        System.out.println("Nhập email để lấy ra thông tin cần update: ");
        result[0] = scanner.nextLine();
        System.out.print("Enter fullName New: ");
        result[1] = scanner.nextLine();
        System.out.print("Enter ethnicity New: ");
        result[2] = scanner.nextLine();
        System.out.print("Enter gender (Nam/ Nữ/ Khác): ");
        result[3] = scanner.nextLine();
        System.out.print("Enter address: ");
        result[4] = scanner.nextLine();
        System.out.print("Enter dateOfBirth (mm/dd/yyyy): ");
        result[5] = scanner.nextLine();
        System.out.print("Enter phone: ");
        result[6] = scanner.nextLine();
        System.out.print("Enter email new: ");
        result[7] = scanner.nextLine();
        List<String> stringList = EmployeesDao.getNameDepartment();
        System.out.println("Danh sách các phòng ban: ");
        int i = 1;
        for (String r : stringList) {
            System.out.println(i + ". " + r);
            i++;
        }
        System.out.print("Vui lòng nhập tên có tên trong danh sách trên: ");
        String nameDepartment = scanner.nextLine();
        UUID departmentId = DepartmentDao.getIdDepartment(nameDepartment);
        result[8] = String.valueOf(departmentId);

        System.out.print("Enter salary: ");
        result[9] = String.valueOf(Float.valueOf(scanner.nextLine()));
        result[10] = String.valueOf(EmployeesDao.personalIncomeTax(Float.valueOf(result[9])));
        return result;
    }
}