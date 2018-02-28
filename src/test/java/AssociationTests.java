import org.junit.BeforeClass;
import org.junit.Test;
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
}
