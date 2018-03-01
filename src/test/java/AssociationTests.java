import Domain.Hashtag;
import Domain.Kweet;
import Domain.Role;
import Domain.User;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.swing.text.html.HTMLDocument;
import java.util.Collection;
import java.util.Iterator;

import static org.junit.Assert.*;

/**
 * Scenario for tests:
 *
 * 1. Association: User - Kweet
 *
 * 1.1. 10 unique users will use Kwetter.
 * 1.2. One user follows the 9 other users. This results in 9 following and 9 followers for each user.
 * 1.3. Each user sends 9 unique kweets. In each kweet one of the 9 other users gets mentioned.
 *      This results in 9 mentions for each user.
 *
 * 2. Association: User - Role
 *
 * 2.1. Each user has the 'Kweeter' and 'Moderator' role. This results in a list of 10 users for both
 *      the 'Kweeter' and 'Moderator' role.
 *
 * 3. Association: Kweet - Hashtag
 *
 * 3.1. Each Kweet contains the hashtag '#Test' and #'Kwetter'. In this scenario the hashtag will be added
 *      manually. In a real life scenario, hashtags will be fetched from the message of the kweet.
 *
 * 4. Association: User - Profile
 *
 * 4.1. Each user has one unique profile.
 */

public class AssociationTests {

    private static DummyData dummyData;

    @BeforeClass
    public static void Init() {
        dummyData = new DummyData();
    }

    @Test
    public void someTest() {
        // To Do
    }

    @Test
    public void UserKweetAssociationTest() {
        Collection<User> users = dummyData.getDummyUsers();
        Collection<Kweet> kweets = dummyData.getDummyKweets();

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
        Collection<User> users = dummyData.getDummyUsers();
        Collection<Role> roles = dummyData.getDummyRoles();


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
        Collection<Hashtag> hashtags = dummyData.getDummyHashtags();
        Collection<Kweet> kweets = dummyData.getDummyKweets();


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

}
