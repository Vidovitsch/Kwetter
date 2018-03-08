package DAO;

import DAO.Mock.*;
import DaoInterfaces.*;
import Domain.Hashtag;
import Domain.Profile;
import Domain.User;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ProfileDaoTest {

    private static IProfileDao profileDao;

    private static IUserDao userDao;

    @BeforeClass
    public static void Init() {
        userDao = new UserDaoMock();
        profileDao = new ProfileDaoMock(userDao.findAll());
    }

    @Test
    public void findAllTest() {
        // Set status before
        Collection<Profile> ProfilesBefore = new ArrayList<Profile>(profileDao.findAll());

        // Insert new profile
        Profile mockProfile = new Profile("mockProfile");
        profileDao.insertProfile(mockProfile);

        // Check status after
        Collection<Profile> profilesAfter = profileDao.findAll();
        Assert.assertEquals("Returns list with size + 1",
                ProfilesBefore.size() + 1, profilesAfter.size());
        Assert.assertTrue("New Profile has been added", profilesAfter.contains(mockProfile));

        // Remove mock Profile (cleanup)
        profileDao.deleteProfile(mockProfile);
    }

    @Test
    public void findByIdTest() {
        long id = 999999;

        // Insert new profile
        Profile mockProfile = new Profile("mockProfile");
        mockProfile.setId(id);
        profileDao.insertProfile(mockProfile);

        // Check fetched profile
        Profile fetchedProfile = profileDao.findById(id);
        Assert.assertEquals("Fetched Profile is the same as the mocked one", mockProfile, fetchedProfile);

        // Remove mock profile (cleanup)
        profileDao.deleteProfile(fetchedProfile);
    }

    @Test
    public void findByUserTest() {
        User mockUser = new User("Hank");

        // Insert new profile
        Profile mockProfile = new Profile("mockProfile");
        mockProfile.setUser(mockUser);
        profileDao.insertProfile(mockProfile);

        // Check fetched profile
        Profile fetchedProfile = profileDao.findByUser(mockUser);
        Assert.assertEquals("Fetched profile is the same as the mocked one", mockProfile, fetchedProfile);

        // Remove mock profile (cleanup)
        profileDao.deleteProfile(fetchedProfile);
    }

    @Test
    public void findByNameTest() {
        String name = "mockProfile";

        // Insert new profiles
        Profile mockProfile1 = new Profile(name);
        Profile mockProfile2 = new Profile(name);
        Profile mockProfile3 = new Profile("mockProfile123");
        profileDao.insertProfile(mockProfile1);
        profileDao.insertProfile(mockProfile2);
        profileDao.insertProfile(mockProfile3);

        // Check fetched profiles
        List<Profile> fetchedProfiles = profileDao.findByName(name);
        Assert.assertTrue("mockProfile1 has name = " + name, fetchedProfiles.contains(mockProfile1));
        Assert.assertTrue("mockProfile2 has name = " + name, fetchedProfiles.contains(mockProfile2));
        Assert.assertFalse("mockProfile1 doesn't have name = " + name, fetchedProfiles.contains(mockProfile3));

        // Remove mock profile (cleanup)
        profileDao.deleteProfile(mockProfile1);
        profileDao.deleteProfile(mockProfile2);
        profileDao.deleteProfile(mockProfile3);
    }

    @Test
    public void insertProfileTest() {
        // Insert new profile
        Profile mockProfile = new Profile("mockProfile");
        mockProfile.setId(999999);
        profileDao.insertProfile(mockProfile);

        // Check Profile list contains new profile
        Assert.assertTrue("New profile has been added", profileDao.findAll().contains(mockProfile));

        // Remove mock profile (cleanup)
        profileDao.deleteProfile(mockProfile);
    }

    @Test
    public void updateProfileTest() {
        String newName = "mockProfile123";

        // Insert new profile
        Profile mockProfile = new Profile("mockProfile");
        mockProfile.setId(999999);
        profileDao.insertProfile(mockProfile);

        // Update new profile
        mockProfile.setName(newName);
        profileDao.updateProfile(mockProfile);

        // Check Profile list contains new name
        Assert.assertEquals("The name of the profile has been changed", newName, mockProfile.getName());

        // Remove mock Profile (cleanup)
        profileDao.deleteProfile(mockProfile);
    }

    @Test
    public void deleteProfileTest() {
        // Insert new profile
        Profile mockProfile = new Profile("mockProfile");
        mockProfile.setId(999999);
        profileDao.insertProfile(mockProfile);

        // Delete inserted Profile
        profileDao.deleteProfile(mockProfile);

        // Check Profile list contains new Profile
        Assert.assertFalse("New Profile has been removed", profileDao.findAll().contains(mockProfile));
    }
}
