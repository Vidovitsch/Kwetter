package DAO;

import DAO.Mock.*;
import DaoInterfaces.*;
import org.junit.BeforeClass;
import org.junit.Test;

public class UserDaoTest {

    private static IUserDao userDao;

    @BeforeClass
    public static void Init() {
        userDao = new UserDaoMock();
    }

    @Test
    public void someTest() {
        // To Do
    }

    @Test
    public void findAllTest() {
        // To Do
    }

    @Test
    public void findByIdTest() {
        // To Do
    }

    @Test
    public void findByUsernameTest() {
        // To Do
    }

    @Test
    public void insertUserTest() {
        // To Do
    }

    @Test
    public void updateUserTest() {
        // To Do
    }

    @Test
    public void deleteUser() {
        // To Do
    }

}
