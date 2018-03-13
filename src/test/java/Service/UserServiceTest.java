package Service;

import DAO.Mock.HashtagDaoMock;
import DAO.Mock.KweetDaoMock;
import DAO.Mock.ProfileDaoMock;
import DAO.Mock.UserDaoMock;
import DaoInterfaces.IProfileDao;
import DaoInterfaces.IUserDao;
import Domain.User;
import Qualifier.Mock;
import Util.MockFactory;
import Util.MockService;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class UserServiceTest {

    private static IUserDao userDao;
    private static IProfileDao profileDao;

    private static UserService service;

    @BeforeClass
    public static void setUp() {
        userDao = new UserDaoMock();
        profileDao = new ProfileDaoMock();

        service = new UserService();
        service.setUserDao(userDao);
        service.setProfileDao(profileDao);
    }

    @AfterClass
    public static void tearDown() {
        MockService.resetMockData();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void addFollowing() {
        // Setup
        List<User> mockUsers = (List<User>) MockFactory.createMocks(User.class, 2);
        userDao.create(mockUsers);

        // Assert before
        Assert.assertEquals("User follows nobody",
                mockUsers.get(0).getFollowing().size(), 0);
        Assert.assertFalse("User follows nobody",
                mockUsers.get(0).getFollowing().contains(mockUsers.get(1)));

        // Add following
        Boolean result = service.addFollowing(mockUsers.get(0).getId(), mockUsers.get(1).getId());

        // Assert after
        Assert.assertEquals("User follows one other user",
                mockUsers.get(0).getFollowing().size(), 1);
        Assert.assertFalse("User follows one other user",
                mockUsers.get(0).getFollowing().contains(mockUsers.get(1)));
        Assert.assertTrue("User followed user successfully", result);
    }
}
