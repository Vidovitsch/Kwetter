package Service;

import DAO.Mock.UserDaoMock;
import DaoInterfaces.IUserDao;
import Domain.Profile;
import Domain.User;
import Util.MockFactory;
import Util.MockService;
import ViewModels.OtherUserView;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class UserServiceTest {

    private static IUserDao userDao;

    private static UserService service;

    @BeforeClass
    public static void setUp() {
        userDao = new UserDaoMock();

        service = new UserService();
        service.setUserDao(userDao);
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
                0, mockUsers.get(0).getFollowing().size());
        Assert.assertFalse(mockUsers.get(0).getFollowing().contains(mockUsers.get(1)));

        // Add following
        boolean result = service.addFollowing(mockUsers.get(0).getId(), mockUsers.get(1).getId());

        // Assert after
        Assert.assertEquals("User follows one other user",
                1, mockUsers.get(0).getFollowing().size());
        Assert.assertTrue(mockUsers.get(0).getFollowing().contains(mockUsers.get(1)));
        Assert.assertFalse("User followed user successfully", result);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void addFollowing_AlreadyFollowing() {
        // Setup
        List<User> mockUsers = (List<User>) MockFactory.createMocks(User.class, 2);
        userDao.create(mockUsers);
        service.addFollowing(mockUsers.get(0).getId(), mockUsers.get(1).getId());

        // Add following again
        boolean result = service.addFollowing(mockUsers.get(0).getId(), mockUsers.get(1).getId());

        // Assert after
        Assert.assertEquals(1, mockUsers.get(0).getFollowing().size());
        Assert.assertTrue(mockUsers.get(0).getFollowing().contains(mockUsers.get(1)));
        Assert.assertFalse("User followed user already", result);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void getFollowing_WithProfile() {
        // Setup
        Profile mockProfile = (Profile) MockFactory.createMocks(Profile.class, 1).get(0);
        List<User> mockUsers = (List<User>) MockFactory.createMocks(User.class, 3, "profile", mockProfile);
        userDao.create(mockUsers);
        service.addFollowing(mockUsers.get(0).getId(), mockUsers.get(1).getId());
        service.addFollowing(mockUsers.get(0).getId(), mockUsers.get(2).getId());

        // Get followling
        List<OtherUserView> following = service.getFollowing(mockUsers.get(0).getUsername());

        // Asserts
        Assert.assertEquals("User follows two other users", 2, following.size());
        Assert.assertEquals(mockUsers.get(1).getUsername(), following.get(0).getUsername());
        Assert.assertEquals(mockUsers.get(2).getUsername(), following.get(1).getUsername());

        // Check image
        Assert.assertNotNull(following.get(0).getImage());
        Assert.assertNotNull(following.get(1).getImage());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void getFollowing_NoProfile() {
        // Setup
        List<User> mockUsers = (List<User>) MockFactory.createMocks(User.class, 3);
        userDao.create(mockUsers);
        service.addFollowing(mockUsers.get(0).getId(), mockUsers.get(1).getId());
        service.addFollowing(mockUsers.get(0).getId(), mockUsers.get(2).getId());

        // Get followling
        List<OtherUserView> following = service.getFollowing(mockUsers.get(0).getUsername());

        // Asserts
        Assert.assertEquals("User follows two other users", 2, following.size());
        Assert.assertEquals(mockUsers.get(1).getUsername(), following.get(0).getUsername());
        Assert.assertEquals(mockUsers.get(2).getUsername(), following.get(1).getUsername());

        // Check image
        Assert.assertNull(following.get(0).getImage());
        Assert.assertNull(following.get(1).getImage());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void getFollowers() {
        // Setup
        List<User> mockUsers = (List<User>) MockFactory.createMocks(User.class, 3);
        userDao.create(mockUsers);
        service.addFollowing(mockUsers.get(0).getId(), mockUsers.get(2).getId());
        service.addFollowing(mockUsers.get(1).getId(), mockUsers.get(2).getId());

        // Get followers
        List<OtherUserView> followers = service.getFollowers(mockUsers.get(2).getUsername());

        // Asserts
        Assert.assertEquals("User has two followers", 2, followers.size());
        Assert.assertEquals(mockUsers.get(0).getUsername(), followers.get(0).getUsername());
        Assert.assertEquals(mockUsers.get(1).getUsername(), followers.get(1).getUsername());
    }
}
