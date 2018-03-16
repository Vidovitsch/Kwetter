package DAO;

import DAO.Impl.UserDaoImpl;
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

    @BeforeClass
    public static void Init() {
        userDao = new UserDaoImpl("KwetterPU_test");
    }

    @AfterClass
    public static void tearDown() {
        MockService.renewMockData();
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
        User u = new User();
        u.setUsername("henk");
        userDao.create(u);
        Assert.assertEquals(u, userDao.findByUsername(u.getUsername()));

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
    public void updateUserTest() {
        int i = 1;
        List<User> users =new ArrayList<User>();
        users.addAll(userDao.findAll());
        Iterator<User> userIterator = users.iterator();
        User u;
        while(userIterator.hasNext()){
            u = userIterator.next();
            Profile p = new Profile();
            p.setwebsite("test" + i);
            u.setProfile(p);
            userDao.update(u);
            Assert.assertEquals("test" + i, userDao.findByUsername(u.getUsername()).getProfile().getwebsite());
            i++;
        }
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
