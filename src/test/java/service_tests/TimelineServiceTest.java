package service_tests;

import dao.interfaces.IProfileDao;
import dao.mocks.KweetDaoMock;
import dao.mocks.ProfileDaoMock;
import dao.mocks.UserDaoMock;
import dao.interfaces.IKweetDao;
import dao.interfaces.IUserDao;
import domain.Kweet;
import domain.User;
import services.TimelineService;
import util.MockFactory;
import util.MockService;
import viewmodels.TimelineItem;
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
        service.setProfileDao(profileDao);
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
        List<TimelineItem> timeline = service.generateTimeline(user.getUsername());

        // Asserts
        Assert.assertEquals("Timeline does not have a size of 4", 4, timeline.size());
        Assert.assertEquals("This kweet isn't the most recent", timeline.get(0).getKweetId(), user.getFollowing().get(2).getKweets().get(0).getId());
        Assert.assertEquals("This kweet isn't the second most recent", timeline.get(1).getKweetId(), user.getKweets().get(0).getId());
        Assert.assertEquals("This kweet isn't the second most oldest", timeline.get(2).getKweetId(), user.getFollowing().get(1).getKweets().get(0).getId());
        Assert.assertEquals("This kweet isn't the oldest", timeline.get(3).getKweetId(), user.getFollowing().get(0).getKweets().get(0).getId());
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
        List<Kweet> randomKweets =  MockService.toKweets(MockFactory.createMocks(Kweet.class, 4, "sender", randomUser));
        for (int i = 0; i < randomKweets.size(); i++) {
            Kweet randomKweet = randomKweets.get(i);
            randomKweet.setPublicationDate(getDateDaysAgo(i + 1));
        }
        kweetDao.create(randomKweets);

        // Set mentions
        user.setMentions(randomKweets);

        // Get timeline
        List<TimelineItem> timeline = service.generateMentionsTimeline(user.getUsername());

        // Asserts
        Assert.assertEquals("Timeline does not have a size of 4", 4, timeline.size());
        Assert.assertEquals("This kweet isn't the most recent", timeline.get(0).getKweetId(), randomKweets.get(0).getId());
        Assert.assertEquals("This kweet isn't the second most recent", timeline.get(1).getKweetId(), randomKweets.get(1).getId());
        Assert.assertEquals("This kweet isn't the second most oldest", timeline.get(2).getKweetId(), randomKweets.get(2).getId());
        Assert.assertEquals("This kweet isn't the oldest", timeline.get(3).getKweetId(), randomKweets.get(3).getId());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void mostRecentKweets() {
        // Setup user
        User user = (User) MockFactory.createMocks(User.class, 1, "username", "Hans").get(0);
        userDao.create(user);

        // Setup random kweets
        List<Kweet> randomKweets =  MockService.toKweets(MockFactory.createMocks(Kweet.class, 10, "sender", user));
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
        Assert.assertEquals("This kweet isn't the most recent", timeline.get(0).getKweetId(), randomKweets.get(0).getId());
        Assert.assertEquals("This kweet isn't the second most recent", timeline.get(1).getKweetId(), randomKweets.get(1).getId());
        Assert.assertEquals("This kweet isn't the second most oldest", timeline.get(2).getKweetId(), randomKweets.get(2).getId());
        Assert.assertEquals("This kweet isn't the oldest", timeline.get(3).getKweetId(), randomKweets.get(3).getId());
    }

    @SuppressWarnings("unchecked")
    private User getUserWithFollowing(String username, int numberOfFollowing) {
        User user = (User) MockFactory.createMocks(User.class, 1, "username", username).get(0);
        List<User> followingUsers =  MockService.toUsers(MockFactory.createMocks(User.class, numberOfFollowing));
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
            Kweet kweet = (Kweet) MockFactory.createMocks(Kweet.class, numberOfKweets, "sender", user).get(0);
            List<Kweet> kweets = new ArrayList<>();
            kweets.add(kweet);

            user.setKweets(kweets);
            kweetDao.create(kweets);

        }
    }

    private Date getDateDaysAgo(int numberOfDays) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, -1 * Math.abs(numberOfDays));

        return cal.getTime();
    }
}
