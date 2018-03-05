import DAO.Mock.*;
import DaoInterfaces.*;
import org.junit.BeforeClass;
import org.junit.Test;

public class UserDaoTest {

    private static IUserDao userDao;
    @BeforeClass
    public static void Init() {
        userDao = new UserDaoMock();
    }

    @Test
    public void someTest() {
        // To Do
    }
}
