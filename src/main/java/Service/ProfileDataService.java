package Service;

import DaoInterfaces.IUserDao;
import Domain.Profile;
import Domain.User;
import Qualifier.Mock;
import ViewModels.ProfileDataView;
import ViewModels.UserTotalsView;

import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named(value = "profileDataService")
@RequestScoped
public class ProfileDataService {

    @Inject @Mock
    private IUserDao userDao;

    public ProfileDataService() { }

    public ProfileDataView GetProfileData(long userid){
        Profile p = userDao.findById(userid).getProfile();
        ProfileDataView profileDataView = new ProfileDataView(p.getName(), p.getLocation(), p.getwebsite(), p.getBiography());
        return profileDataView;
    }

    public ProfileDataView GetProfileData(String username){
        Profile p = userDao.findByUsername(username).getProfile();
        ProfileDataView profileDataView = new ProfileDataView(p.getName(), p.getLocation(), p.getwebsite(), p.getBiography());
        return profileDataView;
    }

    public UserTotalsView GetUserTotals(long userid){
        User u = userDao.findById(userid);
        return GetUserTotals(u);
    }

    public UserTotalsView GetUserTotals(String username){
        User u = userDao.findByUsername(username);
        return GetUserTotals(u);
    }

    private UserTotalsView GetUserTotals(User u){
        int following = u.getFollowing().size();
        int followers = u.getFollowers().size();
        int kweets = u.getKweets().size();
        UserTotalsView userTotalsView = new UserTotalsView(following,followers,kweets);
        return userTotalsView;
    }

    public void setUserDao(IUserDao userDao) {
        this.userDao = userDao;
    }
}
