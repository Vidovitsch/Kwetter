package ViewModels;

import Domain.User;

import java.util.Collection;
import java.util.Date;

public class TimeLineItem implements Comparable{

    public long kweetID;
    public Date postDate;
    public String message;
    public String username;
    public Collection<User> hearts;
    public boolean ownKweet;
    public Collection<User> mentions;

    public int compareTo(Object o) {
        return this.postDate.compareTo(((TimeLineItem)o).postDate);
    }
}
