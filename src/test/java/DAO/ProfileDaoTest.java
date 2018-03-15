package DAO;

import DAO.Impl2.ProfileDaoImpl2;
import DAO.Mock.*;
import DaoInterfaces.*;
import Domain.Profile;
import Domain.User;
import Util.MockFactory;
import Util.MockService;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

public class ProfileDaoTest {

    private static IProfileDao profileDao;

    @BeforeClass
    public static void Init() {
        profileDao = new ProfileDaoImpl2();
    }

    @AfterClass
    public static void tearDown() {
        MockService.resetMockData();
    }

    @Test
    public void findAllTest() {
        // Set status before
        List<Profile> profilesBefore = new ArrayList<>(profileDao.findAll());

        // Insert new profile
        Profile mockProfile = profileDao.create(
                (Profile) MockFactory.createMocks(Profile.class, 1).get(0));

        // Check status after
        List<Profile> profilesAfter = profileDao.findAll();
        Assert.assertEquals("Returns list with size + 1", profilesBefore.size() + 1, profilesAfter.size());
        Assert.assertTrue("New Profile has been added", profilesAfter.contains(mockProfile));;
    }

    @Test
    public void findByIdTest() {
        // Insert new profile
        Profile mockProfile =  profileDao.create(
                (Profile) MockFactory.createMocks(Profile.class, 1).get(0));

        // Check fetched profile
        Profile fetchedProfile = profileDao.findById(mockProfile.getId());
        Assert.assertEquals("Fetched Profile is the same as the mocked one", mockProfile, fetchedProfile);
    }

    @Test
    public void findByUserTest() {
        User mockUser =
                (User) MockFactory.createMocks(User.class, 1, "name", "Hank").get(0);

        // Insert new profile
        Profile mockProfile = profileDao.create(
                (Profile) MockFactory.createMocks(Profile.class, 1, "user", mockUser).get(0));

        // Check fetched profile
        Profile fetchedProfile = profileDao.findByUser(mockUser);
        Assert.assertEquals("Fetched profile is the same as the mocked one", mockProfile, fetchedProfile);
    }

    @Test
    public void insertProfileTest() {
        // Insert new profile
        Profile mockProfile = profileDao.create(
                (Profile) MockFactory.createMocks(Profile.class, 1).get(0));

        // Check Profile list contains new profile
        Assert.assertTrue("New profile has been added", profileDao.findAll().contains(mockProfile));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void insertProfilesTest() {
        // Insert new profile
        List<Profile> mockProfiles = (List<Profile>) MockFactory.createMocks(Profile.class, 3);
        profileDao.create(mockProfiles);

        // Check Profile list contains new profile
        Assert.assertTrue("New profiles have been added", profileDao.findAll().containsAll(mockProfiles));
    }

    @Test
    public void updateProfileTest() {
        String newName = "mockProfile123";

        // Insert new profile
        Profile mockProfile = profileDao.create(
                (Profile) MockFactory.createMocks(Profile.class, 1).get(0));

        // Update new profile
        mockProfile.setName(newName);
        profileDao.update(mockProfile);

        // Check Profile list contains new name
        Assert.assertEquals("The name of the profile has been changed", newName, mockProfile.getName());
    }

    @Test
    public void deleteProfileTest() {
        // Insert new profile
        Profile mockProfile = profileDao.create((Profile) MockFactory.createMocks(Profile.class, 1).get(0));

        // Delete inserted Profile
        profileDao.remove(mockProfile);

        // Check Profile list contains new Profile
        Assert.assertFalse("New Profile has been removed", profileDao.findAll().contains(mockProfile));
    }
}
