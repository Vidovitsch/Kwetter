package DAO;

import DAO.Mock.*;
import DaoInterfaces.*;
import Domain.Kweet;
import Domain.User;
import Util.MockFactory;
import Util.MockService;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.*;

public class KweetDaoTest {

    private static IKweetDao kweetDao;

    @BeforeClass
    public static void Init() {
        kweetDao = new KweetDaoMock();
    }

    @AfterClass
    public static void tearDown() {
        MockService.resetMockData();
    }

    @Test
    public void findAllTest() {
        // Set status before
        List<Kweet> kweetsBefore = new ArrayList<>(kweetDao.findAll());

        // Insert new kweet
        Kweet mockKweet = new Kweet();
        mockKweet = kweetDao.create(mockKweet);

        // Check status after
        List<Kweet> kweetsAfter = kweetDao.findAll();
        Assert.assertEquals("Returns list with size + 1",
                kweetsBefore.size() + 1, kweetsAfter.size());
        Assert.assertTrue("New hashtag has been added", kweetsAfter.contains(mockKweet));
    }

    @Test
    public void findByIdTest() {
        // Insert new kweet
        Kweet mockKweet = kweetDao.create(new Kweet());

        // Check fetched kweet
        Kweet fetchedKweet = kweetDao.findById(mockKweet.getId());
        Assert.assertEquals("Fetched hashtag is the same as the mocked one", mockKweet, fetchedKweet);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void findBySenderNameTest() {
        // Insert new kweets
        List<Kweet> kweets = (List<Kweet>)MockFactory.createMocks(Kweet.class, 3);
        User user = (User) MockFactory.createMocks(User.class, 1, "name", "Hank").get(0);
        kweets.get(0).setSender(user);
        kweets.get(1).setSender(user);
        kweets = kweetDao.create(kweets);

        // Check for message
        List<Kweet> fetchedKweets = kweetDao.findBySender(user);
        Assert.assertTrue(fetchedKweets.contains(kweets.get(0)));
        Assert.assertTrue(fetchedKweets.contains(kweets.get(1)));
        Assert.assertFalse(fetchedKweets.contains(kweets.get(2)));
    }

    @Test
    public void insertKweetTest() {
        // Insert new hashtag
        Kweet mockKweet = (Kweet)MockFactory.createMocks(Kweet.class, 1).get(0);
        kweetDao.create(mockKweet);

        // Check hashtag list contains new hashtag
        Assert.assertTrue("New kweet has been added", kweetDao.findAll().contains(mockKweet));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void insertKweetsTest() {
        // Insert new hashtag
        List<Kweet> mockKweets = (List<Kweet>)MockFactory.createMocks(Kweet.class, 3);
        kweetDao.create(mockKweets);

        // Check hashtag list contains new hashtag
        Assert.assertTrue("New kweets have been added", kweetDao.findAll().containsAll(mockKweets));
    }

    @Test
    public void updateKweetTest() {
        // Insert new kweet
        Kweet mockKweet = (Kweet)MockFactory.createMocks(Kweet.class, 1).get(0);
        kweetDao.create(mockKweet);

        // Check before
        Assert.assertFalse(mockKweet.getMessage().equals("Kweet"));

        // Update new kweet
        mockKweet.setMessage("Kweet");
        kweetDao.update(mockKweet);

        // Check kweet list contains new message
        Assert.assertEquals("Kweet", mockKweet.getMessage());
    }

    @Test
    public void deleteKweetTest() {
        // Insert new hashtag
        Kweet mockKweet = (Kweet)MockFactory.createMocks(Kweet.class, 1).get(0);
        kweetDao.create(mockKweet);

        // Delete inserted kweet
        kweetDao.remove(mockKweet);

        // Check hashtag list contains new hashtag
        Assert.assertFalse("New kweet has been removed", kweetDao.findAll().contains(mockKweet));
    }
}
