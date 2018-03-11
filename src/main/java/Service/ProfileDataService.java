package Service;

import DAO.Mock.UserDaoMock;
import DaoInterfaces.IUserDao;
import Domain.Profile;
import Domain.User;
import ViewModels.ProfileDataView;
import ViewModels.UserTotalsView;

public class ProfileDataService {

    private IUserDao userDao;

    public ProfileDataService() {
        // Hardcode mock instances (replace by @Mock)
        this.userDao = new UserDaoMock();
    }

    public ProfileDataView GetProfileData(long userid){
        Profile p = userDao.findById(userid).getProfile();
        ProfileDataView profileDataView = new ProfileDataView(p.getName(), p.getLocation(), p.getwebsite(), p.getBiography());
        return profileDataView;
    }

    public UserTotalsView GetUserTotals(long userid){
        User u = userDao.findById(userid);
        int following = u.getFollowing().size();
        int followers = u.getFollowers().size();
        int kweets = u.getKweets().size();
        UserTotalsView userTotalsView = new UserTotalsView(following,followers,kweets);
        return userTotalsView;
    }
}
