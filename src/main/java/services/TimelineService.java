package services;

import comparators.TimelineItemComparator;
import dao.interfaces.IKweetDao;
import dao.interfaces.IUserDao;
import domain.Kweet;
import domain.User;
import util.KweetConverter;
import viewmodels.TimelineItem;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

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
    public List<TimelineItem> generateTimeline(String username) {
        User user = userDao.findByUsername(username);
        List<TimelineItem> timeline = new ArrayList<>();
        timeline.addAll(getOwnKweets(user));
        timeline.addAll(getFollowingKweets(user));
        timeline.sort(new TimelineItemComparator());

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
    public List<TimelineItem> generateMentionsTimeline(String username) {
        User user = userDao.findByUsername(username);
        List<TimelineItem> mentionedKweets = getMentionedKweets(user);
        mentionedKweets.sort(new TimelineItemComparator());

        return mentionedKweets;
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
        List<TimelineItem> ownKweets = getOwnKweets(user);
        ownKweets.sort(new TimelineItemComparator());

        return ownKweets.subList(0, amount);
    }

    private List<TimelineItem> getOwnKweets(User owner) {
        List<TimelineItem> timeline = new ArrayList<>();
        for (Kweet kweet : owner.getKweets()) {
            timeline.add(KweetConverter.toTimelineItem(kweet, true));
        }

        return timeline;
    }

    private List<TimelineItem> getFollowingKweets(User user) {
        List<TimelineItem> timeline = new ArrayList<>();
        for (User followingUser : user.getFollowing()) {
            for (Kweet kweet : kweetDao.findBySender(followingUser)) {
                timeline.add(KweetConverter.toTimelineItem(kweet, false));
            }
        }

        return timeline;
    }

    private List<TimelineItem> getMentionedKweets(User user) {
        List<TimelineItem> timeline = new ArrayList<>();
        for (Kweet kweet : user.getMentions()) {
            timeline.add(KweetConverter.toTimelineItem(kweet, false));
        }

        return timeline;
    }
}
