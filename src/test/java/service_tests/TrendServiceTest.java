package service_tests;

import dao.mocks.HashtagDaoMock;
import dao.mocks.KweetDaoMock;
import dao.mocks.UserDaoMock;
import dao.interfaces.IHashtagDao;
import dao.interfaces.IKweetDao;
import dao.interfaces.IUserDao;
import domain.Hashtag;
import domain.Kweet;
import domain.User;
import services.KweetService;
import services.TrendService;
import util.MockFactory;
import util.MockService;
import org.junit.*;
import exceptions.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TrendServiceTest {

    private static IUserDao userDao;
    private static IKweetDao kweetDao;
    private static IHashtagDao hashtagDao;

    private static TrendService service;
    private static KweetService kweetService;

    @BeforeClass
    public static void setUp() {
        userDao = new UserDaoMock();
        kweetDao = new KweetDaoMock();
        hashtagDao = new HashtagDaoMock();

        service = new TrendService();
        service.setHashtagDao(hashtagDao);

        kweetService = new KweetService();
        kweetService.setUserDao(userDao);
        kweetService.setKweetDao(kweetDao);
        kweetService.setHashtagDao(hashtagDao);
    }

    @After
    public void tearDown() {
        MockService.renewMockData();
    }

    @Test
    public void getTrend() throws InvalidKweetException, UserNotFoundException {
        // This test needs a full reset of hashtags
        hashtagDao.findAll().clear();

        // Setup
        String message1 = "#snowball";
        String message2 = "#snowball #icecube";
        String message3 = "#snowball #icecube #kitty";
        String message4 = "#snowball #icecube #kitty #coding";

        User rick = userDao.create((User) MockFactory.createMocks(User.class, 1, "username", "Rick").get(0));

        Hashtag h1 = hashtagDao.create((Hashtag) MockFactory.createMocks(Hashtag.class, 1, "name", "snowball").get(0));
        Hashtag h2 = hashtagDao.create((Hashtag) MockFactory.createMocks(Hashtag.class, 1, "name", "icecube").get(0));
        Hashtag h3 = hashtagDao.create((Hashtag) MockFactory.createMocks(Hashtag.class, 1, "name", "kitty").get(0));
        h1.setTimesUsed(0);
        h2.setTimesUsed(0);
        h3.setTimesUsed(0);

        Hashtag oldTag = (Hashtag) MockFactory.createMocks(Hashtag.class, 1, "name", "coding").get(0);
        oldTag.setLastUsed(getDateWeekAgo());
        oldTag.setTimesUsed(10);
        hashtagDao.create(oldTag);

        kweetDao.create((Kweet) MockFactory.createMocks(Kweet.class, 1, "message", message4).get(0));

        // Publish
        kweetService.create(rick.getUsername(), message1);
        kweetService.create(rick.getUsername(), message2);
        kweetService.create(rick.getUsername(), message3);

        // Get trend
        List<Hashtag> names = service.get();

        // Asserts
        Assert.assertEquals("Trend consists of 3 hashtags", 3, names.size());
        Assert.assertEquals("#snowball is most used", 3, h1.getTimesUsed());
        Assert.assertEquals("#snowball is most used", h1.getName(), names.get(0).getName());
        Assert.assertEquals("#icecube is second most used", 2, h2.getTimesUsed());
        Assert.assertEquals("#icecube is second most used", h2.getName(), names.get(1).getName());
        Assert.assertEquals("#kitty is most used", 1, h3.getTimesUsed());
        Assert.assertEquals("#kitty is most used", h3.getName(), names.get(2).getName());
        Assert.assertEquals("#coding is used ten times, but is too old", 10, oldTag.getTimesUsed());
    }

    private Date getDateWeekAgo() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, -7);

        return cal.getTime();
    }
}
