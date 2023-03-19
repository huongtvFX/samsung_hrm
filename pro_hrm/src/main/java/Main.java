import dao.accountDao;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        accountDao accountDao = new accountDao();
        boolean isLogin = false;
        while (true || !isLogin) {
            String n = Menu1();
            switch (n) {
                case "1":
                    String[] result = getInfo();
                    isLogin = accountDao.login(result[0], result[1]);
                    if(isLogin){
                        System.out.println("Login successful!");
                        while (isLogin){
                            String m = Menu2();
                            switch (m){
                                case "1":
                                    break;
                                case "2":
                                    isLogin = false;
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                    else{
                        System.out.println("Login unsuccessful!");
                    }
                    break;
                case "0":
                    System.exit(0);
                default:
                    break;
            }

        }
    }

    static String Menu1() {
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
    static String Menu2() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("+---------------------------+------------------------+");
        System.out.println("|                  PERSONAL MANAGEMENT               |");
        System.out.println("+---------------------------+------------------------+");
        System.out.println("| 1. Create Account                                  |");
        System.out.println("| 2. Log out                                         |");
        System.out.println("+---------------------------+------------------------+");
        System.out.print("Please choose function: ");
        return scanner.nextLine();
    }

    static String[] getInfo() {
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

    static boolean isEmail(String email){
        String regex = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"+ "samsungsds.com";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}