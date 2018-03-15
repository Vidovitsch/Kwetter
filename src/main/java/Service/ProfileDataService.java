package Service;

import DaoInterfaces.IProfileDao;
import DaoInterfaces.IUserDao;
import Domain.Profile;
import Domain.User;
import Qualifier.Mock;
import ViewModels.ProfileData;
import ViewModels.UserTotalsView;
import org.apache.commons.validator.UrlValidator;


import Exception.*;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

@Named(value = "profileDataService")
@Stateless
public class ProfileDataService {

    @Inject
    private IUserDao userDao;

    @Inject
    private IProfileDao profileDao;

    public ProfileDataService() {
    }

    public ProfileData CreateProfile(long userid, ProfileData profileData) throws InvalidProfileException {
        if(profileDao.findByUser(userDao.findById(userid)) != null){
            throw new InvalidProfileException("Profile already created, update instead");
        }
        if (profileData.getName() == null || profileData.getName().isEmpty())
            throw new InvalidProfileException("Profile name can't be empty");
        if (profileData.getWebsite() != null) {
            if (!ValidateUrl(profileData.getWebsite()))
                throw new InvalidProfileException("The given web address is invalid");
        }
        Profile p = new Profile(userid, profileData.getName());
        p.setwebsite(profileData.getWebsite());
        p.setName(profileData.getName());
        p.setBiography(profileData.getBio());
        p.setLocation(profileData.getLocation());
        profileDao.create(p);
        return GetProfileData(userid);
    }

    private boolean ValidateUrl(String url) {
        String[] schemes = {"http", "https"}; // DEFAULT schemes = "http", "https", "ftp"
        UrlValidator urlValidator = new UrlValidator(schemes);
        if (urlValidator.isValid(url)) {
            return true;
        } else {
            return false;
        }
    }

    public ProfileData GetProfileData(long userid) {
        Profile p = userDao.findById(userid).getProfile();
        ProfileData profileData = new ProfileData(p.getName(), p.getLocation(), p.getwebsite(), p.getBiography());
        return profileData;
    }

    public ProfileData GetProfileData(String username) {
        Profile p = userDao.findByUsername(username).getProfile();
        ProfileData profileData = new ProfileData(p.getName(), p.getLocation(), p.getwebsite(), p.getBiography());
        return profileData;
    }

    public UserTotalsView GetUserTotals(long userid) {
        User u = userDao.findById(userid);
        return GetUserTotals(u);
    }

    public UserTotalsView GetUserTotals(String username) {
        User u = userDao.findByUsername(username);
        return GetUserTotals(u);
    }

    private UserTotalsView GetUserTotals(User u) {
        int following = u.getFollowing().size();
        int followers = u.getFollowers().size();
        int kweets = u.getKweets().size();
        UserTotalsView userTotalsView = new UserTotalsView(following, followers, kweets);
        return userTotalsView;
    }

    public ProfileData UpdateProfile(long userid, ProfileData profileData) {
        try {
            if (userDao.findById(userid).getProfile() == null)
                throw new InvalidProfileException("This profile does not have a user linked yet");
            if (profileData.getName() == null) throw new InvalidProfileException("Profile name can't be empty");
            if (profileData.getWebsite() != null) {
                if (!ValidateUrl(profileData.getWebsite()))
                    throw new InvalidProfileException("The given web address is invalid");
            }
            Profile p = profileDao.findByUser(userDao.findById(userid));
            p.setwebsite(profileData.getWebsite());
            p.setName(profileData.getName());
            p.setBiography(profileData.getBio());
            p.setLocation(profileData.getLocation());
            profileDao.update(p);
            return GetProfileData(userid);
        } catch (Exception e) {
            throw (EJBException) new EJBException(e).initCause(e);
        }
    }
}
