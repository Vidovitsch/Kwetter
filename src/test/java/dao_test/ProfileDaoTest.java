package dao_test;

import dao.implementations.ProfileDaoImpl;
import dao.implementations.UserDaoImpl;
import dao.interfaces.*;
import domain.Profile;
import domain.User;
import util.MockFactory;
import util.MockService;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ProfileDaoTest {

    private static IProfileDao profileDao;
    private static IUserDao userDao;

    @BeforeClass
    public static void Init() {
        userDao = new UserDaoImpl("KwetterPU_test");
        profileDao = new ProfileDaoImpl("KwetterPU_test");
    }

    @AfterClass
    public static void tearDown() {
        MockService.renewMockData();
    }

    @Test
    public void findAllTest() {
        // Set status before
        List<Profile> profilesBefore = new ArrayList<>(profileDao.findAll());

        User mockUser = (User) MockFactory.createMocks(User.class, 1, "name", "Hank").get(0);

        beginUserTransaction();
        userDao.create(mockUser);
        endUserTransaction();

        Profile mockProfile = (Profile) MockFactory.createMocks(Profile.class, 1).get(0);
        mockProfile.setUser(mockUser);

        beginProfileTransaction();
        profileDao.create(mockProfile);
        endProfileTransaction();

        // Check status after
        List<Profile> profilesAfter = profileDao.findAll();
        Assert.assertEquals("Returns list with size + 1", profilesBefore.size() + 1, profilesAfter.size());
        Assert.assertTrue("New Profile has been added", profilesAfter.contains(mockProfile));
    }

    @Test
    public void findByIdTest() {
        // Insert new profile
        User mockUser = (User) MockFactory.createMocks(User.class, 1, "name", "Hank").get(0);

        beginUserTransaction();
        userDao.create(mockUser);
        endUserTransaction();

        Profile mockProfile = (Profile) MockFactory.createMocks(Profile.class, 1).get(0);
        mockProfile.setUser(mockUser);

        beginProfileTransaction();
        mockProfile = profileDao.create(mockProfile);
        endProfileTransaction();

        // Check fetched profile
        Profile fetchedProfile = profileDao.findById(mockProfile.getId());
        Assert.assertEquals("Fetched Profile is the same as the mocked one", mockProfile, fetchedProfile);
    }

    @Test
    public void findByUserTest() {
        User mockUser = (User) MockFactory.createMocks(User.class, 1, "name", "Hank").get(0);

        beginUserTransaction();
        userDao.create(mockUser);
        endUserTransaction();

        // Insert new profile
        Profile mockProfile = (Profile) MockFactory.createMocks(Profile.class, 1, "user", mockUser).get(0);
        mockProfile.setUser(mockUser);

        beginProfileTransaction();
        profileDao.create(mockProfile);
        endProfileTransaction();

        // Check fetched profile
        Profile fetchedProfile = profileDao.findByUser(mockUser);
        Assert.assertEquals("Fetched profile is the same as the mocked one", mockProfile, fetchedProfile);
    }

    @Test
    public void insertProfileTest() {
        // Insert new profile
        User mockUser = (User) MockFactory.createMocks(User.class, 1, "name", "Hank").get(0);

        beginUserTransaction();
        userDao.create(mockUser);
        endUserTransaction();

        Profile mockProfile = (Profile) MockFactory.createMocks(Profile.class, 1).get(0);
        mockProfile.setUser(mockUser);

        beginProfileTransaction();
        profileDao.create(mockProfile);
        endProfileTransaction();

        // Check Profile list contains new profile
        Assert.assertTrue("New profile has been added", profileDao.findAll().contains(mockProfile));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void insertProfilesTest() {
        // Insert new profile
        List<User> mockUsers = MockService.toUsers(MockFactory.createMocks(User.class, 3, "name", "Hank"));

        beginUserTransaction();
        userDao.create(mockUsers);
        endUserTransaction();

        List<Profile> mockProfiles = MockService.toProfiles(MockFactory.createMocks(Profile.class, 3));
        for(int  i = 0; i < 3;i++){
            mockProfiles.get(i).setUser(mockUsers.get(i));
        }

        beginProfileTransaction();
        profileDao.create(mockProfiles);
        endProfileTransaction();

        // Check Profile list contains new profile
        Assert.assertTrue("New profiles have been added", profileDao.findAll().containsAll(mockProfiles));
    }


    @Test
    public void updateProfileTest() {
        String newName = "mockProfile123";
        User mockUser = (User) MockFactory.createMocks(User.class, 1, "name", "Hank").get(0);

        beginUserTransaction();
        userDao.create(mockUser);
        endUserTransaction();

        Profile mockProfile = (Profile) MockFactory.createMocks(Profile.class, 1).get(0);
        mockProfile.setUser(mockUser);

        // Insert new profile
        beginProfileTransaction();
        profileDao.create(mockProfile);
        endProfileTransaction();

        // Update new profile
        mockProfile.setName(newName);
        profileDao.update(mockProfile);

        // Check Profile list contains new name
        Assert.assertEquals("The name of the profile has been changed", newName, mockProfile.getName());
    }

    @Test
    public void deleteProfileTest() {
        // Insert new profile
        User mockUser = (User) MockFactory.createMocks(User.class, 1, "name", "Hank").get(0);

        beginUserTransaction();
        userDao.create(mockUser);
        endUserTransaction();

        Profile mockProfile = (Profile) MockFactory.createMocks(Profile.class, 1).get(0);
        mockProfile.setUser(mockUser);

        beginProfileTransaction();
        mockProfile = profileDao.create(mockProfile);
        endProfileTransaction();

        Assert.assertTrue( "New profile not created, removal can not be teste" ,profileDao.findAll().contains(mockProfile));

        // Delete inserted Profile
        beginProfileTransaction();
        profileDao.remove(mockProfile);
        endProfileTransaction();

        // Check Profile list contains new Profile
        Assert.assertFalse("New Profile has not been removed", profileDao.findAll().contains(mockProfile));
    }

    private void beginUserTransaction() {
        if (userDao.getEntityManager() != null) {
            userDao.getEntityManager().getTransaction().begin();
        }
    }

    private void endUserTransaction() {
        if (userDao.getEntityManager() != null) {
            userDao.getEntityManager().getTransaction().commit();
        }
    }

    private void beginProfileTransaction() {
        if (profileDao.getEntityManager() != null) {
            profileDao.getEntityManager().getTransaction().begin();
        }
    }

    private void endProfileTransaction() {
        if (profileDao.getEntityManager() != null) {
            profileDao.getEntityManager().getTransaction().commit();
        }
    }
}
