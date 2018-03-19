package viewmodels;

import java.util.Date;
import java.util.List;

public class TimelineItem implements Comparable<TimelineItem> {

    public Long kweetID;
    public Date postDate;
    public String message;
    public String username;
    public List<UserUsernameView> hearts;
    public boolean ownKweet;
    public List<UserUsernameView> mentions;

    public int compareTo(TimelineItem o) {
        int first = o.postDate.compareTo(this.postDate);

        first = first == 0 ? this.postDate.compareTo(o.postDate) : first;
        if(first == 0 && kweetID.equals(o.kweetID)) {
            return -1;
        } else {
            return first;
        }
    }
}
