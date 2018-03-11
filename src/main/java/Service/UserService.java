package Service;

import DAO.Mock.ProfileDaoMock;
import DAO.Mock.UserDaoMock;
import DaoInterfaces.IProfileDao;
import DaoInterfaces.IUserDao;
import Domain.Profile;
import Domain.User;
import ViewModels.OtherUserView;

import java.util.ArrayList;
import java.util.List;

public class UserService {

    private IUserDao userDao;

    private IProfileDao profileDao;

    public UserService() {
        // Hardcode mock instances (replace by @Mock)
        this.userDao = new UserDaoMock();
        this.profileDao = new ProfileDaoMock();
    }

    public boolean createProfile(Profile profile) {
        if (profile.getName().equals("") || profile.getName() == null || profile.getUser() == null) {
            return false;
        } else {
            profileDao.create(profile);
            return true;
        }
    }

    public boolean editProfile(Profile profile) {
        return false;
    }

    public List<OtherUserView> getFollowers(String username) {
        User user = userDao.findByUsername(username);
        return generateOtherUserViews(user.getFollowers());
    }

    public List<OtherUserView> getFollowers(long userid) {
        User user = userDao.findById(userid);
        return generateOtherUserViews(user.getFollowers());
    }

    public List<OtherUserView> getFollowing(String username) {
        User user = userDao.findByUsername(username);
        return generateOtherUserViews(user.getFollowing());
    }

    public List<OtherUserView> getFollowing(long userid) {
        User user = userDao.findById(userid);
        return generateOtherUserViews(user.getFollowing());
    }

    private List<OtherUserView> generateOtherUserViews(List<User> users){
        ArrayList<OtherUserView> OtherUserViews = new ArrayList<>();
        for (User u : users) {
            String image = "";
            try {
                image = u.getProfile().getImage();
            } catch (Exception e) {
            }
            OtherUserViews.add(new OtherUserView(u.getUsername(), image));
        }
        return OtherUserViews;
    }
}

