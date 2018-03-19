package association_tests;

import dao_tests.mocks.*;
import dao_tests.interfaces.*;
import domain.*;
import util.MockService;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.Collection;
import java.util.Iterator;

/**
 * Scenario for tests:
 *
 * 1. association_tests: User - Kweet
 *
 * 1.1. 10 unique users will use Kwetter.
 * 1.2. One user follows the 9 other users. This results in 9 following and 9 followers for each user.
 * 1.3. Each user sends 9 unique kweets. In each kweet one of the 9 other users gets mentioned.
 *      This results in 9 mentions for each user.
 *
 * 2. association_tests: User - Role
 *
 * 2.1. Each user has the 'Kweeter' and 'Moderator' role. This results in a list of 10 users for both
 *      the 'Kweeter' and 'Moderator' role.
 *
 * 3. association_tests: Kweet - Hashtag
 *
 * 3.1. Each Kweet contains the hashtag '#Test' and #'Kwetter'. In this scenario the hashtag will be added
 *      manually. In a real life scenario, hashtags will be fetched from the message of the kweet.
 *
 * 4. association_tests: User - Profile
 *
 * 4.1. Each user has one unique profile.
 */

public class AssociationTestsMockDAO {

    private static IHashtagDao hashtagDao;
    private static IKweetDao kweetDao;
    private static IProfileDao profileDao;
    private static IRoleDao roleDao;
    private static IUserDao userDao;

    @BeforeClass
    public static void Init() {
        userDao = new UserDaoMock();
        profileDao = new ProfileDaoMock();
        roleDao = new RoleDaoMock();
        kweetDao = new KweetDaoMock();
        hashtagDao = new HashtagDaoMock();
    }

    @AfterClass
    public static void tearDown() {
        MockService.renewMockData();
    }

    @Test
    public void UserKweetAssociationTest() {
        Collection<User> users = userDao.findAll();
        Collection<Kweet> kweets = kweetDao.findAll();

        Iterator<User> userIterator = users.iterator();
        while(userIterator.hasNext()){
            Assert.assertTrue(kweets.containsAll(userIterator.next().getKweets()));
        }
        Iterator<Kweet> kweetIterator = kweets.iterator();
        while(kweetIterator.hasNext()){
            Kweet k = kweetIterator.next();
            User u = k.getSender();
            Assert.assertTrue(u.getKweets().contains(k));
        }
    }

    @Test
    public void UserRoleAssociationTest() {
        Collection<User> users = userDao.findAll();
        Collection<Role> roles = roleDao.findAll();


        Iterator<User> userIterator = users.iterator();
        while(userIterator.hasNext()){
            Assert.assertTrue(roles.containsAll(userIterator.next().getRoles()));
        }

        Iterator<Role> roleIterator = roles.iterator();
        while(roleIterator.hasNext()){
            Role r = roleIterator.next();
            Collection<User> users1 = r.getUsers();
            Iterator<User> roleUsersIterator = users1.iterator();
            while(roleUsersIterator.hasNext())
            {
                Assert.assertTrue(roleUsersIterator.next().getRoles().contains(r));
            }
        }
    }

    @Test
    public void KweetHashtagAssociationTest() {
        Collection<Hashtag> hashtags = hashtagDao.findAll();
        Collection<Kweet> kweets = kweetDao.findAll();

        Iterator<Hashtag> hashtagIterator = hashtags.iterator();
        while(hashtagIterator.hasNext()){
            Assert.assertTrue(kweets.containsAll(hashtagIterator.next().getKweets()));
        }

        Iterator<Kweet> kweetIterator = kweets.iterator();
        while(kweetIterator.hasNext()){
            Kweet k = kweetIterator.next();
            Collection<Hashtag> hashtags1 = k.getHashtags();
            Iterator<Hashtag> kweetHashtagsIterator = hashtags1.iterator();
            while(kweetHashtagsIterator.hasNext())
            {
                Assert.assertTrue(kweetHashtagsIterator.next().getKweets().contains(k));
            }
        }
    }

    @Test
    public void UserProfileAssociationTest() {
        Collection<User> users = userDao.findAll();
        Collection<Profile> profiles = profileDao.findAll();

        Iterator<Profile> profileIterator = profiles.iterator();
        while(profileIterator.hasNext()){
            Assert.assertTrue(users.contains(profileIterator.next().getUser()));
        }
        Iterator<User> userIterator = users.iterator();
        while(userIterator.hasNext()){
            User u = userIterator.next();
            Profile p = profileDao.findByUser(u);
            Assert.assertEquals(p.getUser(), u);
        }
    }

    @Test
    public void minitest() {

    }
}
