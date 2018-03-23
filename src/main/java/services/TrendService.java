package services;

import comparators.TrendComparator;
import dao.interfaces.IHashtagDao;
import domain.Hashtag;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

@Named(value = "trendService")
@Stateless
public class TrendService {

    @Inject
    private IHashtagDao hashtagDao;

    public void setHashtagDao(IHashtagDao hashtagDao) {
        this.hashtagDao = hashtagDao;
    }

    /**
     * Gets the current trend of hashtags.
     * The trend is measured by two factors:
     *      1. The number of times the hashtag is used in kweets.
     *      2. The latest use of the hashtag in a kweet.
     *          If the difference between the latest date and the current date is bigger than 7 days,
     *          the hashtag can't be a trend anymore, despite the number of uses.
     * @return A list of hashtag names
     */
    public List<String> get() {
        List<Hashtag> trends = filterOnDates(hashtagDao.findAll());
        trends.sort(new TrendComparator());

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
