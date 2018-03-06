package DAO;

import DAO.Mock.*;
import DaoInterfaces.*;
import Domain.Hashtag;
import Domain.Kweet;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.*;

public class HashtagDaoTest {


    private static IHashtagDao hashtagDao;
    private static IKweetDao kweetDao;
    private static IUserDao userDao;

    @BeforeClass
    public static void Init() {
        userDao = new UserDaoMock();
        kweetDao = new KweetDaoMock(userDao.findAll());
        hashtagDao = new HashtagDaoMock(kweetDao.findAll());
    }

    @Test
    public void findAllTest() {
        // Set status before
        Collection<Hashtag> hashtagsBefore = new ArrayList<Hashtag>(hashtagDao.findAll());

        // Insert new hashtag
        Hashtag mockHashtag = new Hashtag("mockHashtag");
        hashtagDao.insertHashtag(mockHashtag);

        // Check status after
        Collection<Hashtag> hashtagsAfter = hashtagDao.findAll();
        Assert.assertEquals("Returns list with size + 1",
                hashtagsBefore.size() + 1, hashtagsAfter.size());
        Assert.assertTrue("New hashtag has been added", hashtagsAfter.contains(mockHashtag));
    }

    // Gekke test?
    @Test
    public void findByIdTest() {
        long id = 999999;

        // Insert new hashtag
        Hashtag mockHashtag = new Hashtag(id, "mockHashtag");
        hashtagDao.insertHashtag(mockHashtag);

        // Check fetched hashtag
        Hashtag fetchedHashtag = hashtagDao.findById(id);
        Assert.assertSame("Fetched hashtag is the same as the mocked one", mockHashtag, fetchedHashtag);
    }

    @Test
    public void findByNameTest() {
        String name = "myHashtag123";

        // Insert new hashtag
        Hashtag mockHashtag = new Hashtag(name);
        hashtagDao.insertHashtag(mockHashtag);

        // Check fetched hashtag
        Hashtag fetchedHashtag = hashtagDao.findByName(name);
        Assert.assertEquals("Fetched hashtag is the same as the mocked one", mockHashtag, fetchedHashtag);
    }

    @Test
    public void insertHashtagTest() {
        // Insert new hashtag
        Hashtag mockHashtag = new Hashtag(999999, "mockHashtag");
        hashtagDao.insertHashtag(mockHashtag);

        // Check hashtag list contains new hashtag
        Assert.assertTrue("New hashtag has been added", hashtagDao.findAll().contains(mockHashtag));
    }

    @Test
    public void updateHashtagTest() {
        String newName = "mockHashtag123";

        // Insert new hashtag
        Hashtag mockHashtag = new Hashtag(999999, "mockHashtag");
        hashtagDao.insertHashtag(mockHashtag);

        // Update new hashtag
        mockHashtag.setName(newName);
        hashtagDao.updateHashtag(mockHashtag);

        // Check hashtag list contains new hashtag
        Assert.assertEquals("The name of the hashtag has been changed", newName, mockHashtag.getName());
    }

    @Test
    public void deleteHashtagTest() {
        // Insert new hashtag
        Hashtag mockHashtag = new Hashtag(999999, "mockHashtag");
        hashtagDao.insertHashtag(mockHashtag);

        // Delete inserted hashtag
        hashtagDao.deleteHashtag(mockHashtag);

        // Check hashtag list contains new hashtag
        Assert.assertFalse("New hashtag has been removed", hashtagDao.findAll().contains(mockHashtag));
    }

    @Test
    public void getTrendTest() {
        // Empty existing list
        hashtagDao.findAll().clear();

        // Create mock hashtags
        Hashtag mockHashtag1 = new Hashtag(1, "mockHashtag1");
        Hashtag mockHashtag2 = new Hashtag(2, "mockHashtag2");
        Hashtag mockHashtag3 = new Hashtag(3, "mockHashtag3");
        Hashtag mockHashtag4 = new Hashtag(4, "mockHashtag4");

        // Add mock kweets to hashtags
        mockHashtag1.setTimesUsed(1);
        mockHashtag2.setTimesUsed(8);
        mockHashtag3.setTimesUsed(6);
        mockHashtag4.setTimesUsed(3);

        // Set current trends
        mockHashtag1.setLastUsed(new Date());
        mockHashtag2.setLastUsed(new Date());
        mockHashtag3.setLastUsed(new Date());

        // Set old trend
        mockHashtag4.setLastUsed(getDateWeekAgo());

        // Insert new hashtags
        hashtagDao.insertHashtag(mockHashtag1);
        hashtagDao.insertHashtag(mockHashtag2);
        hashtagDao.insertHashtag(mockHashtag3);
        hashtagDao.insertHashtag(mockHashtag4);

        // Check trend
        List<Hashtag> trend = hashtagDao.getTrend();
        Assert.assertEquals("mockHashtag2 is most trendy", mockHashtag2, trend.get(0));
        Assert.assertEquals("mockHashtag3 is second most trendy", mockHashtag3, trend.get(1));
        Assert.assertEquals("mockHashtag1 is third most trendy", mockHashtag1, trend.get(2));
        Assert.assertFalse("mockHashtag4 isn't a trend", trend.contains(mockHashtag4));
    }

    private Date getDateWeekAgo() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, -30);

        return cal.getTime();
    }
}
