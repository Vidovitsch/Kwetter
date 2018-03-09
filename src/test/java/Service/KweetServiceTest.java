package Service;

import DaoInterfaces.IHashtagDao;
import DaoInterfaces.IKweetDao;
import DaoInterfaces.IUserDao;
import Domain.Kweet;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class KweetServiceTest {

    @Mock
    private IKweetDao kweetDao;

    @Mock
    private IHashtagDao hashtagDao;

    @Mock
    private IUserDao userDao;

    @Mock
    private Kweet mockKweet = new Kweet();

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private KweetService service;

    @Before
    public void setUp() {
        service = new KweetService(kweetDao, hashtagDao, userDao);
    }

    @After
    public void tearDown() { }

    @Test
    public void createNewKweetTest() {

    }
}
