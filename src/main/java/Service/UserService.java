package Service;

import DaoInterfaces.IProfileDao;
import DaoInterfaces.IUserDao;
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
        List<User> following = userDao.findById(userId).getFollowing();
        if (following.contains(userDao.findById(followingId))) {
            return false;
        } else {
            following.add(userDao.findById(followingId));
            return true;
        }
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

    // For mocking
    private List<OtherUserView> generateOtherUserViews(List<User> users) {
        ArrayList<OtherUserView> OtherUserViews = new ArrayList<>();
        for (User u : users) {
            String image = u.getProfile().getImage();
            OtherUserViews.add(new OtherUserView(u.getUsername(), image));
        }
        return OtherUserViews;
    }
}

