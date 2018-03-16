package Service;

import DAO.Mock.UserDaoMock;
import DaoInterfaces.IProfileDao;
import DaoInterfaces.IUserDao;
import Util.MockService;
import org.junit.After;
import org.junit.BeforeClass;

public class ProfileServiceTest {

    private static IUserDao userDao;
    private static IProfileDao profileDao;

    private static ProfileServiceTest service;

    @BeforeClass
    public static void setUp() {
        userDao = new UserDaoMock();

    }

    @After
    public void tearDown() {
        MockService.renewMockData();
    }
}
