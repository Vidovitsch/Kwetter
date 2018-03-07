package Services;

import DAO.Mock.KweetDaoMock;
import DAO.Mock.UserDaoMock;
import DaoInterfaces.IKweetDao;
import Domain.Kweet;
import Domain.User;
import ViewModels.TimeLineItem;

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
