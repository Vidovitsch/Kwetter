package DAO;

import DAO.Mock.*;
import DaoInterfaces.*;
import org.junit.BeforeClass;
import org.junit.Test;

public class RoleDaoTest {
    private static IRoleDao roleDao;
    private static IUserDao userDao;

    @BeforeClass
    public static void Init() {
        userDao = new UserDaoMock();
        roleDao = new RoleDaoMock(userDao.findAll());
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
    public void findByNameTest() {
        // To Do
    }

    @Test
    public void insertRoleTest() {
        // To Do
    }

    @Test
    public void updateRoleTest() {
        // To Do
    }

    @Test
    public void deleteRoleTest() {
        // To Do
    }

    @Test
    public void someTestTest() {
        // To Do
    }

}
