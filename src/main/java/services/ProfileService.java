package services;

import dao.interfaces.IKweetDao;
import dao.interfaces.IProfileDao;
import dao.interfaces.IUserDao;
import domain.Profile;
import domain.User;
import viewmodels.ProfileData;
import viewmodels.UserTotalsView;
import org.apache.commons.validator.UrlValidator;

import exceptions.*;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

@Named(value = "profileDataService")
@Stateless
public class ProfileService {

    @Inject
    private IUserDao userDao;

    @Inject
    private IProfileDao profileDao;

    @Inject
    private IKweetDao kweetDao;

    public void setUserDao(IUserDao userDao) {
        this.userDao = userDao;
    }

    public void setProfileDao(IProfileDao profileDao) {
        this.profileDao = profileDao;
    }

    public void setKweetDao(IKweetDao kweetDao) {this.kweetDao = kweetDao;}

    /**
     * Sets a profile for a user by username
     * If the user has already a profile, his/her current profile will get updated.
     * If the user has no profile, a new profile for this user will be created.
     *
     * @param username of the user with the new or updated profile
     * @param profileData needed for the creation or update of the profile
     * @throws InvalidProfileException when the profile name or web address of the profile is invalid
     */
    public void setProfile(String username, ProfileData profileData) throws InvalidProfileException {
        User owner = userDao.findByUsername(username);
        if (profileData.getName() == null || profileData.getName().isEmpty()) {
            throw new InvalidProfileException("Profile name can't be empty");
        } else if (profileData.getWebsite() == null || !validateUrl(profileData.getWebsite())) {
            throw new InvalidProfileException("The given web address is invalid");
        }

        createOrUpdateProfile(owner, profileData);
    }

    /**
     * Gets the profile data of a user by username.
     * The returned profile is in a view format and consists only of useful visual data.
     *
     * @param username of the user with the profile
     * @return the profile of the user in view format
     */
    public ProfileData getProfileData(String username) {
        Profile profile = profileDao.findByUser(userDao.findByUsername(username));
        if (profile != null) {
            return new ProfileData(profile.getName(), profile.getImage(), profile.getLocation(), profile.getwebsite(), profile.getBiography());
        }
        return null;
    }

    /**
     * Gets a view of total followers, following and kweets a user has by username.
     *
     * @param username of the user with the view data
     * @return total values of followers, following and kweets in view format
     */
    public UserTotalsView getUserTotals(String username) {
        return getUserTotals(userDao.findByUsername(username));
    }

    private boolean validateUrl(String url) {
        // Empty url is always true because a website is not required
        return url == null || url.equals("") || new UrlValidator(new String[] { "http", "https" }).isValid(url);
    }

    private UserTotalsView getUserTotals(User user) {
        int following = user.getFollowing().size();
        int followers = user.getFollowers().size();
        int kweets = kweetDao.findBySender(user).size();

        return new UserTotalsView(following, followers, kweets);
    }

    private void createOrUpdateProfile(User owner, ProfileData profileData) {
        Profile profile = profileDao.findByUser(owner);
        if (profile != null) {
            profileDao.update(convertToUpdatedProfile(profile, profileData));
        } else {
            Profile newProfile = convertToProfile(owner, profileData);
            profileDao.create(newProfile);
        }
    }

    private Profile convertToUpdatedProfile(Profile profile, ProfileData profileData) {
        if (!profile.getName().equals(profileData.getName())) {
            profile.setName(profileData.getName());
        }
        if (!profile.getImage().equals(profileData.getImage())) {
            profile.setImage(profileData.getImage());
        }
        if (!profile.getBiography().equals(profileData.getBio())) {
            profile.setBiography(profileData.getBio());
        }
        if (!profile.getLocation().equals(profileData.getLocation())) {
            profile.setLocation(profileData.getLocation());
        }
        if (!profile.getwebsite().equals(profileData.getWebsite())) {
            profile.setwebsite(profileData.getWebsite());
        }

        return profile;
    }

    private Profile convertToProfile(User owner, ProfileData profileData) {
        Profile profile = new Profile();
        profile.setName(profileData.getName());
        profile.setUser(owner);
        profile.setImage(profileData.getImage());
        profile.setwebsite(profileData.getWebsite());
        profile.setName(profileData.getName());
        profile.setBiography(profileData.getBio());
        profile.setLocation(profileData.getLocation());

        return profile;
    }
}
