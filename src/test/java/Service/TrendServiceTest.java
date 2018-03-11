package Service;

import DAO.Mock.HashtagDaoMock;
import DAO.Mock.KweetDaoMock;
import DAO.Mock.UserDaoMock;
import DaoInterfaces.IHashtagDao;
import DaoInterfaces.IKweetDao;
import DaoInterfaces.IUserDao;
import Domain.Hashtag;
import Domain.Kweet;
import Domain.User;
import Util.MockFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import Exception.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TrendServiceTest {

    private static IUserDao userDao = new UserDaoMock();
    private static IKweetDao kweetDao = new KweetDaoMock();
    private static IHashtagDao hashtagDao = new HashtagDaoMock();

    private static TrendService service;
    private static KweetService kweetService;

    // For the 100%!
    private static TrendService emptyService;

    @BeforeClass
    public static void setUp() {
        service = new TrendService(hashtagDao);
        kweetService = new KweetService(kweetDao, hashtagDao, userDao);
        emptyService = new TrendService();
    }

    @After
    public void tearDown() { }

    @Test
    public void getTrend() throws UserNotFoundException, InvalidKweetException {
        // Clear list
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

        Kweet kweet1 = (Kweet) MockFactory.createMocks(Kweet.class, 1, "message", message1).get(0);
        Kweet kweet2 = (Kweet) MockFactory.createMocks(Kweet.class, 1, "message", message2).get(0);
        Kweet kweet3 = (Kweet) MockFactory.createMocks(Kweet.class, 1, "message", message3).get(0);
        kweetDao.create((Kweet) MockFactory.createMocks(Kweet.class, 1, "message", message4).get(0));

        // Publish
        kweetService.publish(rick.getId(), kweet1);
        kweetService.publish(rick.getId(), kweet2);
        kweetService.publish(rick.getId(), kweet3);

        // Get trend
        List<String> names = service.get();

        // Asserts
        Assert.assertEquals("Trend consists of 3 hashtags", 3, names.size());
        Assert.assertEquals("#snowball is most used", 3, h1.getTimesUsed());
        Assert.assertEquals("#snowball is most used", h1.getName(), names.get(0));
        Assert.assertEquals("#icecube is second most used", 2, h2.getTimesUsed());
        Assert.assertEquals("#icecube is second most used", h2.getName(), names.get(1));
        Assert.assertEquals("#kitty is most used", 1, h3.getTimesUsed());
        Assert.assertEquals("#kitty is most used", h3.getName(), names.get(2));
        Assert.assertEquals("#coding is used ten times, but is too old", 10, oldTag.getTimesUsed());
        Assert.assertFalse("#coding is used ten times, but is too old", names.contains(oldTag.getName()));
    }

    private Date getDateWeekAgo() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, -7);

        return cal.getTime();
    }
}
