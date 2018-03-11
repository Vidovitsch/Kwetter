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
import Exception.*;
import Util.MockFactory;
import org.junit.*;

import java.util.ArrayList;
import java.util.List;

public class KweetServiceTest {

    private static IUserDao userDao = new UserDaoMock();
    private static IKweetDao kweetDao = new KweetDaoMock(userDao.findAll());
    private static IHashtagDao hashtagDao = new HashtagDaoMock(kweetDao.findAll());

    private static KweetService service;

    // For the 100%!
    private static KweetService emptyService;

    @BeforeClass
    public static void setUp() {
        service = new KweetService(kweetDao, hashtagDao, userDao);
        emptyService = new KweetService();
    }

    @After
    public void tearDown() { }

    @Test
    public void publishNewKweet() throws UserNotFoundException, InvalidKweetException {
        // Setup
        Kweet kweet = (Kweet) MockFactory.createMocks(Kweet.class, 1).get(0);
        User user = userDao.create((User) MockFactory.createMocks(User.class, 1).get(0));

        // Get kweets befor publish
        List<Kweet> kweets = new ArrayList<>(kweetDao.findAll());

        // Assert before publish
        Assert.assertFalse(kweets.contains(kweet));

        // Publish kweet
        Kweet publishedKweet = service.publish(user.getId(), kweet);

        // Get kweets after publish
        List<Kweet> fetchedKweets = kweetDao.findAll();

        // Asserts after publish
        Assert.assertEquals(kweets.size() + 1, fetchedKweets.size());
        Assert.assertEquals(publishedKweet, kweet);
        Assert.assertTrue(fetchedKweets.contains(kweet));
    }

    @Test(expected = InvalidKweetException.class)
    public void publishKweet_TooManyCharacters() throws UserNotFoundException, InvalidKweetException {
        String message = "This message is too long: sdfhlkasdgjasofjoewfjwafewefewf" +
                "safdasdfasfsdafasdfsdaflkfejalifjdfasfasfasfasfsafasfasdfalesijflasiejflaeifjljlasefijaeslifjalsiefj";

        // Setup
        Kweet kweet = (Kweet) MockFactory.createMocks(Kweet.class, 1, "message", message).get(0);
        User user = userDao.create((User) MockFactory.createMocks(User.class, 1).get(0));

        // Publish kweet
        service.publish(user.getId(), kweet);
    }

    @Test(expected = InvalidKweetException.class)
    public void publishKweet_MessageIsEmpty() throws UserNotFoundException, InvalidKweetException {
        // Setup
        Kweet kweet = (Kweet) MockFactory.createMocks(Kweet.class, 1, "message", "").get(0);
        User user = userDao.create((User) MockFactory.createMocks(User.class, 1).get(0));

        // Publish kweet
        service.publish(user.getId(), kweet);
    }

    @Test(expected = InvalidKweetException.class)
    public void publishKweet_MessageIsNull() throws UserNotFoundException, InvalidKweetException {
        // Setup
        Kweet kweet = (Kweet) MockFactory.createMocks(Kweet.class, 1, "message", null).get(0);
        User user = userDao.create((User) MockFactory.createMocks(User.class, 1).get(0));

        // Publish kweet
        service.publish(user.getId(), kweet);
    }

    @Test(expected = UserNotFoundException.class)
    public void publishKweet_UserIsNull() throws UserNotFoundException, InvalidKweetException {
        // Setup
        Kweet kweet = (Kweet) MockFactory.createMocks(Kweet.class, 1).get(0);

        // Publish kweet
        service.publish(null, kweet);
    }

    @Test
    public void publishKweet_Mentions() throws UserNotFoundException, InvalidKweetException {
        // Setup
        String message = "A message: @Rick @John";
        Kweet kweet = (Kweet) MockFactory.createMocks(Kweet.class, 1, "message", message).get(0);
        User rick = userDao.create((User) MockFactory.createMocks(User.class, 1, "username", "Rick").get(0));
        User john = userDao.create((User) MockFactory.createMocks(User.class, 1, "username", "John").get(0));
        User hank = userDao.create((User) MockFactory.createMocks(User.class, 1, "username", "Hank").get(0));

        // Publish kweet
        Kweet publishedkweet = service.publish(hank.getId(), kweet);

        // Asserts
        List<User> mentions = publishedkweet.getMentions();
        Assert.assertEquals(mentions.size(), 2);
        Assert.assertTrue(mentions.contains(rick));
        Assert.assertTrue(mentions.contains(john));
    }

    @Test(expected = UserNotFoundException.class)
    public void publishKweet_MentionNotFound() throws UserNotFoundException, InvalidKweetException {
        // Setup
        String message = "A message: @Rik @John";
        Kweet kweet = (Kweet) MockFactory.createMocks(Kweet.class, 1, "message", message).get(0);
        User hank = userDao.create((User) MockFactory.createMocks(User.class, 1, "username", "Hank").get(0));
        userDao.create((User) MockFactory.createMocks(User.class, 1, "username", "Rick").get(0));
        userDao.create((User) MockFactory.createMocks(User.class, 1, "username", "John").get(0));

        // Publish kweet
        service.publish(hank.getId(), kweet);
    }

    @Test
    public void publishKweet_Hashtags() throws UserNotFoundException, InvalidKweetException {
        // Setup
        String message = "A message: #test1 #kwetter1";
        Kweet kweet = (Kweet) MockFactory.createMocks(Kweet.class, 1, "message", message).get(0);
        User hank = userDao.create((User) MockFactory.createMocks(User.class, 1).get(0));
        Hashtag test1 = hashtagDao.create((Hashtag) MockFactory.createMocks(Hashtag.class, 1, "name", "test1").get(0));

        // Publish kweet
        Kweet publishedkweet = service.publish(hank.getId(), kweet);

        // Asserts
        List<Hashtag> hashtags = publishedkweet.getHashtags();
        Assert.assertEquals(hashtags.size(), 2);

        Assert.assertTrue(hashtags.contains(test1));
        Assert.assertEquals(hashtags.get(1).getName(), "kwetter1");
    }

    @Test
    public void updateKweet() throws UserNotFoundException, InvalidKweetException {
        // Setup
        String firstMessage = "A kweet message";
        String secondMessage = "Another kweet message";
        Kweet kweet = (Kweet) MockFactory.createMocks(Kweet.class, 1, "message", firstMessage).get(0);
        User user = userDao.create((User) MockFactory.createMocks(User.class, 1).get(0));

        // Publish kweet
        Kweet publishedKweet = service.publish(user.getId(), kweet);

        // Assert before update
        Assert.assertEquals(firstMessage, kweet.getMessage());

        // Update kweet
        publishedKweet.setMessage(secondMessage);
        Kweet updatedKweet = service.publish(user.getId(), kweet.getId());

        // Assert after update
        Assert.assertEquals(secondMessage, updatedKweet.getMessage());
    }

    @Test
    public void deleteExistingKweet() throws UserNotFoundException, InvalidKweetException {
        // Setup
        Kweet kweet = (Kweet) MockFactory.createMocks(Kweet.class, 1).get(0);
        User user = userDao.create((User) MockFactory.createMocks(User.class, 1).get(0));

        // Publish kweet
        Kweet publishedKweet = service.publish(user.getId(), kweet);

        // Assert before
        Assert.assertTrue(kweetDao.findAll().contains(publishedKweet));

        // Delelete kweet
        boolean success = service.delete(publishedKweet.getId());
        Assert.assertTrue(success);

        // Assert after
        Assert.assertFalse(kweetDao.findAll().contains(publishedKweet));
    }

    @Test
    public void deleteNonExistingKweet() {
        // Setup
        List<Kweet> kweets1 = new ArrayList<>(kweetDao.findAll());

        // Delelete kweet
        boolean success = service.delete((long) -1);
        Assert.assertFalse(success);

        // Assert
        List<Kweet> kweets2 = new ArrayList<>(kweetDao.findAll());
        Assert.assertEquals(kweets1.size(), kweets2.size());
    }

    @Test
    public void giveHeart() throws UserNotFoundException, InvalidKweetException {
        // Setup
        Kweet kweet = (Kweet) MockFactory.createMocks(Kweet.class, 1).get(0);
        User user = userDao.create((User) MockFactory.createMocks(User.class, 1).get(0));

        // Assert before
        Assert.assertEquals("Kweet has 0 hearts", 0, kweet.getHearts().size());

        // Publish kweet
        service.publish(user.getId(), kweet);

        // Give heart to kweet
        kweet = service.giveHeart(user.getId(), kweet.getId());

        // Assert after
        Assert.assertEquals("Kweet has 1 heart", 1, kweet.getHearts().size());
        Assert.assertTrue("User gave kweet 1 heart", kweet.getHearts().contains(user));
        Assert.assertTrue("User gave kweet 1 heart", user.getHearts().contains(kweet));
    }

    @Test(expected = NullPointerException.class)
    public void giveHeart_KweetNull() {
        // Setup
        User user = userDao.create((User) MockFactory.createMocks(User.class, 1).get(0));

        // Give heart to kweet
        service.giveHeart(user.getId(), (long) -1);
    }

    @Test(expected = NullPointerException.class)
    public void giveHeart_UserNull() throws UserNotFoundException, InvalidKweetException {
        // Setup
        Kweet kweet = (Kweet) MockFactory.createMocks(Kweet.class, 1).get(0);
        User user = userDao.create((User) MockFactory.createMocks(User.class, 1).get(0));

        // Publish kweet
        service.publish(user.getId(), kweet);

        // Give heart to kweet
        service.giveHeart((long) -1, kweet.getId());
    }

    @Test
    public void search() throws UserNotFoundException, InvalidKweetException {
        // Setup
        String message = "A message: #test1 #kweet1";
        User kwet = userDao.create((User) MockFactory.createMocks(User.class, 1, "username", "Leet").get(0));
        User rick = userDao.create((User) MockFactory.createMocks(User.class, 1, "username", "Rick").get(0));
        Kweet kweet1 = (Kweet) MockFactory.createMocks(Kweet.class, 1).get(0);
        Kweet kweet2 = (Kweet) MockFactory.createMocks(Kweet.class, 1, "message", message).get(0);
        Kweet kweet3 = (Kweet) MockFactory.createMocks(Kweet.class, 1, "message", "Somemessage").get(0);

        // Publish kweets
        kweet1 = service.publish(kwet.getId(), kweet1);
        kweet2 = service.publish(rick.getId(), kweet2);
        service.publish(rick.getId(), kweet3);

        // Search
        List<Kweet> results = service.search("eet");

        // Asserts
        Assert.assertEquals("Two kweets met the given term", 2, results.size());
        Assert.assertTrue("The term 'et' was met by kweet1", results.contains(kweet1));
        Assert.assertTrue("The term 'et' was met by kweet2", results.contains(kweet2));
    }

    @Test
    public void search_empty() throws UserNotFoundException, InvalidKweetException {
        // Setup
        String message = "A message: #test1 #kweet1";
        User kwet = userDao.create((User) MockFactory.createMocks(User.class, 1, "username", "Leet").get(0));
        User rick = userDao.create((User) MockFactory.createMocks(User.class, 1, "username", "Rick").get(0));
        Kweet kweet1 = (Kweet) MockFactory.createMocks(Kweet.class, 1).get(0);
        Kweet kweet2 = (Kweet) MockFactory.createMocks(Kweet.class, 1, "message", message).get(0);
        Kweet kweet3 = (Kweet) MockFactory.createMocks(Kweet.class, 1, "message", "Somemessage").get(0);

        // Publish kweets
        service.publish(kwet.getId(), kweet1);
        service.publish(rick.getId(), kweet2);
        service.publish(rick.getId(), kweet3);

        // Search
        List<Kweet> results = service.search("xqxq");

        // Asserts
        Assert.assertEquals("Two kweets met the given term", 0, results.size());
    }
}
