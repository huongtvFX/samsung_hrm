
import dao.AccountDao;
import model.Account;
import model.Department;
import service.AccountService;
import service.DepartmentService;

import javax.swing.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
                    if(accountService.ConfirmPermission(result).toLowerCase().equals("admin"))
                    {
                        while (isLogin){
                            String m = Menu2();
                            switch (m){
                                case "1":
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
                                case "0":
                                    isLogin = false;
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                    else {
                        while (isLogin){
                            String m = Menu3();
                            switch (m){
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
        System.out.println("+---------------------------+------------------------+");
        System.out.println("|                  PERSONAL MANAGEMENT               |");
        System.out.println("+---------------------------+------------------------+");
        System.out.println("| 1. Sign in                                         |");
        System.out.println("| 0. Exit                                            |");
        System.out.println("+---------------------------+------------------------+");
        System.out.print("Please choose function: ");
        return scanner.nextLine();
    }
    public static String Menu2() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("+---------------------------+------------------------+");
        System.out.println("|                  PERSONAL ADMIN                    |");
        System.out.println("+---------------------------+------------------------+");
        System.out.println("| 1. Create Account                                  |");
        System.out.println("| 2. Update Account                                  |");
        System.out.println("| 3. Delete Account                                  |");
        System.out.println("| 4. Information Account                             |");
        System.out.println("| 5. Create Department                               |");
        System.out.println("| 0. Log out                                         |");
        System.out.println("+---------------------------+------------------------+");
        System.out.print("Please choose function: ");
        return scanner.nextLine();
    }
    public static String Menu3() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("+---------------------------+------------------------+");
        System.out.println("|                  PERSONAL USER                     |");
        System.out.println("+---------------------------+------------------------+");
        System.out.println("| 1. Information Account                             |");
        System.out.println("| 0. Log out                                         |");
        System.out.println("+---------------------------+------------------------+");
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
    public static Account createAccount(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        String passwordHash = getMd5(password);
        System.out.print("Enter permission: ");
        String permission = scanner.nextLine();
        return new Account(UUID.randomUUID(), username, passwordHash, permission);
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
        System.out.printf("%-20s %-40s %-20s", "STT", "Tên tài khoản", "Quyền sử dụng");
        System.out.println();
        for (int i = 0; i < accountList.size(); i++) {
            Account account = accountList.get(i);
            System.out.printf("%-20s %-40s %-20s\n", (i + 1), account.getUsername(), account.getPermission());
        }
    }
    //endregion
    public static Department createDepartment(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập tên phòng ban: ");
        String departmentName = scanner.nextLine();
        System.out.print("Nhập số điện thoại: ");
        String phone = scanner.nextLine();
        return new Department(UUID.randomUUID(), departmentName, phone);
    }
}