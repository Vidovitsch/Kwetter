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
import Util.MockService;
import org.junit.*;

import java.util.ArrayList;
import java.util.List;

public class KweetServiceTest {

    private static IUserDao userDao;
    private static IKweetDao kweetDao;
    private static IHashtagDao hashtagDao;

    private static KweetService service;

    @BeforeClass
    public static void setUp() {
        userDao = new UserDaoMock();
        kweetDao = new KweetDaoMock();
        hashtagDao = new HashtagDaoMock();

        service = new KweetService();
        service.setUserDao(userDao);
        service.setKweetDao(kweetDao);
        service.setHashtagDao(hashtagDao);
    }

    @AfterClass
    public static void tearDown() {
        MockService.resetMockData();
    }

    @Test
    public void publishNewKweet() throws UserNotFoundException, InvalidKweetException {
        // Setup
        User user = userDao.create(
                (User) MockFactory.createMocks(User.class, 1, "username", "publishNewKweet").get(0));

        // Get kweets befor publish
        List<Kweet> kweets = new ArrayList<>(kweetDao.findAll());

        // Publish kweet
        Kweet publishedKweet = service.publish(user.getUsername(), "A message");

        // Get kweets after publish
        List<Kweet> fetchedKweets = kweetDao.findAll();

        // Asserts after publish
        Assert.assertEquals(kweets.size() + 1, fetchedKweets.size());
        Assert.assertTrue(fetchedKweets.contains(publishedKweet));
    }

    @Test(expected = InvalidKweetException.class)
    public void publishKweet_TooManyCharacters() throws UserNotFoundException, InvalidKweetException {
        String message = "This message is too long: sdfhlkasdgjasofjoewfjwafewefewf" +
                "safdasdfasfsdafasdfsdaflkfejalifjdfasfasfasfasfsafasfasdfalesijflasiejflaeifjljlasefijaeslifjalsiefj";

        // Setup
        User user = userDao.create(
                (User) MockFactory.createMocks(User.class, 1, "username", "publishKweet_TooManyCharacters").get(0));

        // Publish kweet
        service.publish(user.getUsername(), message);
    }

    @Test(expected = InvalidKweetException.class)
    public void publishKweet_MessageIsEmpty() throws UserNotFoundException, InvalidKweetException {
        // Setup
        User user = userDao.create(
                (User) MockFactory.createMocks(User.class, 1, "username", "publishKweet_MessageIsEmpty").get(0));

        // Publish kweet
        service.publish(user.getUsername(), "");
    }

    @Test(expected = InvalidKweetException.class)
    public void publishKweet_MessageIsNull() throws UserNotFoundException, InvalidKweetException {
        // Setup
        User user = userDao.create(
                (User) MockFactory.createMocks(User.class, 1, "username", "publishKweet_MessageIsNull").get(0));

        // Publish kweet
        service.publish(user.getUsername(), null);
    }

    @Test(expected = UserNotFoundException.class)
    public void publishKweet_UserIsNull() throws UserNotFoundException, InvalidKweetException {
        // Publish kweet
        service.publish(null, "A message");
    }

    @Test
    public void publishKweet_Mentions() throws UserNotFoundException, InvalidKweetException {
        // Setup
        User rick = userDao.create(
                (User) MockFactory.createMocks(User.class, 1, "username", "Rick").get(0));
        User john = userDao.create(
                (User) MockFactory.createMocks(User.class, 1, "username", "John").get(0));
        User hank = userDao.create(
                (User) MockFactory.createMocks(User.class, 1, "username", "Hank").get(0));

        // Publish kweet
        Kweet publishedkweet = service.publish(hank.getUsername(), "A message: @Rick @John");

        // Asserts
        List<User> mentions = publishedkweet.getMentions();
        Assert.assertEquals(mentions.size(), 2);
        Assert.assertTrue(mentions.contains(rick));
        Assert.assertTrue(mentions.contains(john));
    }

    @Test(expected = UserNotFoundException.class)
    public void publishKweet_MentionNotFound() throws UserNotFoundException, InvalidKweetException {
        // Setup
        User hank = userDao.create(
                (User) MockFactory.createMocks(User.class, 1, "username", "Hank").get(0));
        userDao.create(
                (User) MockFactory.createMocks(User.class, 1, "username", "Rick").get(0));
        userDao.create(
                (User) MockFactory.createMocks(User.class, 1, "username", "John").get(0));

        // Publish kweet
        service.publish(hank.getUsername(), "A message: @Rik @John");
    }

    @Test
    public void publishKweet_Hashtags() throws UserNotFoundException, InvalidKweetException {
        // Setup
        User hank = userDao.create(
                (User) MockFactory.createMocks(User.class, 1, "username", "publishKweet_Hashtags").get(0));
        Hashtag test1 = hashtagDao.create(
                (Hashtag) MockFactory.createMocks(Hashtag.class, 1, "name", "test1").get(0));

        // Publish kweet
        Kweet publishedkweet = service.publish(hank.getUsername(), "A message: #test1 #kwetter1");

        // Asserts
        List<Hashtag> hashtags = publishedkweet.getHashtags();
        Assert.assertEquals(hashtags.size(), 2);

        Assert.assertTrue(hashtags.contains(test1));
        Assert.assertEquals(hashtags.get(1).getName(), "kwetter1");
    }

    @Test
    public void updateKweet() throws UserNotFoundException, InvalidKweetException, KweetNotFoundException {
        // Setup
        String firstMessage = "A kweet message";
        String secondMessage = "Another kweet message";
        User user = userDao.create(
                (User) MockFactory.createMocks(User.class, 1, "username", "updateKweet").get(0));

        // Publish kweet
        Kweet publishedKweet = service.publish(user.getUsername(), firstMessage);

        // Assert before update
        Assert.assertEquals(firstMessage, publishedKweet.getMessage());

        // Update kweet
        Kweet updatedKweet = service.publish(user.getId(), publishedKweet.getId(), secondMessage);

        // Assert after update
        Assert.assertEquals(secondMessage, updatedKweet.getMessage());
    }

    @Test(expected = KweetNotFoundException.class)
    public void updateKweet_NullKweet() throws UserNotFoundException, InvalidKweetException, KweetNotFoundException {
        // Setup
        User user = userDao.create(
                (User) MockFactory.createMocks(User.class, 1).get(0));

        // Assert update kweet
        service.publish(user.getId(), (long) -1, "A message");
    }

    @Test
    public void deleteExistingKweet() throws UserNotFoundException, InvalidKweetException {
        // Setup
        User user = userDao.create(
                (User) MockFactory.createMocks(User.class, 1, "username", "deleteExistingKweet").get(0));

        // Publish kweet
        Kweet publishedKweet = service.publish(user.getUsername(), "A message");

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
    public void giveHeart() throws UserNotFoundException, KweetNotFoundException, InvalidKweetException {
        // Setup
        Kweet kweet =
                (Kweet) MockFactory.createMocks(Kweet.class, 1).get(0);
        User user = userDao.create(
                (User) MockFactory.createMocks(User.class, 1, "username", "giveHeart").get(0));

        // Assert before
        Assert.assertEquals("Kweet has 0 hearts", 0, kweet.getHearts().size());

        // Publish kweet
        Kweet publishedKweet = service.publish(user.getUsername(), "A messaage");

        // Give heart to kweet
        service.giveHeart(user.getId(), publishedKweet.getId());

        // Assert after
        Assert.assertEquals("Kweet has 1 heart", 1, publishedKweet.getHearts().size());
        Assert.assertTrue("User gave kweet 1 heart", publishedKweet.getHearts().contains(user));
        Assert.assertTrue("User gave kweet 1 heart", user.getHearts().contains(publishedKweet));
    }

    @Test(expected = KweetNotFoundException.class)
    public void giveHeart_KweetNull() throws UserNotFoundException, KweetNotFoundException {
        // Setup
        User user = userDao.create(
                (User) MockFactory.createMocks(User.class, 1).get(0));

        // Give heart to kweet
        service.giveHeart(user.getId(), (long) -1);
    }

    @Test(expected = UserNotFoundException.class)
    public void giveHeart_UserNull() throws UserNotFoundException, KweetNotFoundException, InvalidKweetException {
        // Setup
        Kweet kweet =
                (Kweet) MockFactory.createMocks(Kweet.class, 1).get(0);
        User user = userDao.create(
                (User) MockFactory.createMocks(User.class, 1, "username", "giveHeart_UserNull").get(0));

        // Publish kweet
        service.publish(user.getUsername(), "A message");

        // Give heart to kweet
        service.giveHeart((long) -1, kweet.getId());
    }

    @Test
    public void search() throws UserNotFoundException, InvalidKweetException {
        // Setup
        User kwet = userDao.create(
                (User) MockFactory.createMocks(User.class, 1, "username", "Laxcxc").get(0));
        User rick = userDao.create(
                (User) MockFactory.createMocks(User.class, 1, "username", "Rick").get(0));

        // Publish kweets
        Kweet kweet1 = service.publish(kwet.getUsername(), "A message");
        Kweet kweet2 = service.publish(rick.getUsername(), "A message: #test1 #xcxc1");
        service.publish(rick.getUsername(), "Somemessage");

        // Search
        List<Kweet> results = service.search("xcxc");

        // Asserts
        Assert.assertEquals("Two kweets met the given term", 2, results.size());
        Assert.assertTrue("The term 'et' was met by kweet1", results.contains(kweet1));
        Assert.assertTrue("The term 'et' was met by kweet2", results.contains(kweet2));
    }

    @Test
    public void search_empty() throws UserNotFoundException, InvalidKweetException {
        // Setup
        User kwet = userDao.create(
                (User) MockFactory.createMocks(User.class, 1, "username", "Let").get(0));
        User rick = userDao.create(
                (User) MockFactory.createMocks(User.class, 1, "username", "Rick1").get(0));

        // Publish kweets
        service.publish(kwet.getUsername(), "A message");
        service.publish(rick.getUsername(), "A message: #test1 #kwet1");
        service.publish(rick.getUsername(), "Somemessage");

        // Search
        List<Kweet> results = service.search("xqxq");

        // Asserts
        Assert.assertEquals("Two kweets met the given term", 0, results.size());
    }
}
