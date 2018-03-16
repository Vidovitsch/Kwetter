package DAO;

import DAO.Impl.ProfileDaoImpl;
import DAO.Impl.UserDaoImpl;
import DAO.Mock.ProfileDaoMock;
import DaoInterfaces.IProfileDao;
import DaoInterfaces.IUserDao;
import Domain.Profile;
import Domain.User;
import Util.MockFactory;
import Util.MockService;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class UserDaoTest {


    private static IUserDao userDao;

    private static IProfileDao profileDao;

    @BeforeClass
    public static void Init() {
        userDao = new UserDaoImpl("KwetterPU_test");
        profileDao = new ProfileDaoImpl("KwetterPU_test");
    }

    @AfterClass
    public static void tearDown() {
        MockService.resetMockData();
    }

    @Test
    public void findAllTest() {
        Collection<User> foundUsers = userDao.findAll();
        Assert.assertNotNull(foundUsers);
    }

    @Test
    public void findByIdTest() {
        for (User u : userDao.findAll()) {
            Assert.assertEquals(u, userDao.findById(u.getId()));
        }
    }

    @Test
    public void findByUsernameTest() {
        for (User u : userDao.findAll()) {
            Assert.assertEquals(u, userDao.findByUsername(u.getUsername()));
        }
    }

    @Test
    public void insertUserTest() {
        User u = (User) MockFactory.createMocks(User.class, 1).get(0);
        String username = u.getUsername();
        userDao.create(u);
        Assert.assertEquals(u, userDao.findByUsername(username));
        // Cleanup
        userDao.remove(u);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void insertUsersTest() {
        // Insert new role
        List<User> mockUsers = (List<User>) MockFactory.createMocks(User.class, 3);
        userDao.create(mockUsers);

        // Check Role list contains new role
        Assert.assertTrue("New users have been added", userDao.findAll().containsAll(mockUsers));;
    }

    @Test
    public void deleteUser() {
        // Insert new user
        User mockUser = new User((long)-1,"mockUser");
        mockUser.setId((long)999999);
        userDao.create(mockUser);

        // Delete inserted user
        userDao.remove(mockUser);

        // Check User list contains new user
        Assert.assertFalse("New user has been removed", userDao.findAll().contains(mockUser));
    }
}
