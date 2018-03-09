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
import java.util.Date;

public class KweeterDataService {
    IUserDao userDao = new UserDaoMock();
    IKweetDao kweetDao = new KweetDaoMock(userDao.findAll());
    IProfileDao profileDao = new ProfileDaoMock(userDao.findAll());

    public KweeterDataService() {
    }

    public KweeterData getKweeterData(String username) {
        KweeterData data = new KweeterData();
        Collection<Kweet> kweets = kweetDao.findBySenderName(username);
        data.setTotalKweets(kweets.size());
        Kweet lastKweet = null;
        for (Kweet k : kweets) {
            if (lastKweet == null) {
                lastKweet = k;
            }else if(lastKweet.getPublicationDate().before(k.getPublicationDate())){
                lastKweet = k;
            }
        }
        data.setLastKweet(lastKweet);
        ArrayList<HomePageUserView> following = new ArrayList<HomePageUserView>();
        for (User u : userDao.findByUsername(username).getFollowing()) {
            Profile p = u.getProfile();
            following.add(new HomePageUserView(p.getName(), p.getImage()));
        }
        data.setFollowing(following);
        ArrayList<HomePageUserView> followers = new ArrayList<HomePageUserView>();
        for (User u : userDao.findByUsername(username).getFollowers()) {
            Profile p = u.getProfile();
            following.add(new HomePageUserView(p.getName(), p.getImage()));
        }
        data.setFollowers(followers);
        return data;
    }
}
