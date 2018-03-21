package dao_test;

import dao.implementations.KweetDaoImpl;
import dao.implementations.UserDaoImpl;
import dao.interfaces.*;
import domain.Kweet;
import domain.User;
import util.MockFactory;
import util.MockService;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.*;

public class KweetDaoTest {

    private static IKweetDao kweetDao;
    private static IUserDao userDao;

    @BeforeClass
    public static void Init() {
        kweetDao = new KweetDaoImpl("KwetterPU_test");
        userDao = new UserDaoImpl("KwetterPU_test");
    }

    @AfterClass
    public static void tearDown() {
        MockService.renewMockData();
    }

    @Test
    public void findAllTest() {
        // Set status before
        List<Kweet> kweetsBefore = new ArrayList<>(kweetDao.findAll());

        // Insert new kweet
        Kweet mockKweet = (Kweet)MockFactory.createMocks(Kweet.class, 1).get(0);
        User user = (User) MockFactory.createMocks(User.class, 1, "name", "Hank").get(0);

        beginUserTransaction();
        userDao.create(user);
        endUserTransaction();

        mockKweet.setSender(user);

        beginKweetTransaction();
        mockKweet = kweetDao.create(mockKweet);
        endKweetTransaction();

        // Check status after
        List<Kweet> kweetsAfter = kweetDao.findAll();
        Assert.assertEquals("Returns list with size + 1",
                kweetsBefore.size() + 1, kweetsAfter.size());
        Assert.assertTrue("New hashtag has been added", kweetsAfter.contains(mockKweet));
    }

    @Test
    public void findByIdTest() {
        // Insert new kweet
        Kweet mockKweet = (Kweet)MockFactory.createMocks(Kweet.class, 1).get(0);
        User user = (User) MockFactory.createMocks(User.class, 1, "name", "Hank").get(0);

        beginUserTransaction();
        userDao.create(user);
        endUserTransaction();

        mockKweet.setSender(user);

        beginKweetTransaction();
        mockKweet = kweetDao.create(mockKweet);
        endKweetTransaction();

        // Check fetched kweet
        Kweet fetchedKweet = kweetDao.findById(mockKweet.getId());
        Assert.assertEquals("Fetched hashtag is the same as the mocked one", mockKweet, fetchedKweet);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void findBySenderTest() {
        // Insert new kweets
        List<Kweet> kweets = MockService.toKweets(MockFactory.createMocks(Kweet.class, 2));
        User user = (User) MockFactory.createMocks(User.class, 1, "name", "Hank").get(0);

        beginUserTransaction();
        userDao.create(user);
        endUserTransaction();

        kweets.get(0).setSender(user);
        kweets.get(1).setSender(user);

        beginKweetTransaction();
        kweets = kweetDao.create(kweets);
        endKweetTransaction();

        // Check for message
        List<Kweet> fetchedKweets = kweetDao.findBySender(user);
        Assert.assertTrue(fetchedKweets.contains(kweets.get(0)));
        Assert.assertTrue(fetchedKweets.contains(kweets.get(1)));
    }

    @Test
    public void insertKweetTest() {
        // Insert new hashtag
        Kweet mockKweet = (Kweet)MockFactory.createMocks(Kweet.class, 1).get(0);
        User user = (User) MockFactory.createMocks(User.class, 1, "name", "Hank").get(0);

        beginUserTransaction();
        userDao.create(user);
        endUserTransaction();

        mockKweet.setSender(user);

        beginKweetTransaction();
        kweetDao.create(mockKweet);
        endKweetTransaction();

        // Check hashtag list contains new hashtag
        Assert.assertTrue("New kweet has been added", kweetDao.findAll().contains(mockKweet));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void insertKweetsTest() {
        // Insert new hashtag
        List<Kweet> mockKweets = MockService.toKweets(MockFactory.createMocks(Kweet.class, 3));
        User user = (User) MockFactory.createMocks(User.class, 1, "name", "Hank12").get(0);

        beginUserTransaction();
        userDao.create(user);
        endUserTransaction();

        for(Kweet k : mockKweets){
            k.setSender(userDao.findById((long)1));
        }

        beginKweetTransaction();
        kweetDao.create(mockKweets);
        endKweetTransaction();

        // Check hashtag list contains new hashtag
        Assert.assertTrue("New kweets have not been added", kweetDao.findAll().containsAll(mockKweets));
    }

    @Test
    public void updateKweetTest() {
        // Insert new kweet
        Kweet mockKweet = (Kweet)MockFactory.createMocks(Kweet.class, 1).get(0);
        User user = (User) MockFactory.createMocks(User.class, 1, "name", "Hank").get(0);

        beginUserTransaction();
        userDao.create(user);
        endUserTransaction();

        mockKweet.setSender(user);

        beginKweetTransaction();
        kweetDao.create(mockKweet);
        endKweetTransaction();

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
        User user = (User) MockFactory.createMocks(User.class, 1, "name", "Hank").get(0);

        beginUserTransaction();
        userDao.create(user);
        endUserTransaction();

        mockKweet.setSender(user);

        beginKweetTransaction();
        kweetDao.create(mockKweet);
        endKweetTransaction();

        // Delete inserted kweet
        beginKweetTransaction();
        kweetDao.remove(mockKweet);
        endKweetTransaction();

        // Check hashtag list contains new hashtag
        Assert.assertFalse("New kweet has been removed", kweetDao.findAll().contains(mockKweet));
    }

    private void beginUserTransaction() {
        if (userDao.getEntityManager() != null) {
            userDao.getEntityManager().getTransaction().begin();
        }
    }

    private void endUserTransaction() {
        if (userDao.getEntityManager() != null) {
            userDao.getEntityManager().getTransaction().commit();
        }
    }

    private void beginKweetTransaction() {
        if (kweetDao.getEntityManager() != null) {
            kweetDao.getEntityManager().getTransaction().begin();
        }
    }

    private void endKweetTransaction() {
        if (kweetDao.getEntityManager() != null) {
            kweetDao.getEntityManager().getTransaction().commit();
        }
    }
}
