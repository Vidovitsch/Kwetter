package Service;

import DaoInterfaces.IProfileDao;
import DaoInterfaces.IUserDao;
import Domain.Profile;
import Domain.User;
import Qualifier.Mock;
import ViewModels.OtherUserView;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Named(value = "userService")
@RequestScoped
public class UserService {

    @Inject @Mock
    private IUserDao userDao;

    @Inject @Mock
    private IProfileDao profileDao;

    public UserService() { }

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

    public void setUserDao(IUserDao userDao) {
        this.userDao = userDao;
    }

    public void setProfileDao(IProfileDao profileDao) {
        this.profileDao = profileDao;
    }
}

