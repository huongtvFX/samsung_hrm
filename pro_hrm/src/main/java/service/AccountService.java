//region Thư viện cần import
package service;

import dao.AccountDao;
import model.Account;

import java.util.List;
//endregion

public class AccountService {
    public static boolean login(Account account) {
        String passwordHash = AccountDao.getPasswordHash(account);
        return passwordHash.equals(account.getPassword());
    }

    public static String ConfirmPermission(Account account) {
        String permission = AccountDao.getPermission(account);
        return permission;
    }

    public static void create(Account account) {
        AccountDao.create(account);
    }

    public static void update(String name, String[] info) {
        AccountDao.update(name, info);
    }

    public static void delete(String username) {
        AccountDao.delete(username);
    }

    public static List<Account> getAll() {
        List<Account> lstAccount = AccountDao.getAll();
        return lstAccount;
    }
}
