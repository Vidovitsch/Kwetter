import DAO.Mock.*;
import DaoInterfaces.*;
import org.junit.BeforeClass;
import org.junit.Test;

public class HashtagDaoTest {


    private static IHashtagDao hashtagDao;
    private static IKweetDao kweetDao;
    private static IProfileDao profileDao;
    private static IRoleDao roleDao;
    private static IUserDao userDao;
    @BeforeClass
    public static void Init() {
        userDao = new UserDaoMock();
        profileDao = new ProfileDaoMock(userDao.findAll());
        roleDao = new RoleDaoMock(userDao.findAll());
        kweetDao = new KweetDaoMock(userDao.findAll());
        hashtagDao = new HashtagDaoMock(kweetDao.findAll());
    }

    @Test
    public void someTest() {
        // To Do
    }
}
