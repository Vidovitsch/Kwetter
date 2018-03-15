package Service;

import Comparator.KweetComparator;
import DaoInterfaces.IKweetDao;
import DaoInterfaces.IUserDao;
import Domain.Kweet;
import Domain.Profile;
import Domain.User;
import ViewModels.UserImageView;
import ViewModels.KweeterData;
import Exception.*;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Named(value = "kweeterDataService")
@Stateless
public class KweeterDataService {

    @Inject
    private IUserDao userDao;

    @Inject
    private IKweetDao kweetDao;

    public KweeterData getKweeterData(Long userId) throws UserNotFoundException {
        return getKweeterData(userDao.findById(userId).getUsername());
    }

    public void setUserDao(IUserDao userDao) {
        this.userDao = userDao;
    }

    public void setKweetDao(IKweetDao kweetDao) {
        this.kweetDao = kweetDao;
    }

    /**
     * Gets the data of a user by username.
     * This data consists of image view of the last kweet message, last kweet date, followers and following.
     *
     * @param username of the user with kweeter data
     * @return kweeter data in a view format
     * @throws UserNotFoundException when the user with the given username doesn't exist
     */
    public KweeterData getKweeterData(String username) throws UserNotFoundException {
        User user = userDao.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException();
        } else {
            KweeterData data = new KweeterData();

            setKweetData(data, username);

            // Set following and followers
            data.setFollowing(getUserImageViews(user.getFollowing()));
            data.setFollowers(getUserImageViews(user.getFollowers()));

            return data;
        }
    }

    private void setKweetData(KweeterData data, String username) {
        List<Kweet> sentKweets = kweetDao.findBySender(userDao.findByUsername(username));

        // Set total of sent kweets
        data.setTotalKweets(sentKweets.size());

        // Set laset kweet message and date
        if (sentKweets.size() > 0) {

            // Sort to get the last kweet on first index
            sentKweets.sort(new KweetComparator());

            Kweet lastKweet = sentKweets.get(0);
            data.setLastKweetMessage(lastKweet.getMessage());
            data.setLastKweetDate(lastKweet.getPublicationDate());
        }
    }

    private List<UserImageView> getUserImageViews(List<User> users) {
        List<UserImageView> followers = new ArrayList<>();
        if (users != null) {
            for (User u : users) {
                Profile profile = u.getProfile();
                if (profile != null) {
                    // Add user profile data
                    followers.add(new UserImageView(profile.getName(), profile.getImage()));
                } else {
                    // User has no profile. Place an empty one
                    followers.add(new UserImageView(null, null));
                }
            }
        }

        return followers;
    }
}
