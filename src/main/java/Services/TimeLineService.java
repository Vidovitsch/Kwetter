package Services;

import DAO.Mock.KweetDaoMock;
import DAO.Mock.UserDaoMock;
import DaoInterfaces.IKweetDao;
import Domain.Kweet;
import Domain.User;
import ViewModels.TimeLineItem;

import java.sql.Time;
import java.util.PriorityQueue;
import java.util.TreeSet;

public class TimeLineService {

    IKweetDao kweetDao = new KweetDaoMock((new UserDaoMock()).findAll());

    public TimeLineService() {
    }

    public TreeSet<TimeLineItem> GenerateTimeLine(User user) {
        TreeSet<TimeLineItem> TimeLine = new TreeSet<TimeLineItem>();
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

    public TreeSet<TimeLineItem> TenMostRecentKweets(User user){
        TreeSet<TimeLineItem> TimeLine = new TreeSet<TimeLineItem>();
        for (Kweet k : user.getKweets()) {
            TimeLine.add(CreatTimeLineItem(k, true));
        }
        TreeSet<TimeLineItem> TimeLineTen = new TreeSet<TimeLineItem>();
        int i = 0;
        for(TimeLineItem t : TimeLineTen){
            TimeLineTen.add(t);
            i++;
            if(i>9){break;}
        }
        return TimeLineTen;
    }

    private TimeLineItem CreatTimeLineItem(Kweet k, boolean owner) {
        TimeLineItem timeLineItem = new TimeLineItem();
        timeLineItem.kweetID = k.getId();
        timeLineItem.postDate = k.getPublicationDate();
        timeLineItem.message = k.getMessage();
        timeLineItem.username = k.getSender().getUsername();
        timeLineItem.ownKweet = owner;
        timeLineItem.hearts = k.getHearts();
        timeLineItem.mentions = k.getMentions();
        return timeLineItem;
    }
}
