import DAO.Mock.*;
import DaoInterfaces.*;
import org.junit.BeforeClass;
import org.junit.Test;

public class ProfileDaoTest {

    private static IProfileDao profileDao;

    private static IUserDao userDao;
    @BeforeClass
    public static void Init() {
        userDao = new UserDaoMock();
        profileDao = new ProfileDaoMock(userDao.findAll());
    }

    @Test
    public void someTest() {
        // To Do
    }
}
