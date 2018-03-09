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
        Kweet kweet = createMockKweet(null, "A message");
        User user = createMockUser((long)1001, "Hank");
        userDao.create(user);

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

        // Cleanup
        kweetDao.remove(publishedKweet);
        userDao.remove(user);
    }

    @Test(expected = InvalidKweetException.class)
    public void publishKweet_TooManyCharacters() throws UserNotFoundException, InvalidKweetException {
        // Setup
        Kweet kweet = createMockKweet(null, "This message is too long: sdfhlkasdgjasofjoewfjwafewefewf" +
                "safdasdfasfsdafasdfsdaflkfejalifjdfasfasfasfasfsafasfasdfalesijflasiejflaeifjljlasefijaeslifjalsiefj");
        User user = createMockUser((long)1001, "Hank");
        userDao.create(user);

        // Publish kweet
        service.publish(user.getId(), kweet);

        // Cleanup
        userDao.remove(user);
    }

    @Test(expected = InvalidKweetException.class)
    public void publishKweet_MessageIsEmpty() throws UserNotFoundException, InvalidKweetException {
        // Setup
        Kweet kweet = createMockKweet(null, "");
        User user = createMockUser((long)1001, "Hank");
        userDao.create(user);

        // Publish kweet
        service.publish(user.getId(), kweet);

        // Cleanup
        userDao.remove(user);
    }

    @Test(expected = InvalidKweetException.class)
    public void publishKweet_MessageIsNull() throws UserNotFoundException, InvalidKweetException {
        // Setup
        Kweet kweet = createMockKweet(null, null);
        User user = createMockUser((long)1001, "Hank");
        userDao.create(user);

        // Publish kweet
        service.publish(user.getId(), kweet);

        // Cleanup
        userDao.remove(user);
    }

    @Test(expected = UserNotFoundException.class)
    public void publishKweet_UserIsNull() throws UserNotFoundException, InvalidKweetException {
        // Setup
        Kweet kweet = createMockKweet(null, "A message");

        // Publish kweet
        service.publish((long)999999, kweet);
    }

    @Test
    public void publishKweet_Mentions() throws UserNotFoundException, InvalidKweetException {
        // Setup
        User hank = createMockUser((long)101, "Hank");
        User rick = createMockUser((long)102, "Rick");
        User john = createMockUser((long)103, "John");
        Kweet kweet = createMockKweet(null, "A message: @Rick @John");
        userDao.create(hank);
        userDao.create(rick);
        userDao.create(john);

        // Publish kweet
        Kweet publishedkweet = service.publish(hank.getId(), kweet);

        List<User> mentions = publishedkweet.getMentions();
        Assert.assertEquals(mentions.size(), 2);
        Assert.assertTrue(mentions.contains(rick));
        Assert.assertTrue(mentions.contains(john));
    }

    private Kweet createMockKweet(Long id, String message) {
        Kweet kweet = new Kweet();
        kweet.setId(id);
        kweet.setMessage(message);

        return kweet;
    }

    private User createMockUser(Long id, String username) {
        User user = new User();
        user.setId(id);
        user.setUsername(username);

        return user;
    }
}
