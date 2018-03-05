import DAO.Mock.*;
import DaoInterfaces.*;
import org.junit.BeforeClass;
import org.junit.Test;

public class HashtagDaoTest {


    private static IHashtagDao hashtagDao;
    private static IKweetDao kweetDao;
    private static IUserDao userDao;

    @BeforeClass
    public static void Init() {
        userDao = new UserDaoMock();
        kweetDao = new KweetDaoMock(userDao.findAll());
        hashtagDao = new HashtagDaoMock(kweetDao.findAll());
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
    public void findByNameTest() {
        // To Do
    }

    @Test
    public void insertHashtagTest() {
        // To Do
    }

    @Test
    public void updateHashtagTest() {
        // To Do
    }

    @Test
    public void deleteHashtagTest() {
        // To Do
    }

    @Test
    public void getTrendTest() {
        // To Do
    }

}
