package Service;

import DaoInterfaces.IProfileDao;
import DaoInterfaces.IUserDao;
import Domain.Profile;
import Domain.User;
import Qualifier.Mock;
import ViewModels.OtherUserView;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Named(value = "userService")
@Stateless
public class UserService {

    @Inject
    private IUserDao userDao;

    @Inject
    private IProfileDao profileDao;

    public void setUserDao(IUserDao userDao) {
        this.userDao = userDao;
    }

    public void setProfileDao(IProfileDao profileDao) {
        this.profileDao = profileDao;
    }

    public Boolean addFollowing(String username, String following) {
        User user = userDao.findByUsername(username);
        User followingUser = userDao.findByUsername(following);

        List<User> followingUsers = user.getFollowing();
        if (followingUsers.contains(followingUser)) {
            return false;
        } else {
            followingUsers.add(followingUser);
            followingUser.getFollowers().add(user);
            return true;
        }
    }

    public List<OtherUserView> getFollowers(String username) {
        User user = userDao.findByUsername(username);
        return generateOtherUserViews(user.getFollowers());
    }

    public List<OtherUserView> getFollowing(String username) {
        User user = userDao.findByUsername(username);
        return generateOtherUserViews(user.getFollowing());
    }

    private List<OtherUserView> generateOtherUserViews(List<User> users) {
        ArrayList<OtherUserView> OtherUserViews = new ArrayList<>();
        for (User user : users) {
            Profile profile = user.getProfile();
            String image;
            if (profile != null) {
                image = profile.getImage();
            } else {
                image = null;
            }
            OtherUserViews.add(new OtherUserView(user.getUsername(), image));
        }

        return OtherUserViews;
    }
}

