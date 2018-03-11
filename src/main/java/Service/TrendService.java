package Service;

import Comparator.TrendComparator;
import DAO.Mock.HashtagDaoMock;
import DAO.Mock.KweetDaoMock;
import DAO.Mock.ProfileDaoMock;
import DAO.Mock.UserDaoMock;
import DaoInterfaces.IHashtagDao;
import DaoInterfaces.IKweetDao;
import DaoInterfaces.IProfileDao;
import DaoInterfaces.IUserDao;
import Domain.Hashtag;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.*;

//@Stateless
public class TrendService {

    IUserDao userDao = new UserDaoMock();
    IKweetDao kweetDao = new KweetDaoMock(userDao.findAll());
    IProfileDao profileDao = new ProfileDaoMock(userDao.findAll());
    //@EJB
    private IHashtagDao hashtagDao = new HashtagDaoMock(kweetDao.findAll());


    public TrendService() { }

    /**
     * To Do
     *
     * @return
     */
    public List<String> get() {
        List<Hashtag> trends = filterOnDates(hashtagDao.findAll());
        Collections.sort(trends, new TrendComparator());

        return convertToNameList(trends);
    }

    private List<Hashtag> filterOnDates(List<Hashtag> trends) {
        List<Hashtag> filteredTrends = new ArrayList<>();
        Date weekAgo = getDateWeekAgo();
        for (Hashtag trend : trends) {
            if (trend.getLastUsed().after(weekAgo)) {
                filteredTrends.add(trend);
            }
        }

        return filteredTrends;
    }

    private Date getDateWeekAgo() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, -7);

        return cal.getTime();
    }

    private List<String> convertToNameList(List<Hashtag> hashtags) {
        List<String> names = new ArrayList<>();
        for (Hashtag hashtag : hashtags) {
            names.add(hashtag.getName());
        }

        return names;
    }
}
