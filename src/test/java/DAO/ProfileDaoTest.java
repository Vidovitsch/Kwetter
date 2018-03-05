package DAO;

import DAO.Mock.*;
import DaoInterfaces.*;
import org.junit.BeforeClass;
import org.junit.Test;

public class ProfileDaoTest {

    private static IProfileDao profileDao;

    private static IUserDao userDao;

    @BeforeClass
    public static void Init() {
        userDao = new UserDaoMock();
        profileDao = new ProfileDaoMock(userDao.findAll());
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
    public void findByUserTest() {
        // To Do
    }

    @Test
    public void findByNameTest() {
        // To Do
    }

    @Test
    public void insertProfileTest() {
        // To Do
    }

    @Test
    public void updateProfileTest() {
        // To Do
    }

    @Test
    public void deleteProfileTest() {
        // To Do
    }
}
