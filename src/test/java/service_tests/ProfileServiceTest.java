package service_tests;

import dao.mocks.KweetDaoMock;
import dao.mocks.ProfileDaoMock;
import dao.mocks.UserDaoMock;
import dao.interfaces.IKweetDao;
import dao.interfaces.IProfileDao;
import dao.interfaces.IUserDao;
import domain.Kweet;
import domain.User;
import exceptions.*;
import services.ProfileService;
import util.MockFactory;
import util.MockService;
import viewmodels.ProfileData;
import viewmodels.UserTotalsView;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class ProfileServiceTest {

    private static IUserDao userDao;
    private static IProfileDao profileDao;
    private static IKweetDao kweetDao;

    private static ProfileService service;

    @BeforeClass
    public static void setUp() {
        userDao = new UserDaoMock();
        profileDao = new ProfileDaoMock();
        kweetDao = new KweetDaoMock();

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

    @Test
    public void getProfileData() throws InvalidProfileException {
        // Setup
        User hank = (User) MockFactory.createMocks(User.class, 1, "username", "henk7").get(0);
        userDao.create(hank);
        ProfileData profileData = new ProfileData("dummyProfile", "Eindhoven", "https://www.google.nl", "somebody");

        // Assert before
        Assert.assertNull(service.getProfileData(hank.getUsername()));

        // Set profile
        service.setProfile(hank.getUsername(), profileData);

        // Assert after
        Assert.assertSame("Location couldn't be found for " + hank.getUsername(), profileData.getLocation(),
                service.getProfileData(hank.getUsername()).getLocation());
        Assert.assertSame("Bio couldn't be found for " + hank.getUsername(), profileData.getBio(),
                service.getProfileData(hank.getUsername()).getBio());
        Assert.assertSame("Website couldn't be found for " + hank.getUsername(), profileData.getWebsite(),
                service.getProfileData(hank.getUsername()).getWebsite());
        Assert.assertSame("Profile name couldn't be found for " + hank.getUsername(), profileData.getName(),
                service.getProfileData(hank.getUsername()).getName());
    }

    @Test
    public void getProfileData_nullProfile() {
        // Setup
        User hank = (User) MockFactory.createMocks(User.class, 1, "username", "henk7").get(0);
        userDao.create(hank);

        // Assert before
        Assert.assertNull(service.getProfileData(hank.getUsername()));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void getUserTotals() {
        // Setup
        User hank = (User) MockFactory.createMocks(User.class, 1, "username", "henk8").get(0);
        List<User> following =  MockService.toUsers(MockFactory.createMocks(User.class, 3));
        List<User> follower =  MockService.toUsers(MockFactory.createMocks(User.class, 4));
        List<Kweet> kweets =  MockService.toKweets(MockFactory.createMocks(Kweet.class, 5, "sender", hank));
        userDao.create(hank);
        hank.setFollowing(userDao.create(following));
        hank.setFollowers(userDao.create(follower));
        hank.setKweets(kweetDao.create(kweets));

        // Get user totals
        UserTotalsView userTotals = service.getUserTotals(hank.getUsername());

        // Asserts
        Assert.assertEquals("Following size isn't 3", 3, userTotals.getFollowing());
        Assert.assertEquals("Follower size isn't 4", 4, userTotals.getFollowers());
        Assert.assertEquals("Kweets size isn't 5", 5, userTotals.getkweets());
    }
}
