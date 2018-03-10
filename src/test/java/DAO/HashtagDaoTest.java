package DAO;

import DAO.Mock.*;
import DaoInterfaces.*;
import Domain.Hashtag;
import Util.MockFactory;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.*;

public class HashtagDaoTest {


    private static IHashtagDao hashtagDao;

    @BeforeClass
    public static void Init() {
        IUserDao userDao = new UserDaoMock();
        IKweetDao kweetDao = new KweetDaoMock(userDao.findAll());
        hashtagDao = new HashtagDaoMock(kweetDao.findAll());
    }

    @Test
    public void findAllTest() {
        // Set status before
        List<Hashtag> hashtagsBefore = new ArrayList<>(hashtagDao.findAll());

        // Insert new hashtag
        Hashtag mockHashtag = (Hashtag)MockFactory.createMocks(Hashtag.class, 1).get(0);
        hashtagDao.create(mockHashtag);

        // Check status after
        List<Hashtag> hashtagsAfter = hashtagDao.findAll();
        Assert.assertEquals("Returns list with size + 1",
                hashtagsBefore.size() + 1, hashtagsAfter.size());
        Assert.assertTrue("New hashtag has been added", hashtagsAfter.contains(mockHashtag));
    }

    @Test
    public void findByIdTest() {
        // Insert new hashtag
        Hashtag mockHashtag = hashtagDao.create(new Hashtag());

        // Check fetched hashtag
        Hashtag fetchedHashtag = hashtagDao.findById(mockHashtag.getId());
        Assert.assertEquals("Fetched hashtag is the same as the mocked one", mockHashtag, fetchedHashtag);
    }

    @Test
    public void findByNameTest() {
        // Insert new hashtag
        Hashtag mockHashtag = (Hashtag)MockFactory.createMocks(Hashtag.class, 1).get(0);
        mockHashtag = hashtagDao.create(mockHashtag);

        // Check fetched hashtag
        Hashtag fetchedHashtag = hashtagDao.findByName(mockHashtag.getName());
        Assert.assertEquals("Fetched hashtag is the same as the mocked one", mockHashtag, fetchedHashtag);
    }

    @Test
    public void insertHashtagTest() {
        // Insert new hashtag
        Hashtag mockHashtag = (Hashtag)MockFactory.createMocks(Hashtag.class, 1).get(0);
        hashtagDao.create(mockHashtag);

        // Check hashtag list contains new hashtag
        Assert.assertTrue("New hashtag has been added", hashtagDao.findAll().contains(mockHashtag));
    }

    @Test
    public void updateHashtagTest() {
        // Insert new hashtag
        Hashtag mockHashtag = (Hashtag)MockFactory.createMocks(Hashtag.class, 1).get(0);
        mockHashtag = hashtagDao.create(mockHashtag);

        // Check before
        Assert.assertFalse(mockHashtag.getName().equals("Kweet"));

        // Update new hashtag
        mockHashtag.setName("Kweet");
        hashtagDao.update(mockHashtag);

        // Check hashtag list contains new name
        Assert.assertEquals("The name of the hashtag has been changed", "Kweet", mockHashtag.getName());
    }

    @Test
    public void deleteHashtagTest() {
        // Insert new hashtag
        Hashtag mockHashtag = (Hashtag)MockFactory.createMocks(Hashtag.class, 1).get(0);
        mockHashtag = hashtagDao.create(mockHashtag);

        // Delete inserted hashtag
        hashtagDao.remove(mockHashtag);

        // Check hashtag list contains new hashtag
        Assert.assertFalse("New hashtag has been removed", hashtagDao.findAll().contains(mockHashtag));
    }
}
