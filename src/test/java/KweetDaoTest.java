import DAO.Mock.*;
import DaoInterfaces.*;
import org.junit.BeforeClass;
import org.junit.Test;

public class KweetDaoTest {
    private static IHashtagDao hashtagDao;
    private static IKweetDao kweetDao;
    private static IUserDao userDao;

    @BeforeClass
    public static void Init() {
        userDao = new UserDaoMock();
        kweetDao = new KweetDaoMock(userDao.findAll());
    }

    @Test
    public void findAllTest() {
        // To Do
    }

    @Test
    public void findByIdTest() {
        // To Do
    }

    @Test
    public void findByMessageTest() {
        // To Do
    }

    @Test
    public void findBySenderNameTest() {
        // To Do
    }

    @Test
    public void insertKweetTest() {
        // To Do
    }

    @Test
    public void updateKweetTest() {
        // To Do
    }

    @Test
    public void deleteKweetTest() {
        // To Do
    }

    @Test
    public void getTimelineTest() {
        // To Do
    }

    @Test
    public void searchTest() {
        // To Do
    }
}
