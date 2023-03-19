package dao;

import connection.MySQLConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class accountDao {
    public static boolean login(String username, String passwordHash) {
        boolean result = false;
        try{
            // lấy ra password
            Connection conn = MySQLConnection.getConnection();
            String sql = String.format("SELECT password_hash FROM account WHERE BINARY username = '%s'", username);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            // gán giá trị lấy được từ việc thực hiện câu lệnh mysql
            String pass = "";
            while (rs.next()) {
                pass = rs.getString("password_hash");
            }
            result = passwordHash.equals(pass) ? true : false;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
