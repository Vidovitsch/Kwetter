import DAO.Mock.*;
import DaoInterfaces.*;
import org.junit.BeforeClass;
import org.junit.Test;

public class RoleDaoTest {
    private static IRoleDao roleDao;
    private static IUserDao userDao;
    @BeforeClass
    public static void Init() {
        userDao = new UserDaoMock();
        roleDao = new RoleDaoMock(userDao.findAll());
    }

    @Test
    public void someTest() {
        // To Do
    }
}
