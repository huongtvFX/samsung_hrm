package dao;

import model.Account;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pre_processing.preProcess;

import java.util.List;
import java.util.UUID;

class AccountDaoTest {
    @Test
    public void testGetPasswordHash(){
        // kịch bản ngoại lệ 1 (tên tài khoản không tồn tại)
        Account account = new Account(
                UUID.fromString("33411d2d-9915-4a1a-98bc-92f1916e9011"),
                "thanhpax1@samsungsds.com",
                "ee11cbb19052e40b07aac0ca060c23ee",
                "user");
        account.setUsername("thanh@samsungsds.com");

        String pass = AccountDao.getPasswordHash(account);
        Assertions.assertEquals("", pass);

        // kịch bản 2: tên tài khoản hợp lệ
        account.setUsername("thanhpax1@samsungsds.com");
        String pass_success = AccountDao.getPasswordHash(account);
        Assertions.assertEquals(account.getPassword(), pass_success);
    }

    @Test
    public void testGetPermission(){
        // Kịch bản 1: Sai username
        Account account = new Account(
                UUID.fromString("33411d2d-9915-4a1a-98bc-92f1916e9011"),
                "thanhpax@samsungsds.com",
                "ee11cbb19052e40b07aac0ca060c23ee",
                "user");
        account.setUsername("thanh@samsungsds.com");

        String permission = AccountDao.getPermission(account);
        Assertions.assertEquals("", permission);

        // kịch bản 2: Sai password
        account.setUsername("thanhpax@samsungsds.com");
        account.setPassword("11cbb1905992e40b07aac0ca060c23ee");
        Assertions.assertEquals("", AccountDao.getPermission(account));

        // Kịch bản 3: sai cả username và password
        account.setUsername("thanh@samsungsds.com");
        account.setPassword("11cbb1905992e40b07aac0ca060c23ee");
        Assertions.assertEquals("", AccountDao.getPermission(account));

        // Kịch bản 4: Đúng cả username và password
        account.setUsername("thanhpax@samsungsds.com");
        account.setPassword("ee11cbb19052e40b07aac0ca060c23ee");
        Assertions.assertEquals("user", AccountDao.getPermission(account));
    }

    @Test
    public void testCreate(){
        // TH1: user có đã tồn tại
        Account account = new Account(
                UUID.fromString("33411d2d-9915-4a1a-98bc-92f1916e9011"),
                "thanhpax@samsungsds.com",
                "ee11cbb19052e40b07aac0ca060c23ee",
                "user");
        AccountDao.create(account);
        Assertions.assertNotNull(account);

        // TH2: user có username sai định dạng
        Account account1 = new Account(
                UUID.fromString("33411d2d-9915-4a1a-98bc-92f1916e9011"),
                "thanhpax@gmail.com",
                "ee11cbb19052e40b07aac0ca060c23ee",
                "user");
        AccountDao.create(account1);
        Assertions.assertNotNull(account1);

        // TH3: Tạo tài khoản thành công
        Account account2 = new Account(
                UUID.fromString("53bad5df-94c7-4beb-98b3-df44571eaa18"),
                "namnv1012@samsungsds.com",
                "ee11cbb19052e40b07aac0ca060c23ee",
                "user");
        AccountDao.create(account2);
        String id = AccountDao.getIdByName("namnv1012@samsungsds.com");
        Assertions.assertNotNull(account2);
        Assertions.assertEquals(account2.getAccount_id(), UUID.fromString(id));
    }
    @Test
    public void testExistAccount(){
        // TH1: username sai hoặc không tồn tại
        String username = "thanhpa@samsungsds.com";
        boolean result = AccountDao.existAccount(username);
        Assertions.assertEquals(result, false);

        String username1 = "thanhpax@samsungsds.com";
        boolean result1 = AccountDao.existAccount(username1);
        Assertions.assertEquals(result1, true);
    }
    @Test
    public void testUpdate(){
        // Kịch bản 1: Cập nhật thành công
        String name = "namnv1012@samsungsds.com";
        UUID accountID = UUID.fromString("53bad5df-94c7-4beb-98b3-df44571eaa18");

        String newName = "thanhpax2@samsungsds.com";
        String passWord = "user";
        String passwordHash = preProcess.getMd5(passWord);
        String permission = "user";
        String[] result = new String[3];
        result[0] = newName;
        result[1] = passwordHash;
        result[2] = permission;

        AccountDao.update(name, result);

        // Kiểm tra
        String id = AccountDao.getIdByName("thanhpax2@samsungsds.com");
        Assertions.assertEquals(accountID, UUID.fromString(id));
    }

    @Test
    public void testGetAll(){
        List<Account> aList = AccountDao.getAll();
        Assertions.assertNotNull(aList);
        Assertions.assertEquals(5, aList.size());
    }

    @Test
    public void testDelete(){
        String username = "Ducbn2102@samsungsds.com";
        AccountDao.delete(username);

        List<Account> aList = AccountDao.getAll();
        Assertions.assertNotNull(aList);
        Assertions.assertEquals(5, aList.size());
    }

    @Test
    public void testDeleteSuccess(){
        String username = "namnv1012@samsungsds.com";
        AccountDao.delete(username);
        List<Account> aList1 = AccountDao.getAll();
        Assertions.assertNotNull(aList1);
        Assertions.assertEquals(4, aList1.size());
    }
}