package service;

import exceptions.*;
import dao.mocks.KweetDaoMock;
import dao.mocks.ProfileDaoMock;
import dao.mocks.UserDaoMock;
import dao.interfaces.IKweetDao;
import dao.interfaces.IProfileDao;
import dao.interfaces.IUserDao;
import domain.Kweet;
import domain.User;
import util.MockFactory;
import util.MockService;
import view_models.KweeterData;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class KweeterDataServiceTest {

    private static IUserDao userDao;
    private static IKweetDao kweetDao;
    private static IProfileDao profileDao;

    private static KweeterDataService service;

    @BeforeClass
    public static void setUp() {
        userDao = new UserDaoMock();
        kweetDao = new KweetDaoMock();
        profileDao = new ProfileDaoMock();

        service = new KweeterDataService();
        service.setUserDao(userDao);
        service.setKweetDao(kweetDao);
        service.setProfileDao(profileDao);
    }

    @After
    public void tearDown() {
        MockService.renewMockData();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void getKweeterData() throws UserNotFoundException {
        // Setup
        User hank = (User) MockFactory.createMocks(User.class, 1, "username", "henk1").get(0);
        List<User> following = (List<User>) MockFactory.createMocks(User.class, 3);
        List<User> follower = (List<User>) MockFactory.createMocks(User.class, 4);
        List<Kweet> kweets = (List<Kweet>) MockFactory.createMocks(Kweet.class, 5, "sender", hank);
        for (int i = 0; i < kweets.size(); i++) {
            kweets.get(i).setPublicationDate(getDateDaysAgo(i + 1));
        }
        userDao.create(hank);
        hank.setFollowing(userDao.create(following));
        hank.setFollowers(userDao.create(follower));
        hank.setKweets(kweetDao.create(kweets));

        // Get Kweeter data
        KweeterData kweeterData = service.getKweeterData(hank.getUsername());

        // Asserts
        Assert.assertEquals("Follower size is 4", 4, kweeterData.getFollowers().size());
        Assert.assertEquals("Following size is 3", 3, kweeterData.getFollowing().size());
        Assert.assertEquals("Kweets size is 5", 5, kweeterData.getTotalKweets());
        Assert.assertEquals("Publication date isn't from latest kweet", kweets.get(0).getPublicationDate(), kweeterData.getLastKweetDate());
        Assert.assertEquals("Message isn't from latest kweet", kweets.get(0).getMessage(), kweeterData.getLastKweetMessage());
    }

    @Test(expected = UserNotFoundException.class)
    @SuppressWarnings("unchecked")
    public void getKweeterData_nullUser() throws UserNotFoundException {
        // Setup
        User hank = (User) MockFactory.createMocks(User.class, 1, "username", "henk2").get(0);

        // Get Kweeter data
        service.getKweeterData(hank.getUsername());
    }

    private Date getDateDaysAgo(int numberOfDays) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, -1 * Math.abs(numberOfDays));

        return cal.getTime();
    }
}
