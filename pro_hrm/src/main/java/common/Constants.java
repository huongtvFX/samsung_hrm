package common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Constants {
    public static boolean isEmail(String email) {
        String regex = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" + "samsungsds.com";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isPhone(String phone) {
        String regex = "(84|0[2|3|5|7|8|9])+([0-9]{8})\\b";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    public static boolean isDateOfBirth(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        boolean isCheck = false;
        try {
            Date d1 = sdf.parse(date);
            isCheck = true;
        } catch (Exception e) {
            isCheck = false;
        }
        return isCheck;
    }

    public static String LOGIN_SUCCESS = "Login successful!";
    public static String LOGIN_FAILED = "Login unsuccessful!";
    public static String EMAIL_ADMIN = "TranVanHuongSDS@gmail.com";
    public static String ADMIN = "admin";
    public static String PASSWORD_HASH = "password_hash";
    public static String PERMISSION = "permission";
    public static String USERNAME = "username";
    public static String EMPTY = "";
    public static String DEPARTMENT_NAME = "department_name";
    public static String DEPARTMENT_ID = "department_id";
    public static String CREATE_SUCCESS = "Create success!";
    public static String CREATE_FAILED = "Creation failed!";
    public static String UPDATE_SUCCESS = "Update successful!";
    public static String UPDATE_FAILED = "Update failed!";
    public static String DELETE_SUCCESS = "Delete successfully!";
    public static String DELETE_FAILED = "Delete failed!";
    public static String DELETE_EMP_TO_DEPARTMENT_SUCCESS = "Remove employee from department successfully!";
    public static String DELETE_EMP_TO_DEPARTMENT_FAILED = "Remove employees from failure department!";
    public static String ADD_EMP_TO_DEPARTMENT_SUCCESS = "Add employee from department successfully!";
    public static String ADD_EMP_TO_DEPARTMENT_FAILED = "Add employees from failure department!";
}
