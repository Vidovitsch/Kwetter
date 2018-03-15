package Service;

import DaoInterfaces.IProfileDao;
import DaoInterfaces.IUserDao;
import Domain.Profile;
import Domain.User;
import ViewModels.ProfileData;
import ViewModels.UserTotalsView;
import org.apache.commons.validator.UrlValidator;

import Exception.*;
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
        if (profileDao.findByUser(owner) != null)
        if (profileData.getName() == null || profileData.getName().isEmpty()) {
            throw new InvalidProfileException("Profile name can't be empty");
        } else if (profileData.getWebsite() != null && !validateUrl(profileData.getWebsite())) {
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
        Profile p = userDao.findByUsername(username).getProfile();
        return new ProfileData(p.getName(), p.getLocation(), p.getwebsite(), p.getBiography());
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
        // Default schemes = "http", "https", "ftp"
        return new UrlValidator(new String[] { "http", "https" }).isValid(url);
    }

    private UserTotalsView getUserTotals(User user) {
        int following = user.getFollowing().size();
        int followers = user.getFollowers().size();
        int kweets = user.getKweets().size();

        return new UserTotalsView(following, followers, kweets);
    }

    private void createOrUpdateProfile(User owner, ProfileData profileData) {
        if (profileDao.findByUser(owner) != null) {
            Profile updatedProfile = convertToProfile(owner, profileData);
            profileDao.update(updatedProfile);
        } else {
            Profile newProfile = convertToProfile(owner, profileData);
            profileDao.create(newProfile);
        }
    }

    private Profile convertToProfile(User owner, ProfileData profileData) {
        Profile profile = new Profile();
        profile.setName(profileData.getName());
        profile.setUser(owner);
        profile.setwebsite(profileData.getWebsite());
        profile.setName(profileData.getName());
        profile.setBiography(profileData.getBio());
        profile.setLocation(profileData.getLocation());

        return profile;
    }
}
