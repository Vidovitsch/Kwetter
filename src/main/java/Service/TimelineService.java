package Service;

import DAO.Mock.KweetDaoMock;
import DAO.Mock.UserDaoMock;
import DaoInterfaces.IKweetDao;
import Domain.Kweet;
import Domain.User;
import ViewModels.TimelineItem;

import java.util.TreeSet;

public class TimelineService {

    IKweetDao kweetDao = new KweetDaoMock((new UserDaoMock()).findAll());

    public TimelineService() {
    }

    public TreeSet<TimelineItem> GenerateTimeLine(User user) {
        TreeSet<TimelineItem> TimeLine = new TreeSet<TimelineItem>();
        for (Kweet k : user.getKweets()) {
            TimeLine.add(CreatTimeLineItem(k, true));
        }
        for (User u : user.getFollowing()) {
            for (Kweet k : kweetDao.findBySenderName(u.getUsername())) {
                TimeLine.add(CreatTimeLineItem(k, false));
            }
        }
        return TimeLine;
    }

    public TreeSet<TimelineItem> TenMostRecentKweets(User user){
        TreeSet<TimelineItem> TimeLine = new TreeSet<TimelineItem>();
        for (Kweet k : user.getKweets()) {
            TimeLine.add(CreatTimeLineItem(k, true));
        }
        TreeSet<TimelineItem> TimeLineTen = new TreeSet<TimelineItem>();
        int i = 0;
        for(TimelineItem t : TimeLineTen){
            TimeLineTen.add(t);
            i++;
            if(i>9){break;}
        }
        return TimeLineTen;
    }

    private TimelineItem CreatTimeLineItem(Kweet k, boolean owner) {
        TimelineItem timelineItem = new TimelineItem();
        timelineItem.kweetID = k.getId();
        timelineItem.postDate = k.getPublicationDate();
        timelineItem.message = k.getMessage();
        timelineItem.username = k.getSender().getUsername();
        timelineItem.ownKweet = owner;
        timelineItem.hearts = k.getHearts();
        timelineItem.mentions = k.getMentions();

        return timelineItem;
    }
}
