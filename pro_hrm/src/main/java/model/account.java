package model;

import java.util.UUID;

public class account {
    private UUID account_id; // UUID -> khai báo khóa chính kiểu new guid
    private String useName;
    private String password;
    private String permission;

    public account() {
    }

    public account(UUID account_id, String useName, String password, String permission) {
        this.account_id = account_id;
        this.useName = useName;
        this.password = password;
        this.permission = permission;
    }

    public UUID getAccount_id() {
        return account_id;
    }

    public void setAccount_id(UUID account_id) {
        this.account_id = account_id;
    }

    public String getUseName() {
        return useName;
    }

    public void setUseName(String useName) {
        this.useName = useName;
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
                ", useName='" + useName + '\'' +
                ", password='" + password + '\'' +
                ", permission='" + permission + '\'' + ']';
    }
}
