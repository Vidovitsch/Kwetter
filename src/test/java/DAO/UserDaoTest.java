package DAO;

import DAO.Mock.UserDaoMock;
import DaoInterfaces.IUserDao;
import Domain.Profile;
import Domain.User;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import static com.sun.org.apache.xml.internal.security.keys.keyresolver.KeyResolver.iterator;

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
/*        for(User u : userDao.findAll()){
            Assert.assertEquals(u,userDao.findById(u.getId()));
        }*/
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
        userDao.insertUser(u);
        Assert.assertEquals(u, userDao.findByUsername(u.getUsername()));
    }

    @Test
    public void updateUserTest() {
        int i = 1;
        Collection<User> users =new ArrayList<User>();
        users.addAll(userDao.findAll());
        Iterator<User> userIterator = users.iterator();
        User u;
        while(userIterator.hasNext()){
            u = userIterator.next();
            Profile p = new Profile();
            p.setwebsite("test" + i);
            u.setProfile(p);
            userDao.updateUser(u);
            Assert.assertEquals("test" + i, userDao.findByUsername(u.getUsername()).getProfile().getwebsite());
            i++;
        }
    }

    @Test
    public void deleteUser() {
        // To Do
    }

}
