package Service;

import DAO.Impl.ProfileDaoImpl;
import DAO.Mock.KweetDaoMock;
import DAO.Mock.ProfileDaoMock;
import DAO.Mock.UserDaoMock;
import DaoInterfaces.IKweetDao;
import DaoInterfaces.IProfileDao;
import DaoInterfaces.IUserDao;
import Domain.Kweet;
import Domain.User;
import Util.MockFactory;
import Util.MockService;
import ViewModels.TimelineItem;
import org.junit.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TimelineServiceTest {

    private static IUserDao userDao;
    private static IKweetDao kweetDao;
    private static IProfileDao profileDao;

    private static TimelineService service;

    @BeforeClass
    public static void setUp() {
        userDao = new UserDaoMock();
        kweetDao = new KweetDaoMock();
        profileDao = new ProfileDaoMock();

        service = new TimelineService();
        service.setKweetDao(kweetDao);
        service.setUserDao(userDao);
    }

    @After
    public void tearDown() {
        MockService.renewMockData();
    }

    @Test
    public void generateTimeline() {
        // Setup following
        User user = getUserWithFollowing("Rick", 3);
        userDao.create(user);

        // Setup random user
        User randomUser = (User) MockFactory.createMocks(User.class, 1, "username", "Random").get(0);
        userDao.create(randomUser);

        // Setup mock kweets
        sendMockKweets(user.getFollowing(), 1);
        sendMockKweets(user, 1);
        sendMockKweets(randomUser, 1);

        // Setup kweet dates
        user.getFollowing().get(0).getKweets().get(0).setPublicationDate(getDateDaysAgo(4));
        user.getFollowing().get(1).getKweets().get(0).setPublicationDate(getDateDaysAgo(3));
        user.getFollowing().get(2).getKweets().get(0).setPublicationDate(getDateDaysAgo(1));
        user.getKweets().get(0).setPublicationDate(getDateDaysAgo(2));

        // Get timeline
        List<TimelineItem> timeline = new ArrayList<>(service.generateTimeline(user.getUsername()));

        // Asserts
        Assert.assertEquals("Timeline does not have a size of 4", 4, timeline.size());
        Assert.assertEquals("This kweet isn't the most recent", timeline.get(0).kweetID, user.getFollowing().get(2).getKweets().get(0).getId());
        Assert.assertEquals("This kweet isn't the second most recent", timeline.get(1).kweetID, user.getKweets().get(0).getId());
        Assert.assertEquals("This kweet isn't the second most oldest", timeline.get(2).kweetID, user.getFollowing().get(1).getKweets().get(0).getId());
        Assert.assertEquals("This kweet isn't the oldest", 4, timeline.get(3).kweetID, user.getFollowing().get(0).getKweets().get(0).getId());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void generateMentionsTimeline() {
        // Setup user
        User user = (User) MockFactory.createMocks(User.class, 1, "username", "Jack").get(0);
        userDao.create(user);

        // Setup random users
        User randomUser = (User) MockFactory.createMocks(User.class, 1, "username", "Daniels").get(0);
        userDao.create(user);

        // Setup random kweets
        List<Kweet> randomKweets = (List<Kweet>) MockFactory.createMocks(Kweet.class, 4, "sender", randomUser);
        for (int i = 0; i < randomKweets.size(); i++) {
            Kweet randomKweet = randomKweets.get(i);
            randomKweet.setPublicationDate(getDateDaysAgo(i + 1));
        }
        kweetDao.create(randomKweets);

        // Set mentions
        user.setMentions(randomKweets);

        // Get timeline
        List<TimelineItem> timeline = new ArrayList<>(service.generateMentionsTimeline(user.getUsername()));

        // Asserts
        Assert.assertEquals("Timeline does not have a size of 4", 4, timeline.size());
        Assert.assertEquals("This kweet isn't the most recent", timeline.get(0).kweetID, randomKweets.get(0).getId());
        Assert.assertEquals("This kweet isn't the second most recent", timeline.get(1).kweetID, randomKweets.get(1).getId());
        Assert.assertEquals("This kweet isn't the second most oldest", timeline.get(2).kweetID, randomKweets.get(2).getId());
        Assert.assertEquals("This kweet isn't the oldest", timeline.get(3).kweetID, randomKweets.get(3).getId());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void mostRecentKweets() {
        // Setup user
        User user = (User) MockFactory.createMocks(User.class, 1, "username", "Hans").get(0);
        userDao.create(user);

        // Setup random kweets
        List<Kweet> randomKweets = (List<Kweet>) MockFactory.createMocks(Kweet.class, 10, "sender", user);
        for (int i = 0; i < randomKweets.size(); i++) {
            Kweet randomKweet = randomKweets.get(i);
            randomKweet.setPublicationDate(getDateDaysAgo(i + 1));
        }
        kweetDao.create(randomKweets);

        // Set mentions
        user.setKweets(randomKweets);

        // Get timeline
        List<TimelineItem> timeline = new ArrayList<>(service.mostRecentKweets(user.getUsername(), 4));

        // Asserts
        Assert.assertEquals("Timeline does not have a size of 4", 4, timeline.size());
        Assert.assertEquals("This kweet isn't the most recent", timeline.get(0).kweetID, randomKweets.get(0).getId());
        Assert.assertEquals("This kweet isn't the second most recent", timeline.get(1).kweetID, randomKweets.get(1).getId());
        Assert.assertEquals("This kweet isn't the second most oldest", timeline.get(2).kweetID, randomKweets.get(2).getId());
        Assert.assertEquals("This kweet isn't the oldest", timeline.get(3).kweetID, randomKweets.get(3).getId());
    }

    @SuppressWarnings("unchecked")
    private User getUserWithFollowing(String username, int numberOfFollowing) {
        User user = (User) MockFactory.createMocks(User.class, 1, "username", username).get(0);
        List<User> followingUsers = (List<User>) MockFactory.createMocks(User.class, numberOfFollowing);
        user.setFollowing(followingUsers);

        return user;
    }

    private void sendMockKweets(User user, int numberOfKweets) {
        List<User> users = new ArrayList<>();
        users.add(user);
        sendMockKweets(users, numberOfKweets);
    }

    private void sendMockKweets(List<User> users, int numberOfKweets) {
        for (User user : users) {
            for (int i = 0; i < numberOfKweets; i++) {
                Kweet kweet = (Kweet) MockFactory.createMocks(Kweet.class, 1, "sender", user).get(0);
                List<Kweet> kweets = new ArrayList<>();
                kweets.add(kweet);

                user.setKweets(kweets);
                kweetDao.create(kweets);
            }
        }
    }

    private Date getDateDaysAgo(int numberOfDays) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, -1 * Math.abs(numberOfDays));

        return cal.getTime();
    }
}
