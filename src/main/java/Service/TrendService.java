package Service;

import Comparator.TrendComparator;
import DaoInterfaces.IHashtagDao;
import Domain.Hashtag;
import Qualifier.Mock;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

@Named(value = "trendService")
@Stateless
public class TrendService {

    @Inject @Mock
    private IHashtagDao hashtagDao;

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

    public void setHashtagDao(IHashtagDao hashtagDao) {
        this.hashtagDao = hashtagDao;
    }
}
