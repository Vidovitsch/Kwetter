package comparators;

import viewmodels.TimelineItem;

import java.util.Comparator;

public class TimelineItemComparator implements Comparator<TimelineItem> {

    public int compare(TimelineItem o1, TimelineItem o2) {
        if (o1.getPostDate().before(o2.getPostDate())) {
            return 1;
        } else if (o1.getPostDate().after(o2.getPostDate())) {
            return -1;
        }
        return 0;
    }
}
