import DAO.Mock.UserDaoMock;
import DaoInterfaces.IUserDao;
import Domain.User;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Collection;

public class UserDaoTest {

    private static IUserDao userDao;

    @BeforeClass
    public static void Init() {
        userDao = new UserDaoMock();
    }

    @Test
    public void findAllTest() {
        Collection<User> foundUsers = userDao.findAll();
        Assert.assertNotNull(foundUsers);
    }

    @Test
    public void findByIdTest() {
        for(User u : userDao.findAll()){
            Assert.assertEquals(u,userDao.findById(u.getId()));
        }
    }

    @Test
    public void findByUsernameTest() {
        for(User u : userDao.findAll()){
            Assert.assertEquals(u,userDao.findByUsername(u.getUsername()));
        }
    }

    @Test
    public void insertUserTest() {
        User u = new User();
        u.setUsername("henk");
        userDao.insertUser(u);
        Assert.assertEquals(u,userDao.findByUsername(u.getUsername()));
    }

    @Test
    public void updateUserTest() {
        for(User u : userDao.findAll()){
            String currentUsername = u.getUsername();
            u.setUsername(u.getUsername()+"test");
            userDao.updateUser(u);
            Assert.assertEquals(currentUsername+"test",userDao.findById(u.getId()).getUsername());
        }
    }

    @Test
    public void deleteUser() {
        // To Do
    }

}
