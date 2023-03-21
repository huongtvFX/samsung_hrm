//region Thư viện cần import
package dao;

import connection.MySQLConnection;
import model.Account;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//endregion

public class AccountDao {

    //region lấy password theo username được nhập vào
    public static String getPasswordHash(String[] infoAccount) {
        String pass = "";
        try {
            Connection conn = MySQLConnection.getConnection();
            String sql = String.format("SELECT password_hash FROM account WHERE BINARY username = '%s'", infoAccount[0]);
            Statement stmt = conn.createStatement();
            ResultSet rSet = stmt.executeQuery(sql);

            while (rSet.next()) {
                pass = rSet.getString("password_hash");
            }
            rSet.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return pass;
    }
    //endregion

    //region get info permission
    public static String getPermission(String[] infoAccount) {
        String permission = "";
        try {
            Connection conn = MySQLConnection.getConnection();
            String sql = String.format("SELECT `permission` FROM `account` WHERE `username` = '%s' AND `password_hash` = '%s'",
                    infoAccount[0], infoAccount[1]);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                permission = rs.getString("permission");
            }
            rs.close();
            stmt.close();
            conn.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return permission;
    }
    //endregion

    //region check email and create account
    public static boolean isEmail(String email){
        String regex = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"+ "samsungsds.com";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    public static void checkBeforeCreate(Account account) {
        try {
            if(!isEmail(account.getUsername())){
                System.out.println("Username not email format!");
                System.out.println("Account creation failed!");
            }
            else{
                Connection conn = MySQLConnection.getConnection();
                String sql = String.format("INSERT IGNORE INTO account VALUES ('%s','%s','%s','%s')",
                        account.getAccount_id(), account.getUsername(), account.getPassword(), account.getPermission());
                Statement stsm = conn.createStatement();
                int rs = stsm.executeUpdate(sql);
                System.out.println((rs == 0) ? "Create unsuccessful" : "Create successful");
                stsm.close();
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //endregion

    //region check account
    public static boolean isExistAccount(String username) {
        boolean isAccount = false;
        try {
            Connection conn = MySQLConnection.getConnection();
            String sql =  String.format("SELECT * FROM account WHERE username = '%s'", username);
            Statement state = conn.createStatement();
            ResultSet rs = state.executeQuery(sql);
            String name = "";
            if (rs.next()) {
                name = rs.getString("username");
            }
            isAccount = name.equals(username) ? true : false;
            rs.close();
            state.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isAccount;
    }
    //endregion

    //region update account
    public static void update(String[] result) {
        try {
            if(!AccountDao.isExistAccount(result[0])){
                System.out.println("Tài khoản không tồn tại!");
            }
            else{
                String account_id = getId(result[0]);
                Connection conn = MySQLConnection.getConnection();
                Statement stsm = conn.createStatement();
                String sql = String.format("UPDATE account SET username = '%s', password_hash = '%s', permission = '%s' WHERE `account_id` = '%s'",
                        result[1], result[2], result[3], account_id);
                int rs = stsm.executeUpdate(sql);
                System.out.println((rs == 0) ? "Cập nhật không thành công!" : "Cập nhật thành công!");
                stsm.close();
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //endregion

    //region lấy ra account id theo username
    public static String getId(String username){
        String account_id = "";
        try {
            Connection conn = MySQLConnection.getConnection();
            Statement stsm = conn.createStatement();
            String sql_get_id = String.format("SELECT account_id FROM account WHERE username = '%s'", username);
            ResultSet rSet = stsm.executeQuery(sql_get_id);

            if (rSet.next()) {
                account_id = rSet.getString("account_id");
            }
            rSet.close();
            stsm.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return account_id;
    }
    //endregion

    //region Delete account
    public static void deleteAccount(String username){
        if(username.equals("TranVanHuongSDS@gmail.com")){
            System.out.println("Không thể xóa tài khoản admin!");
        }
        else{
            String account_id = getId(username);
            try {
                Connection conn = MySQLConnection.getConnection();
                String sql = String.format("DELETE FROM `account` WHERE account_id = '%s'", account_id);;
                Statement stsm = conn.createStatement();
                int rs = stsm.executeUpdate(sql);
                System.out.println((rs == 0) ? "Delete unsuccessful!" : "Delete successful!");
                stsm.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    //endregion

    //region get all info
    public static List<Account> getAllAccount() {
        List<Account> accountList = new ArrayList<>();
        try {
            Connection conn = MySQLConnection.getConnection();
            String sql = "SELECT * FROM `account`";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Account account = new Account();
                account.setUsername(rs.getString("username"));
                account.setPermission(rs.getString("permission"));
                accountList.add(account);
            }
            rs.close();
            stmt.close();
            conn.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return accountList;
    }
    //endregion
}