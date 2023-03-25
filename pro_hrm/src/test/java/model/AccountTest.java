package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;


class AccountTest {
    @Test
    @DisplayName("Tạo tài khoản với constructor không tham số")
    public void createAccount1(){
        Account account = new Account();
        account.setAccount_id(UUID.fromString("da576def-cb3f-11ed-b538-6c2b595bf169"));
        account.setUsername("namnguyen@samsungsds.com");
        account.setPassword("admin");
        account.setPermission("user");
    }

    @Test
    @DisplayName("Tạo tài khoản với constructor có 4 tham số")
    public void createAccount4(){
        Account account = new Account(UUID.fromString("da576def-cb3f-11ed-b538-6c2b595bf169"), "namnguyen@samsungsds.com", "admin", "user");
        UUID account_id = UUID.fromString("da576def-cb3f-11ed-b538-6c2b595bf169");
        Assertions.assertEquals(account_id, account.getAccount_id());
        String username = "namnguyen@samsungsds.com";
        Assertions.assertEquals(username, account.getUsername());
        String pass = "admin";
        Assertions.assertEquals(pass, account.getPassword());
        String permission = "user";
        Assertions.assertEquals(permission, account.getPermission());
    }

    @Test
    @DisplayName("Tạo tài khoản với constructor có 2 tham số")
    public void createAccount2(){
        Account account = new Account("namnguyen@samsungsds.com", "admin");
        String username = "namnguyen@samsungsds.com";
        Assertions.assertEquals(username, account.getUsername());
        String pass = "admin";
        Assertions.assertEquals(pass, account.getPassword());
    }

    @Test
    @DisplayName("Tạo tài khoản với constructor có 3 tham số")
    public void createAccount3(){
        Account account = new Account("namnguyen@samsungsds.com", "admin", "user");
        String username = "namnguyen@samsungsds.com";
        Assertions.assertEquals(username, account.getUsername());
        String pass = "admin";
        Assertions.assertEquals(pass, account.getPassword());
        String permission = "user";
        Assertions.assertEquals(permission, account.getPermission());
    }

    @Test
    @DisplayName("Giá trị của các trường thông tin là null")
    public void usernameDefault(){
        Account account = new Account();
        String username = null;
        Assertions.assertEquals(username, account.getUsername());
        String pass = null;
        Assertions.assertEquals(pass, account.getPassword());
        String permission = null;
        Assertions.assertEquals(permission, account.getPermission());
        String account_id = null;
        Assertions.assertEquals(account_id, account.getAccount_id());

    }
}