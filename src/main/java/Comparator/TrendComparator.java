package Comparator;

import Domain.Hashtag;
import java.util.Comparator;

public class TrendComparator implements Comparator<Hashtag> {

    public int compare(Hashtag o1, Hashtag o2) {
        if (o1.getTimesUsed() < o2.getTimesUsed()) {
            return 1;
        } else if (o1.getTimesUsed() > o2.getTimesUsed()) {
            return -1;
        }
        return 0;
    }
}
