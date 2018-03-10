package DAO;

import DAO.Mock.*;
import DaoInterfaces.*;
import Domain.Profile;
import Domain.User;
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
        userDao = new UserDaoMock();
        profileDao = new ProfileDaoMock(userDao.findAll());
    }

    @Test
    public void findAllTest() {
        // Set status before
        List<Profile> ProfilesBefore = new ArrayList<Profile>(profileDao.findAll());

        // Insert new profile
        Profile mockProfile = new Profile((long)-1,"mockProfile");
        profileDao.create(mockProfile);

        // Check status after
        List<Profile> profilesAfter = profileDao.findAll();
        Assert.assertEquals("Returns list with size + 1",
                ProfilesBefore.size() + 1, profilesAfter.size());
        Assert.assertTrue("New Profile has been added", profilesAfter.contains(mockProfile));

        // Remove mock Profile (cleanup)
        profileDao.remove(mockProfile);
    }

    @Test
    public void findByIdTest() {
        // Insert new profile
        Profile mockProfile =  profileDao.create(new Profile());

        // Check fetched profile
        Profile fetchedProfile = profileDao.findById(mockProfile.getId());
        Assert.assertEquals("Fetched Profile is the same as the mocked one", mockProfile, fetchedProfile);

        // Remove mock profile (cleanup)
        profileDao.remove(fetchedProfile);
    }

    @Test
    public void findByUserTest() {
        User mockUser = new User((long)-1,"Hank");

        // Insert new profile
        Profile mockProfile = new Profile((long)-1,"mockProfile");
        mockProfile.setUser(mockUser);
        profileDao.create(mockProfile);

        // Check fetched profile
        Profile fetchedProfile = profileDao.findByUser(mockUser);
        Assert.assertEquals("Fetched profile is the same as the mocked one", mockProfile, fetchedProfile);

        // Remove mock profile (cleanup)
        profileDao.remove(fetchedProfile);
    }

    @Test
    public void findByNameTest() {
        String name = "mockProfile";

        // Insert new profiles
        Profile mockProfile1 = new Profile((long)-1,name);
        Profile mockProfile2 = new Profile((long)-1,name);
        Profile mockProfile3 = new Profile((long)-1,"mockProfile123");
        profileDao.create(mockProfile1);
        profileDao.create(mockProfile2);
        profileDao.create(mockProfile3);

        // Check fetched profiles
        List<Profile> fetchedProfiles = profileDao.findByName(name);
        Assert.assertTrue("mockProfile1 has name = " + name, fetchedProfiles.contains(mockProfile1));
        Assert.assertTrue("mockProfile2 has name = " + name, fetchedProfiles.contains(mockProfile2));
        Assert.assertFalse("mockProfile1 doesn't have name = " + name, fetchedProfiles.contains(mockProfile3));

        // Remove mock profile (cleanup)
        profileDao.remove(mockProfile1);
        profileDao.remove(mockProfile2);
        profileDao.remove(mockProfile3);
    }

    @Test
    public void insertProfileTest() {
        // Insert new profile
        Profile mockProfile = new Profile((long)-1,"mockProfile");
        mockProfile.setId((long)999999);
        profileDao.create(mockProfile);

        // Check Profile list contains new profile
        Assert.assertTrue("New profile has been added", profileDao.findAll().contains(mockProfile));

        // Remove mock profile (cleanup)
        profileDao.remove(mockProfile);
    }

    @Test
    public void updateProfileTest() {
        String newName = "mockProfile123";

        // Insert new profile
        Profile mockProfile = new Profile((long)-1,"mockProfile");
        mockProfile.setId((long)999999);
        profileDao.create(mockProfile);

        // Update new profile
        mockProfile.setName(newName);
        profileDao.update(mockProfile);

        // Check Profile list contains new name
        Assert.assertEquals("The name of the profile has been changed", newName, mockProfile.getName());

        // Remove mock Profile (cleanup)
        profileDao.remove(mockProfile);
    }

    @Test
    public void deleteProfileTest() {
        // Insert new profile
        Profile mockProfile = new Profile((long)-1,"mockProfile");
        mockProfile.setId((long)999999);
        profileDao.create(mockProfile);

        // Delete inserted Profile
        profileDao.remove(mockProfile);

        // Check Profile list contains new Profile
        Assert.assertFalse("New Profile has been removed", profileDao.findAll().contains(mockProfile));
    }
}
