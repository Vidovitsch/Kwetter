package Service;

import DaoInterfaces.IProfileDao;
import DaoInterfaces.IUserDao;
import Domain.Profile;
import Domain.User;
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

    /**
     * Adds a user(follwoing) that this user will follow
     *
     * @param username of the user that follows
     * @param following: the user that will get followed
     * @return true if the user follows a user successfully, false if the user already follows the user
     */
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

    /**
     * Gets a list of the followers of the user with the given username.
     * The list is in a view format and consists only of useful visual data.
     *
     * @param username of the user with the followers
     * @return a list of follwers in a view format
     */
    public List<OtherUserView> getFollowers(String username) {
        User user = userDao.findByUsername(username);
        return generateOtherUserViews(user.getFollowers());
    }

    /**
     * Gets a list of the following of the user with the given username.
     * The list is in a view format and consists only of useful visual data.
     *
     * @param username of the user with the following
     * @return a list of following in a view format
     */
    public List<OtherUserView> getFollowing(String username) {
        User user = userDao.findByUsername(username);
        return generateOtherUserViews(user.getFollowing());
    }

    private List<OtherUserView> generateOtherUserViews(List<User> users) {
        ArrayList<OtherUserView> OtherUserViews = new ArrayList<>();
        for (User user : users) {
            Profile profile = profileDao.findByUser(user);
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

