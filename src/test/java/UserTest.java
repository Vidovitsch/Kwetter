import Domain.User;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class UserTest {

    @Test
    public void createNewProfileTest() {
        User dummyUser = getSimpleDummyUser("dummyUser");
        // Check before adding profile
        assertEquals("User has no profile", null, dummyUser.getProfile());

        dummyUser.createProfile("dummyProfile");
    }

    private User getSimpleDummyUser(String username) {
        return new User(username);
    }
}
