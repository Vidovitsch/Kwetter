package service_tests;

import comparators.KweetComparator;
import dao.interfaces.IKweetDao;
import dao.interfaces.IProfileDao;
import dao.interfaces.IUserDao;
import domain.Kweet;
import domain.Profile;
import domain.User;
import viewmodels.UserImageView;
import viewmodels.KweeterData;
import exceptions.*;

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

    @Inject
    private IProfileDao profileDao;

    public void setUserDao(IUserDao userDao) {
        this.userDao = userDao;
    }

    public void setKweetDao(IKweetDao kweetDao) {
        this.kweetDao = kweetDao;
    }

    public void setProfileDao(IProfileDao profileDao) {
        this.profileDao = profileDao;
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
        if (user != null) {
            KweeterData data = new KweeterData();

            setKweetData(data, username);

            // Set following and followers
            data.setFollowing(getUserImageViews(user.getFollowing()));
            data.setFollowers(getUserImageViews(user.getFollowers()));

            return data;
        }

        throw new UserNotFoundException();
    }

    private void setKweetData(KweeterData data, String username) {
        List<Kweet> sentKweets = kweetDao.findBySender(userDao.findByUsername(username));

        // Set total of sent kweets
        data.setTotalKweets(sentKweets.size());

        // Set laset kweet message and date
        if (!sentKweets.isEmpty()) {

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
                Profile profile = profileDao.findByUser(u);
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
