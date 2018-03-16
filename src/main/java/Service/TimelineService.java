package Service;

import DaoInterfaces.IKweetDao;
import DaoInterfaces.IUserDao;
import Domain.Kweet;
import Domain.User;
import ViewModels.TimelineItem;
import ViewModels.UserUsernameView;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

@Named(value = "timelineService")
@Stateless
public class TimelineService {

    @Inject
    private IUserDao userDao;

    @Inject
    private IKweetDao kweetDao;

    public void setUserDao(IUserDao userDao) {
        this.userDao = userDao;
    }

    public void setKweetDao(IKweetDao kweetDao) {
        this.kweetDao = kweetDao;
    }

    /**
     * Generates a timeline for a specific user by username.
     * The timeline consists of kweets sent by the user and kweets sent by the users this user follows.
     * The timeline is sorted from recent to older.
     *
     * @param username of the user of the timeline
     * @return a collection of timeline items (timeline)
     */
    public TreeSet<TimelineItem> generateTimeline(String username) {
        User user = userDao.findByUsername(username);
        TreeSet<TimelineItem> timeline = new TreeSet<>();
        timeline.addAll(getOwnKweets(user));
        timeline.addAll(getFollowingKweets(user));

        return timeline;
    }

    /**
     * Generates a timeline for a specific user by username.
     * The timeline consists of kweets that this user got mentioned in.
     * The timeline is sorted from recent to older.
     *
     * @param username of the user of the timeline
     * @return a collection of timeline items (timeline)
     */
    public TreeSet<TimelineItem> generateMentionsTimeline(String username) {
        User user = userDao.findByUsername(username);

        return new TreeSet<>(getMentionedKweets(user));
    }

    /**
     * Get the (n) most recent kweets of the user by username.
     *
     * @param username of the user of the timeline
     * @param amount the amount of recent kweets in the timeline
     * @return a list of timeline items (timeline)
     */
    public List<TimelineItem> mostRecentKweets(String username, int amount) {
        User user = userDao.findByUsername(username);
        TreeSet<TimelineItem> timeline = new TreeSet<>(getOwnKweets(user));

        return new ArrayList<>(timeline).subList(0, amount);
    }

    private List<TimelineItem> getOwnKweets(User owner) {
        List<TimelineItem> timeline = new ArrayList<>();
        for (Kweet kweet : owner.getKweets()) {
            timeline.add(creatTimelineItem(kweet, true));
        }

        return timeline;
    }

    private List<TimelineItem> getFollowingKweets(User user) {
        List<TimelineItem> timeline = new ArrayList<>();
        for (User followingUser : user.getFollowing()) {
            for (Kweet kweet : kweetDao.findBySender(followingUser)) {
                timeline.add(creatTimelineItem(kweet, false));
            }
        }

        return timeline;
    }

    private List<TimelineItem> getMentionedKweets(User user) {
        List<TimelineItem> timeline = new ArrayList<>();
        for (Kweet kweet : user.getMentions()) {
            timeline.add(creatTimelineItem(kweet, false));
        }

        return timeline;
    }

    private TimelineItem creatTimelineItem(Kweet kweet, boolean owner) {
        TimelineItem timelineItem = new TimelineItem();
        timelineItem.kweetID = kweet.getId();
        timelineItem.postDate = kweet.getPublicationDate();
        timelineItem.message = kweet.getMessage();
        timelineItem.username = kweet.getSender().getUsername();
        timelineItem.ownKweet = owner;
        timelineItem.hearts = getUserViewsByHearts(kweet);
        timelineItem.mentions = getUserViewsByMentions(kweet);

        return timelineItem;
    }

    private List<UserUsernameView> getUserViewsByHearts(Kweet kweet) {
        List<UserUsernameView> hearts = new ArrayList<>();
        for (User user : kweet.getHearts()) {
            hearts.add(new UserUsernameView(user.getUsername(), user.getId()));
        }

        return hearts;
    }

    private List<UserUsernameView> getUserViewsByMentions(Kweet kweet) {
        List<UserUsernameView> mentions = new ArrayList<>();
        for (User user : kweet.getMentions()) {
            mentions.add(new UserUsernameView(user.getUsername(), user.getId()));
        }

        return mentions;
    }
}
