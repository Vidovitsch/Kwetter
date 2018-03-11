package Service;

import DAO.Mock.HashtagDaoMock;
import DAO.Mock.KweetDaoMock;
import DAO.Mock.UserDaoMock;
import DaoInterfaces.IHashtagDao;
import DaoInterfaces.IKweetDao;
import DaoInterfaces.IUserDao;
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

    @BeforeClass
    public static void setUp() {
        service = new KweetService(kweetDao, hashtagDao, userDao);
    }

    @After
    public void tearDown() { }

    @Test
    public void publishNewKweetTest() throws UserNotFoundException, InvalidKweetException {
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

    @Test
    public void publishKweet_MessageIsNull() throws UserNotFoundException, InvalidKweetException {
        // Setup
        Kweet kweet = (Kweet) MockFactory.createMocks(Kweet.class, 1, "sender", new User((long)0, "Hank")).get(0);
        Assert.assertTrue(kweet.getSender().getUsername().equals("Hank"));
        User user = userDao.create((User) MockFactory.createMocks(User.class, 1).get(0));

        // Publish kweet
        service.publish(user.getId(), kweet);
    }

//    @Test(expected = UserNotFoundException.class)
//    public void publishKweet_UserIsNull() throws UserNotFoundException, InvalidKweetException {
//        // Setup
//        Kweet kweet = createMockKweet(null, "A message");
//
//        // Publish kweet
//        service.publish((long)999999, kweet);
//    }
//
//    @Test
//    public void publishKweet_Mentions() throws UserNotFoundException, InvalidKweetException {
//        // Setup
//        User hank = createMockUser((long)101, "Hank");
//        User rick = createMockUser((long)102, "Rick");
//        User john = createMockUser((long)103, "John");
//        Kweet kweet = createMockKweet(null, "A message: @Rick @John");
//        userDao.create(hank);
//        userDao.create(rick);
//        userDao.create(john);
//
//        // Publish kweet
//        Kweet publishedkweet = service.publish(hank.getId(), kweet);
//
//        List<User> mentions = publishedkweet.getMentions();
//        Assert.assertEquals(mentions.size(), 2);
//        Assert.assertTrue(mentions.contains(rick));
//        Assert.assertTrue(mentions.contains(john));
//    }
}
