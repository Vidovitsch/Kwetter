package DAO;

import DAO.Mock.*;
import DaoInterfaces.*;
import Domain.Hashtag;
import Domain.Kweet;
import Domain.User;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.*;

public class KweetDaoTest {

    private static IHashtagDao hashtagDao;
    private static IKweetDao kweetDao;
    private static IUserDao userDao;

    @BeforeClass
    public static void Init() {
        userDao = new UserDaoMock();
        kweetDao = new KweetDaoMock(userDao.findAll());
    }

    @Test
    public void findAllTest() {
        // Set status before
        List<Kweet> kweetsBefore = new ArrayList<Kweet>(kweetDao.findAll());

        // Insert new kweet
        Kweet mockKweet = new Kweet();
        kweetDao.insertKweet(mockKweet);

        // Check status after
        List<Kweet> kweetsAfter = kweetDao.findAll();
        Assert.assertEquals("Returns list with size + 1",
                kweetsBefore.size() + 1, kweetsAfter.size());
        Assert.assertTrue("New hashtag has been added", kweetsAfter.contains(mockKweet));

        // Remove mock kweet (cleanup)
        kweetDao.deleteKweet(mockKweet);
    }

    @Test
    public void findByIdTest() {
        long id = 999999;

        // Insert new hashtag
        Kweet mockKweet = new Kweet();
        mockKweet.setId(id);
        kweetDao.insertKweet(mockKweet);

        // Check fetched hashtag
        Kweet fetchedKweet = kweetDao.findById(id);
        Assert.assertEquals("Fetched hashtag is the same as the mocked one", mockKweet, fetchedKweet);

        // Remove mock kweet (cleanup)
        kweetDao.deleteKweet(fetchedKweet);
    }

    @Test
    public void findByMessagePartTest() {
        String message1 = "The quick brown fox jumps over the lazy dog";
        String message2 = "I'm a brown fox";

        // Create mock kweets
        Kweet mockKweet1 = new Kweet(-1,new User(), message1);
        Kweet mockKweet2 = new Kweet(-1,new User(), message2);
        Kweet mockKweet3 = new Kweet(-1,new User(), "mockMessage1");

        // Insert new hashtags
        kweetDao.insertKweet(mockKweet1);
        kweetDao.insertKweet(mockKweet2);
        kweetDao.insertKweet(mockKweet3);

        // Check for message
        List<Kweet> fetchedKweets = kweetDao.findByMessagePart("brown fox");
        Assert.assertEquals("First message has been found with message part 'brown fox'",
                mockKweet1, fetchedKweets.get(0));
        Assert.assertEquals("Second message has been found with message part 'brown fox'",
                mockKweet2, fetchedKweets.get(1));
        Assert.assertFalse("The fetched kweets won't contain mockKweet3", fetchedKweets.contains(mockKweet3));

        // Remove mock kweets (cleanup)
        kweetDao.deleteKweet(mockKweet1);
        kweetDao.deleteKweet(mockKweet2);
        kweetDao.deleteKweet(mockKweet3);
    }

    @Test
    public void findBySenderNameTest() {
        String name = "Hank";

        // Create mock kweets
        Kweet mockKweet1 = new Kweet(-1, new User(-1,name), "mockMessage1");
        Kweet mockKweet2 = new Kweet(-1, new User(-1,name), "mockMessage2");
        Kweet mockKweet3 = new Kweet(-1, new User(-1,"Jack"), "mockMessage3");

        // Insert new hashtags
        kweetDao.insertKweet(mockKweet1);
        kweetDao.insertKweet(mockKweet2);
        kweetDao.insertKweet(mockKweet3);

        // Check for message
        List<Kweet> fetchedKweets = kweetDao.findBySenderName(name);
        Assert.assertEquals("First message has been found with 'Hank' as sender",
                mockKweet1, fetchedKweets.get(0));
        Assert.assertEquals("Second message has been found with 'Hank' as sender",
                mockKweet2, fetchedKweets.get(1));
        Assert.assertFalse("The fetched kweets won't contain mockKweet3", fetchedKweets.contains(mockKweet3));

        // Remove mock kweets (cleanup)
        kweetDao.deleteKweet(mockKweet1);
        kweetDao.deleteKweet(mockKweet2);
        kweetDao.deleteKweet(mockKweet3);
    }

    @Test
    public void insertKweetTest() {
        // Insert new hashtag
        Kweet mockKweet = new Kweet();
        mockKweet.setId(999999);
        kweetDao.insertKweet(mockKweet);

        // Check hashtag list contains new hashtag
        Assert.assertTrue("New kweet has been added", kweetDao.findAll().contains(mockKweet));

        // Remove mock kweet (cleanup)
        kweetDao.deleteKweet(mockKweet);
    }

    @Test
    public void updateKweetTest() {
        String newMessage = "mockKweet123";

        // Insert new kweet
        Kweet mockKweet = new Kweet();
        mockKweet.setId(999999);
        kweetDao.insertKweet(mockKweet);

        // Update new kweet
        mockKweet.setMessage(newMessage);
        kweetDao.updateKweet(mockKweet);

        // Check kweet list contains new message
        Assert.assertEquals("The message of the kweet has been changed", newMessage, mockKweet.getMessage());

        // Remove mock kweet (cleanup)
        kweetDao.deleteKweet(mockKweet);
    }

    @Test
    public void deleteKweetTest() {
        // Insert new hashtag
        Kweet mockKweet = new Kweet();
        mockKweet.setId(999999);
        kweetDao.insertKweet(mockKweet);

        // Delete inserted kweet
        kweetDao.deleteKweet(mockKweet);

        // Check hashtag list contains new hashtag
        Assert.assertFalse("New kweet has been removed", kweetDao.findAll().contains(mockKweet));
    }

    @Test
    public void getTimelineTest() {
        // Get mock users
        User mockUser1 = new User(-1,"Hank");
        User mockUser2 = new User(-1,"Jack");
        User mockUser3 = new User(-1,"Frank");

        // Hank, Jack and Frank send both 2 mock kweets
        List<Kweet> mockKweets1 = new ArrayList<Kweet>();
        List<Kweet> mockKweets2 = new ArrayList<Kweet>();
        List<Kweet> mockKweets3 = new ArrayList<Kweet>();
        Kweet mockKweet1 = new Kweet();
        Kweet mockKweet2 = new Kweet();
        Kweet mockKweet3 = new Kweet();
        Kweet mockKweet4 = new Kweet();
        Kweet mockKweet5 = new Kweet();
        Kweet mockKweet6 = new Kweet();
        mockKweets1.add(mockKweet1);
        mockKweets1.add(mockKweet2);
        mockKweets2.add(mockKweet3);
        mockKweets2.add(mockKweet4);
        mockKweets3.add(mockKweet5);
        mockKweets3.add(mockKweet6);

        mockUser1.setKweets(mockKweets1);
        mockUser2.setKweets(mockKweets2);
        mockUser3.setKweets(mockKweets3);

        // Set publication dates
        mockKweet1.setPublicationDate(getDateDaysAgo(1));
        mockKweet2.setPublicationDate(getDateDaysAgo(3));
        mockKweet3.setPublicationDate(getDateDaysAgo(2));
        mockKweet4.setPublicationDate(getDateDaysAgo(4));

        // Hank follows Jack
        List<User> mockFollowing = new ArrayList<User>();
        mockFollowing.add(mockUser2);
        mockUser1.setFollowing(mockFollowing);

        // Check timeline
        List<Kweet> timelineKweets = kweetDao.getTimeline(mockUser1);
        Assert.assertEquals("Timeline contains mockKweet1 on index 0", mockKweet1.getPublicationDate(), timelineKweets.get(0).getPublicationDate());
        Assert.assertEquals("Timeline contains mockKweet2 on index 2", mockKweet2, timelineKweets.get(2));
        Assert.assertEquals("Timeline contains mockKweet3 on index 1", mockKweet3, timelineKweets.get(1));
        Assert.assertEquals("Timeline contains mockKweet4 on index 3", mockKweet4, timelineKweets.get(3));
        Assert.assertFalse("Timeline doesn't contain mockKweet5", timelineKweets.contains(mockKweet5));
        Assert.assertFalse("Timeline doesn't contain mockKweet6", timelineKweets.contains(mockKweet6));

        // Remove mock kweets (cleanup)
        kweetDao.deleteKweet(mockKweet1);
        kweetDao.deleteKweet(mockKweet2);
        kweetDao.deleteKweet(mockKweet3);
        kweetDao.deleteKweet(mockKweet4);
        kweetDao.deleteKweet(mockKweet5);
        kweetDao.deleteKweet(mockKweet6);
    }

    @Test
    public void searchTest() {
        String sender = "Hank";
        String hashtag = "HashHash";

        // Get mock users
        User mockUser1 = new User(-1,sender);

        // Henk sends 2 mock kweets
        List<Kweet> mockKweets = new ArrayList<Kweet>();
        Kweet mockKweet1 = new Kweet(-1,mockUser1, "mockKweet1");
        Kweet mockKweet2 = new Kweet(-1,mockUser1, "mockKweet2");
        mockKweets.add(mockKweet1);
        mockKweets.add(mockKweet2);
        mockUser1.setKweets(mockKweets);

        // Insert mock kweets
        kweetDao.insertKweet(mockKweet1);
        kweetDao.insertKweet(mockKweet2);

        // Set mock hashtag in mockKweet1
        List<Hashtag> mockHashtags = new ArrayList<Hashtag>();
        Hashtag mockHashtag = new Hashtag(-1,hashtag);
        mockHashtags.add(mockHashtag);
        mockKweet1.setHashtags(mockHashtags);

        // Check search by sender (incomplete term)
        List<Kweet> kweets = kweetDao.search("Han");
        Assert.assertTrue("Result contains mockKweet1", kweets.contains(mockKweet1));
        Assert.assertTrue("Result contains mockKweet2", kweets.contains(mockKweet2));

        // Check search by hashtag (incomplete term)
        kweets = kweetDao.search("Has");
        Assert.assertTrue("Result contains mockKweet1", kweets.contains(mockKweet1));

        // Remove mock kweets (cleanup)
        kweetDao.deleteKweet(mockKweet1);
        kweetDao.deleteKweet(mockKweet2);
    }

    private Date getDateDaysAgo(int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, -days);

        return cal.getTime();
    }
}
