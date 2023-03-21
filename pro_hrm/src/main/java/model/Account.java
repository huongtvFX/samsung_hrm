package model;

import java.util.UUID;

public class Account {
    private UUID account_id; // UUID -> khai báo khóa chính kiểu new guid
    private String username;
    private String password;
    private String permission;

    //region constructor
    public Account() {
    }
    public Account(String username, String password, String permission) {
        this.username = username;
        this.password = password;
        this.permission = permission;
    }
    public Account(UUID account_id, String username, String password, String permission) {
        this.account_id = account_id;
        this.username = username;
        this.password = password;
        this.permission = permission;
    }
    //endregion
    public UUID getAccount_id() {
        return account_id;
    }
    public void setAccount_id(UUID account_id) {
        this.account_id = account_id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPermission() {
        return permission;
    }
    public void setPermission(String permission) {
        this.permission = permission;
    }
    @Override
    public String toString() {
        return "account[" +
                "account_id=" + account_id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", permission='" + permission + '\'' +
                ']';
    }
}

