package pre_processing;

import common.Constants;
import dao.DepartmentDao;
import dao.EmployeesDao;
import model.Account;
import model.Department;
import model.Employees;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class preProcess {
    public static String GeneralMenu() {
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

    public static String MenuAdmin() {
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
        System.out.println("| 12. Delete Employees                                                           |");
        System.out.println("| 13. Information Employees                                                      |");
        System.out.println("| 14. Search Employees By Email                                                  |");
        System.out.println("| 15. Remove employee from department                                            |");
        System.out.println("| 16. Switching departments to an employee                                       |");
        System.out.println("| 17. Calculate personal income tax for an employee                              |");
        System.out.println("| 0. Logout                                                                      |");
        System.out.println("+---------------------------+------------------------+---------------------------+");
        System.out.print("Please choose function: ");
        return scanner.nextLine();
    }

    public static Account EnterLogin() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        String passwordHash = getMd5(password);
        return new Account(username, passwordHash);
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

    public static String[] EnterUpdateAccount() {
        String[] result = new String[3];
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter username new: ");
        result[0] = scanner.nextLine();
        System.out.print("Enter password new: ");
        String passwordNew = scanner.nextLine();
        result[1] = getMd5(passwordNew);
        System.out.print("Enter permission new: ");
        result[2] = scanner.nextLine();
        return result;
    }

    public static void ShowAccount(List<Account> accountList) {
        System.out.println("+---------------------------+------------------------+---------------------------+");
        System.out.println("|                               Information Account                              |");
        System.out.println("+---------------------------+------------------------+---------------------------+");
        System.out.printf("%-10s %-40s %-30s", "STT", "Account name", "|", "Permission");
        System.out.println();
        System.out.println("+--------------------------------------------------------------------------------+");
        for (int i = 0; i < accountList.size(); i++) {
            Account account = accountList.get(i);
            System.out.printf("%-10s %-40s %-30s\n", (i + 1), account.getUsername(), account.getPermission());
        }
        System.out.println("+---------------------------+------------------------+---------------------------+");
    }

    public static Department createDepartment() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter department name: ");
        String departmentName = scanner.nextLine();
        System.out.print("Enter phone number: ");
        String phone = scanner.nextLine();
        return new Department(UUID.randomUUID(), departmentName.toUpperCase(), phone);
    }

    public static String[] EnterUpdateDepartment() {
        String[] result = new String[2];
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the new department name: ");
        result[0] = scanner.nextLine();
        result[0] = result[0].toUpperCase();
        System.out.print("Enter new phone number: ");
        result[1] = scanner.nextLine();
        return result;
    }

    public static void ShowDepartment(List<Department> DList) {
        System.out.printf("%-20s %-40s %-20s", "STT", "Department name", "Phone number");
        System.out.println();
        for (int i = 0; i < DList.size(); i++) {
            Department department = DList.get(i);
            System.out.printf("%-20s %-40s %-20s\n", (i + 1), department.getDepartment_name(), department.getPhone());
        }
    }

    public static Employees createEmployees() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter fullName: ");
        String fullName = scanner.nextLine();
        System.out.print("Enter ethnicity: ");
        String ethnicity = scanner.nextLine();
        System.out.print("Enter gender (Male/ Female/ Other): ");
        String gender = scanner.nextLine();
        System.out.print("Enter address: ");
        String address = scanner.nextLine();
        System.out.print("Enter dateOfBirth (dd/MM/yyyy): ");
        String dateOfBirth = scanner.nextLine();
        boolean isDate = Constants.isDateOfBirth(dateOfBirth);
        while (!isDate) {
            System.out.print("Enter again dateOfBirth (dd/MM/yyyy): ");
            dateOfBirth = scanner.nextLine();
            if (Constants.isDateOfBirth(dateOfBirth)) {
                break;
            } else {
                System.out.println("Do not use the date of birth format!");
            }
        }
        System.out.print("Enter phone: ");
        String phone = scanner.nextLine();
        boolean isPhone = Constants.isPhone(phone);
        while (!isPhone) {
            System.out.print("Enter again phone: ");
            phone = scanner.nextLine();
            if (Constants.isPhone(phone)) {
                break;
            } else {
                System.out.println("Do not use the phone format!");
            }
        }
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        boolean isMail = Constants.isEmail(email);
        while (!isMail) {
            System.out.print("Enter again email: ");
            email = scanner.nextLine();
            if (Constants.isEmail(email)) {
                break;
            } else {
                System.out.println("Do not use the email format!");
            }
        }
        System.out.print("Would you like to enter a list of departments for this employee? (Yes/No): ");
        UUID departmentId = null;
        if (scanner.nextLine().toLowerCase().equals("yes")) {
            List<String> result = DepartmentDao.getListOfDepartments();
            System.out.println("List of departments: ");
            int i = 1;
            for (String r : result) {
                System.out.println(i + ". " + r);
                i++;
            }
            System.out.print("Please enter the name listed above: ");
            String nameDepartment = scanner.nextLine();
            departmentId = DepartmentDao.getIdByName(nameDepartment);
        }

        Float salary = (float) 0;
        while (salary == (float) 0) {
            System.out.print("Enter salary: ");
            String s = scanner.nextLine();
            try {
                salary = Float.valueOf(s);
            } catch (Exception e) {
                System.out.println("Salary incorrect data type is float!");
            }
        }
        Float incomeTax = EmployeesDao.CalculateIncomeTax(salary);
        return new Employees(UUID.randomUUID(), fullName, ethnicity, gender, address, dateOfBirth, phone, email, departmentId, salary, incomeTax);
    }

    public static String[] EnterUpdateEmployees() {
        String[] result = new String[10];
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter fullName new: ");
        result[0] = scanner.nextLine();
        System.out.print("Enter ethnicity new: ");
        result[1] = scanner.nextLine();
        System.out.print("Enter gender (Male/ Female/ Other): ");
        result[2] = scanner.nextLine();
        System.out.print("Enter address new: ");
        result[3] = scanner.nextLine();
        System.out.print("Enter dateOfBirth (mm/dd/yyyy): ");
        result[4] = scanner.nextLine();
        boolean isDate = Constants.isDateOfBirth(result[5]);
        while (!isDate) {
            System.out.print("Enter again dateOfBirth (dd/MM/yyyy): ");
            result[4] = scanner.nextLine();
            if (Constants.isDateOfBirth(result[4])) {
                break;
            } else {
                System.out.println("Do not use the date of birth format!");
            }
        }
        System.out.print("Enter phone new: ");
        result[5] = scanner.nextLine();
        boolean isPhone = Constants.isPhone(result[5]);
        while (!isPhone) {
            System.out.print("Enter again phone: ");
            result[5] = scanner.nextLine();
            if (Constants.isPhone(result[5])) {
                break;
            } else {
                System.out.println("Do not use the phone format!");
            }
        }
        System.out.print("Enter email new: ");
        result[6] = scanner.nextLine();
        boolean isMail = Constants.isEmail(result[6]);
        while (!isMail) {
            System.out.print("Enter again email: ");
            result[6] = scanner.nextLine();
            if (Constants.isEmail(result[6])) {
                break;
            } else {
                System.out.println("Do not use the email format!");
            }
        }

        List<String> dList = DepartmentDao.getListOfDepartments();
        System.out.println("List of departments: ");
        int i = 1;
        for (String r : dList) {
            System.out.println(i + ". " + r);
            i++;
        }
        System.out.print("Please enter the name listed above: ");
        String nameDepartment = scanner.nextLine();
        UUID departmentId = DepartmentDao.getIdByName(nameDepartment);
        result[7] = String.valueOf(departmentId);

        Float salary = (float) 0;
        result[8] = String.valueOf(salary);
        while (salary == (float) 0) {
            System.out.print("Enter salary: ");
            String s = scanner.nextLine();
            try {
                salary = Float.valueOf(s);
                result[8] = String.valueOf(salary);
            } catch (Exception e) {
                System.out.println("Salary incorrect data type is float!");
            }
        }
        result[9] = String.valueOf(EmployeesDao.CalculateIncomeTax(Float.valueOf(result[9])));
        return result;
    }

    public static void ShowEmployees(List<Employees> eList) {
        System.out.printf("%-10s %-20s %-20s %-20s %-20s %-20s %-20s %-30s %-20s %-20s %-20s", "STT", "Full name", "Ethnic", "Gender", "Address",
                "DateOfBirth", "Phone", "Email", "Department", "Salary", "Tax");
        System.out.println();
        for (int i = 0; i < eList.size(); i++) {
            Employees emp = eList.get(i);
            String department_name = DepartmentDao.GetDepartmentNameById(emp.getDepartment_id());
            System.out.printf("%-10s %-20s %-20s %-20s %-20s %-20s %-20s %-30s %-20s %-20s %-20s\n", (i + 1), emp.getFull_name(), emp.getEthnicity(), emp.getGender(),
                    emp.getAddress(), emp.getDate_of_birth(), emp.getPhone(), emp.getEmail(), department_name, String.format("%.0f", emp.getSalary()),
                    String.format("%.0f", emp.getIncome_tax()));
        }
    }

}
