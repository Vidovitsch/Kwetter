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
    @Mock
    private IUserDao userDao;

    @Inject
    @Mock
    private IProfileDao profileDao;

    public void setUserDao(IUserDao userDao) {
        this.userDao = userDao;
    }

    public void setProfileDao(IProfileDao profileDao) {
        this.profileDao = profileDao;
    }

    public Boolean addFollowing(Long userId, Long followingId) {
        User followingUser = userDao.findById(userId);
        User followedUser = userDao.findById(followingId);

        List<User> following = followingUser.getFollowing();
        if (following.contains(followedUser)) {
            return false;
        } else {
            following.add(followedUser);
            followedUser.getFollowers().add(followingUser);
            return true;
        }
    }

    public List<OtherUserView> getFollowers(String username) {
        User user = userDao.findByUsername(username);
        return generateOtherUserViews(user.getFollowers());
    }

    // For testing
    public List<OtherUserView> getFollowers(Long userid) {
        User user = userDao.findById(userid);
        return generateOtherUserViews(user.getFollowers());
    }

    public List<OtherUserView> getFollowing(String username) {
        User user = userDao.findByUsername(username);
        return generateOtherUserViews(user.getFollowing());
    }

    // For testing
    public List<OtherUserView> getFollowing(Long userid) {
        User user = userDao.findById(userid);
        return generateOtherUserViews(user.getFollowing());
    }

    // For mocking
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

