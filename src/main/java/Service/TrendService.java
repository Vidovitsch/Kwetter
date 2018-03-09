package Service;

import Comparator.TrendComparator;
import DaoInterfaces.IHashtagDao;
import DaoInterfaces.IKweetDao;
import Domain.Hashtag;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.*;

@Stateless
public class TrendService {

    @EJB
    private IHashtagDao hashtagDao;

    public TrendService() { }

    /**
     * To Do
     *
     * @return
     */
    public List<Hashtag> get() {
        List<Hashtag> trends = filterOnDates(hashtagDao.findAll());
        Collections.sort(trends, new TrendComparator());

        return trends;
    }

    private List<Hashtag> filterOnDates(List<Hashtag> trends) {
        List<Hashtag> filteredTrends = new ArrayList<Hashtag>();
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
}
