package Service;

import DAO.Mock.KweetDaoMock;
import DAO.Mock.ProfileDaoMock;
import DAO.Mock.UserDaoMock;
import DaoInterfaces.IKweetDao;
import DaoInterfaces.IProfileDao;
import DaoInterfaces.IUserDao;
import Domain.Kweet;
import Domain.Profile;
import Domain.User;
import ViewModels.HomePageUserView;
import ViewModels.KweeterData;

import java.util.ArrayList;
import java.util.Collection;

public class KweeterDataService {
    IUserDao userDao = new UserDaoMock();
    IKweetDao kweetDao = new KweetDaoMock(userDao.findAll());
    IProfileDao profileDao = new ProfileDaoMock(userDao.findAll());

    public KweeterDataService() {
    }

    public KweeterData getKweeterData(long userID) {
        return getKweeterData(userDao.findById(userID).getUsername());
    }

    public KweeterData getKweeterData(String username) {
        KweeterData data = new KweeterData();
        Collection<Kweet> kweets = kweetDao.findBySenderName(username);
        data.setTotalKweets(kweets.size());
        if (data.getTotalKweets() != 0) {
            Kweet lastKweet = null;
            for (Kweet k : kweets) {
                if (lastKweet == null) {
                    lastKweet = k;
                } else if (lastKweet.getPublicationDate().before(k.getPublicationDate())) {
                    lastKweet = k;
                }
            }
            data.setLastKweetMessage(lastKweet.getMessage());
            data.setLastKweetDate(lastKweet.getPublicationDate());
        }

        User user = userDao.findByUsername(username);

        ArrayList<HomePageUserView> following = new ArrayList<>();
        if(user != null) {
            Collection<User> followingUsers = user.getFollowing();
            if (followingUsers != null) {
                for (User u : followingUsers) {
                    Profile p = u.getProfile();
                    following.add(new HomePageUserView(p.getName(), p.getImage()));
                }
                data.setFollowing(following);
            }

            ArrayList<HomePageUserView> followers = new ArrayList<>();
            Collection<User> followerUsers = user.getFollowers();
            if (followerUsers != null) {
                for (User u : followerUsers) {
                    Profile p = u.getProfile();
                    followers.add(new HomePageUserView(p.getName(), p.getImage()));
                }
                data.setFollowers(followers);
            }
        }

        return data;
    }
}
