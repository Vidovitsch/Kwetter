package DAO;

import DAO.Mock.*;
import DaoInterfaces.*;
import Domain.Hashtag;
import Domain.Kweet;
import Domain.User;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

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
        Collection<Kweet> kweetsBefore = new ArrayList<Kweet>(kweetDao.findAll());

        // Insert new kweet
        Kweet mockKweet = new Kweet();
        kweetDao.insertKweet(mockKweet);

        // Check status after
        Collection<Kweet> kweetsAfter = kweetDao.findAll();
        Assert.assertEquals("Returns list with size + 1",
                kweetsBefore.size() + 1, kweetsAfter.size());
        Assert.assertTrue("New hashtag has been added", kweetsAfter.contains(mockKweet));
    }

    @Test
    public void findByIdTest() {
        long id = 999995;

        // Insert new hashtag
        Kweet mockKweet = new Kweet();
        mockKweet.setId(id);
        kweetDao.insertKweet(mockKweet);

        // Check fetched hashtag
        Kweet fetchedHashtag = kweetDao.findById(id);
        Assert.assertEquals("Fetched hashtag is the same as the mocked one", mockKweet, fetchedHashtag);
    }

    @Test
    public void findByMessagePartTest() {
        String message = "The quick brown fox jumps over the lazy dog";

        // Create mock kweets
    }

    @Test
    public void findBySenderNameTest() {
        // To Do
    }

    @Test
    public void insertKweetTest() {
        // To Do
    }

    @Test
    public void updateKweetTest() {
        // To Do
    }

    @Test
    public void deleteKweetTest() {
        // To Do
    }

    @Test
    public void getTimelineTest() {
        // To Do
    }

    @Test
    public void searchTest() {
        // To Do
    }
}
