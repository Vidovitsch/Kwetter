package Service;

import DaoInterfaces.IKweetDao;
import DaoInterfaces.IUserDao;
import Domain.Kweet;
import Domain.User;
import Qualifier.Mock;
import ViewModels.TimelineItem;
import ViewModels.UserUsernameView;

import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Named(value = "timelineService")
@RequestScoped
public class TimelineService {

    @Inject @Mock
    private IUserDao userDao;

    @Inject @Mock
    private IKweetDao kweetDao;

    public TimelineService() { }

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

    public Set<TimelineItem> MostRecentKweets(long userid, int amount) {
        User user = userDao.findById(userid);
        TreeSet<TimelineItem> TimeLine = new TreeSet<TimelineItem>();
        for (Kweet k : user.getKweets()) {
            TimeLine.add(CreatTimeLineItem(k, true));
        }
        TreeSet<TimelineItem> requestedItems = new TreeSet<TimelineItem>();
        int i = 0;
        for(TimelineItem t : TimeLine){
            requestedItems.add(t);
            i++;
            if(i>=amount){break;}
        }
        return requestedItems;
    }

    public TreeSet<TimelineItem> GenerateTimeLine(String userid) {
        User user = userDao.findByUsername(userid);
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

    public TreeSet<TimelineItem> GenerateMentionsTimeLine(String userid) {
        User user = userDao.findByUsername(userid);
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

    public Set<TimelineItem> MostRecentKweets(String userid, int amount) {
        User user = userDao.findByUsername(userid);
        TreeSet<TimelineItem> TimeLine = new TreeSet<TimelineItem>();
        for (Kweet k : user.getKweets()) {
            TimeLine.add(CreatTimeLineItem(k, true));
        }
        TreeSet<TimelineItem> requestedItems = new TreeSet<TimelineItem>();
        int i = 0;
        for(TimelineItem t : TimeLine){
            requestedItems.add(t);
            i++;
            if(i>=amount){break;}
        }
        return requestedItems;
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
