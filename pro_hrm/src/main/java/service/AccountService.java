//region Thư viện cần import
package service;

import dao.AccountDao;
import model.Account;

import java.util.List;
//endregion

public class AccountService {
    public static boolean login(String[] infoAccount){
        String passwordHash = AccountDao.getPasswordHash(infoAccount);
        return passwordHash.equals(infoAccount[1]);
    }
    public static String ConfirmPermission(String[] infoAccount){
        String permission = AccountDao.getPermission(infoAccount);
        return permission;
    }
    public static void create(Account account){
        AccountDao.checkBeforeCreate(account);
    }
    public static void update(String[] info){
        AccountDao.update(info);
    }
    public static void delete(String username){
        AccountDao.deleteAccount(username);
    }
    public static List<Account> getAll(){
        List<Account> lstAccount = AccountDao.getAllAccount();
        return lstAccount;
    }
}
