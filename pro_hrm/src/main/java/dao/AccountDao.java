package dao;

import common.Constants;
import connection.Connect;
import model.Account;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AccountDao {
    public static String getPasswordHash(Account account) {
        String password_hash = Constants.EMPTY;
        try {
            Connection conn = Connect.getConnection();
            String sql = String.format("SELECT password_hash FROM account WHERE BINARY username = '%s'", account.getUsername());
            Statement state = conn.createStatement();
            ResultSet rSet = state.executeQuery(sql);
            while (rSet.next()) {
                password_hash = rSet.getString(Constants.PASSWORD_HASH);
            }
            rSet.close();
            state.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return password_hash;
    }

    public static String getPermission(Account account) {
        String permission = Constants.EMPTY;
        try {
            Connection conn = Connect.getConnection();
            String sql = String.format("SELECT `permission` FROM `account` WHERE `username` = '%s' AND `password_hash` = '%s'", account.getUsername(), account.getPassword());
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                permission = rs.getString(Constants.PERMISSION);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return permission;
    }

    public static void create(Account account) {
        try {
            if (!Constants.isEmail(account.getUsername())) {
                System.out.println("Username not email format!");
                System.out.println(Constants.CREATE_FAILED);
            } else {
                Connection conn = Connect.getConnection();
                String sql = String.format("INSERT IGNORE INTO account VALUES ('%s','%s','%s','%s')", account.getAccount_id(), account.getUsername(), account.getPassword(), account.getPermission());
                Statement stsm = conn.createStatement();
                int rs = stsm.executeUpdate(sql);
                System.out.println((rs == 0) ? Constants.CREATE_FAILED : Constants.CREATE_SUCCESS);
                stsm.close();
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean existAccount(String username) {
        boolean isAccount = false;
        try {
            Connection conn = Connect.getConnection();
            String sql = String.format("SELECT * FROM account WHERE username = '%s'", username);
            Statement state = conn.createStatement();
            ResultSet rs = state.executeQuery(sql);
            String name = "";
            if (rs.next()) {
                name = rs.getString(Constants.USERNAME);
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

    public static void update(String name, String[] result) {
        try {
            String account_id = getIdByName(name);
            Connection conn = Connect.getConnection();
            Statement state = conn.createStatement();
            String sql = String.format("UPDATE account SET username = '%s', password_hash = '%s', permission = '%s' WHERE `account_id` = '%s'",
                    result[0], result[1], result[2], account_id);
            int rs = state.executeUpdate(sql);
            System.out.println((rs == 0) ? Constants.UPDATE_FAILED : Constants.UPDATE_SUCCESS);
            state.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getIdByName(String username) {
        String account_id = Constants.EMPTY;
        try {
            Connection conn = Connect.getConnection();
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

    public static void delete(String username) {
        if (username.equals(Constants.EMAIL_ADMIN)) {
            System.out.println("Can't delete admin account!");
        } else {
            String account_id = getIdByName(username);
            try {
                Connection conn = Connect.getConnection();
                String sql = String.format("DELETE FROM `account` WHERE account_id = '%s'", account_id);
                ;
                Statement stsm = conn.createStatement();
                int rs = stsm.executeUpdate(sql);
                System.out.println((rs == 0) ? Constants.DELETE_FAILED : Constants.DELETE_SUCCESS);
                stsm.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static List<Account> getAll() {
        List<Account> accountList = new ArrayList<>();
        try {
            Connection conn = Connect.getConnection();
            String sql = "SELECT * FROM `account`";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Account account = new Account();
                account.setUsername(rs.getString(Constants.USERNAME));
                account.setPermission(rs.getString(Constants.PERMISSION));
                accountList.add(account);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accountList;
    }
}