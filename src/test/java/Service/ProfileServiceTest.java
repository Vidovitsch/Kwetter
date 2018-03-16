package Service;

import DAO.Mock.ProfileDaoMock;
import DAO.Mock.UserDaoMock;
import DaoInterfaces.IProfileDao;
import DaoInterfaces.IUserDao;
import Domain.User;
import Exception.*;
import Util.MockFactory;
import Util.MockService;
import ViewModels.ProfileData;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ProfileServiceTest {

    private static IUserDao userDao;
    private static IProfileDao profileDao;

    private static ProfileService service;

    @BeforeClass
    public static void setUp() {
        userDao = new UserDaoMock();
        profileDao = new ProfileDaoMock();

        service = new ProfileService();
        service.setUserDao(userDao);
        service.setProfileDao(profileDao);
    }

    @After
    public void tearDown() {
        MockService.renewMockData();
    }

    @Test
    public void setProfile_create() throws InvalidProfileException {
        // Setup
        User hank = (User) MockFactory.createMocks(User.class, 1, "username", "henk1").get(0);
        userDao.create(hank);
        ProfileData profileData = new ProfileData("dummyProfile", "Eindhoven", "https://www.google.nl", "somebody");

        // Assert before
        Assert.assertNull(service.getProfileData(hank.getUsername()));

        // Set profile
        service.setProfile(hank.getUsername(), profileData);

        // Assert after
        Assert.assertEquals("Profile is null or has invalid name", profileData.getName(), service.getProfileData(hank.getUsername()).getName());
        Assert.assertEquals("Profile is null or has invalid website", profileData.getWebsite(), service.getProfileData(hank.getUsername()).getWebsite());
        Assert.assertEquals("Profile is null or has invalid biography", profileData.getBio(), service.getProfileData(hank.getUsername()).getBio());
        Assert.assertEquals("Profile is null or has invalid location", profileData.getLocation(), service.getProfileData(hank.getUsername()).getLocation());
    }

    @Test(expected = InvalidProfileException.class)
    public void setProfile_nullName() throws InvalidProfileException {
        // Setup
        User hank = (User) MockFactory.createMocks(User.class, 1, "username", "henk2").get(0);
        userDao.create(hank);
        ProfileData profileData = new ProfileData(null, "Eindhoven", "https://www.google.nl", "somebody");

        // Set profile
        service.setProfile(hank.getUsername(), profileData);
    }

    @Test(expected = InvalidProfileException.class)
    public void setProfile_emptyName() throws InvalidProfileException {
        // Setup
        User hank = (User) MockFactory.createMocks(User.class, 1, "username", "henk3").get(0);
        userDao.create(hank);
        ProfileData profileData = new ProfileData("", "Eindhoven", "https://www.google.nl", "somebody");

        // Set profile
        service.setProfile(hank.getUsername(), profileData);

    }

    @Test(expected = InvalidProfileException.class)
    public void setProfile_nullWebsite() throws InvalidProfileException {
        // Setup
        User hank = (User) MockFactory.createMocks(User.class, 1, "username", "henk4").get(0);
        userDao.create(hank);
        ProfileData profileData = new ProfileData("dummyProfile", "Eindhoven", null, "somebody");

        // Set profile
        service.setProfile(hank.getUsername(), profileData);

    }

    @Test(expected = InvalidProfileException.class)
    public void setProfile_invalidWebsite() throws InvalidProfileException {
        // Setup
        User hank = (User) MockFactory.createMocks(User.class, 1, "username", "henk5").get(0);
        userDao.create(hank);
        ProfileData profileData = new ProfileData("dummyProfile", "Eindhoven", "https://g.com.oogle", "somebody");

        // Set profile
        service.setProfile(hank.getUsername(), profileData);
    }

    @Test
    public void setProfile_update() throws InvalidProfileException {
        // Setup
        User hank = (User) MockFactory.createMocks(User.class, 1, "username", "henk6").get(0);
        userDao.create(hank);
        ProfileData profileData = new ProfileData("dummyProfile", "Eindhoven", "https://www.google.nl", "somebody");

        // Set profile
        service.setProfile(hank.getUsername(), profileData);

        // Assert before
        Assert.assertEquals("Profile name isn't 'dummyProfile'", "dummyProfile", service.getProfileData(hank.getUsername()).getName());

        // Create new profile
        ProfileData profileData2 = new ProfileData("dummyProfile123123", "Eindhoven", "https://www.google.nl", "somebody");

        // Set profile again
        service.setProfile(hank.getUsername(), profileData2);

        // Assert after
        Assert.assertEquals("Profile name isn't 'dummyProfile'", "dummyProfile123123", service.getProfileData(hank.getUsername()).getName());
    }
}
