package Service;

import DAO.Mock.KweetDaoMock;
import DAO.Mock.ProfileDaoMock;
import DAO.Mock.UserDaoMock;
import DaoInterfaces.IKweetDao;
import DaoInterfaces.IProfileDao;
import DaoInterfaces.IUserDao;
import Domain.Kweet;
import Domain.User;
import ViewModels.TimelineItem;
import ViewModels.UserUsernameView;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class TimelineService {

    IUserDao userDao;
    IKweetDao kweetDao;
    IProfileDao profileDao;

    public TimelineService() {
    }

    public TreeSet<TimelineItem> GenerateTimeLine(long userid) {
        User user = userDao.findById(userid);
        TreeSet<TimelineItem> TimeLine = new TreeSet<>();
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

    public TreeSet<TimelineItem> GenerateMentionsTimeLine(long userid) {
        User user = userDao.findById(userid);
        TreeSet<TimelineItem> mentionsTimeLine = new TreeSet<>();
        for (Kweet k : user.getMentions()) {
            if(k.getSender() == user){
                mentionsTimeLine.add(CreatTimeLineItem(k, true));
            }else{
                mentionsTimeLine.add(CreatTimeLineItem(k, false));
            }
        }
        return mentionsTimeLine;
    }

    public TreeSet<TimelineItem> TenMostRecentKweets(long userid){
        User user = userDao.findById(userid);
        TreeSet<TimelineItem> TimeLine = new TreeSet<TimelineItem>();
        for (Kweet k : user.getKweets()) {
            TimeLine.add(CreatTimeLineItem(k, true));
        }
        TreeSet<TimelineItem> TimeLineTen = new TreeSet<TimelineItem>();
        int i = 0;
        for(TimelineItem t : TimeLine){
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
        List<UserUsernameView> hearts = new ArrayList<>();
        for(User u : k.getHearts()){
            hearts.add(new UserUsernameView(u.getUsername(), u.getId()));
        }
        timelineItem.hearts = hearts;

        List<UserUsernameView> mentions = new ArrayList<>();
        for(User u : k.getHearts()){
            mentions.add(new UserUsernameView(u.getUsername(), u.getId()));
        }
        timelineItem.mentions = mentions;

        return timelineItem;
    }
}
