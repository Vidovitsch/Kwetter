package dao_test;

import dao.implementations.UserDaoImpl;
import dao.interfaces.IUserDao;
import domain.User;
import util.MockFactory;
import util.MockService;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.Collection;
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
            Assert.assertEquals(u.getId(), userDao.findByUsername(u.getUsername()).getId());
        }
    }

    @Test
    public void insertUserTest() {
        User u = (User) MockFactory.createMocks(User.class, 1).get(0);
        String username = u.getUsername();

        beginUserTransaction();
        userDao.create(u);
        endUserTransaction();

        Assert.assertEquals(u, userDao.findByUsername(username));

        // Cleanup
        beginUserTransaction();
        userDao.remove(u);
        endUserTransaction();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void insertUsersTest() {
        // Insert new role
        List<User> mockUsers = (List<User>) MockFactory.createMocks(User.class, 3);

        beginUserTransaction();
        userDao.create(mockUsers);
        endUserTransaction();

        // Check Role list contains new role
        Assert.assertTrue("New users have been added", userDao.findAll().containsAll(mockUsers));;
    }

    @Test
    public void deleteUser() {
        // Insert new user
        User mockUser = new User((long)-1,"mockUser");
        mockUser.setId((long)999999);

        beginUserTransaction();
        userDao.create(mockUser);
        endUserTransaction();

        // Delete inserted user
        beginUserTransaction();
        userDao.remove(mockUser);
        endUserTransaction();

        // Check User list contains new user
        Assert.assertFalse("New user has been removed", userDao.findAll().contains(mockUser));
    }

    private void beginUserTransaction() {
        if (userDao.getEntityManager() != null) {
            userDao.getEntityManager().getTransaction().begin();
        }
    }

    private void endUserTransaction() {
        if (userDao.getEntityManager() != null) {
            userDao.getEntityManager().getTransaction().commit();
        }
    }
}
