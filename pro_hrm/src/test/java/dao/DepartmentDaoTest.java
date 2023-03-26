package dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DepartmentDaoTest {
    @Test
    @DisplayName("Test tồn tại phòng ban")
    public void testExistDepartment(){
        // TH1: phòng ban tồn tại
        String department_name = "NHÂN SỰ";
        boolean result = DepartmentDao.existDepartment(department_name);
        Assertions.assertEquals(result, true);

        // TH2: phòng ban không tồn tại
        String name = "TUYỂN DỤNG";
        boolean result1 = DepartmentDao.existDepartment(name);
        Assertions.assertEquals(result1, false);
    }
    @Test
    @DisplayName("Test tạo mới phòng ban")
    public void testCreate(){

    }

}