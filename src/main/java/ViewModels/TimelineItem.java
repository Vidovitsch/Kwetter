package ViewModels;

import java.util.Date;
import java.util.List;

public class TimelineItem implements Comparable {

    public long kweetID;
    public Date postDate;
    public String message;
    public String username;
    public List<UserUsernameView> hearts;
    public boolean ownKweet;
    public List<UserUsernameView> mentions;

    public int compareTo(Object o) {
        int first = this.postDate.compareTo(((TimelineItem) o).postDate);
        first = first == 0 ? this.postDate.compareTo(((TimelineItem)o).postDate) : first;
        if(first == 0 && kweetID != ((TimelineItem) o).kweetID){
            return -1;
        }else {
            return 0;
        }
    }
}
