package ViewModels;

import Domain.User;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class TimelineItem implements Comparable {

    public long kweetID;
    public Date postDate;
    public String message;
    public String username;
    public Set<User> hearts;
    public boolean ownKweet;
    public List<User> mentions;

    public int compareTo(Object o) {
        return this.postDate.compareTo(((TimelineItem)o).postDate);
    }
}
