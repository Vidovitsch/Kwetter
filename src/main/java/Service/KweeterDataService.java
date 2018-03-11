package Service;

import Comparator.KweetComparator;
import DAO.Mock.KweetDaoMock;
import DAO.Mock.UserDaoMock;
import DaoInterfaces.IKweetDao;
import DaoInterfaces.IUserDao;
import Domain.Kweet;
import Domain.Profile;
import Domain.User;
import ViewModels.UserImageView;
import ViewModels.KweeterData;
import Exception.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KweeterDataService {

    private IUserDao userDao = new UserDaoMock();
    private IKweetDao kweetDao = new KweetDaoMock(userDao.findAll());

    public KweeterDataService() { }

    public KweeterDataService(IUserDao userDao, IKweetDao kweetDao) {
        this.userDao = userDao;
        this.kweetDao = kweetDao;
    }

    public KweeterData getKweeterData(Long userId) throws UserNotFoundException {
        return getKweeterData(userDao.findById(userId).getUsername());
    }

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
        List<Kweet> sentKweets = kweetDao.findBySenderName(username);

        // Set total of sent kweets
        data.setTotalKweets(sentKweets.size());

        // Set laset kweet message and date
        if (sentKweets.size() > 0) {

            // Sort to get the last kweet on first index
            Collections.sort(sentKweets, new KweetComparator());

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
