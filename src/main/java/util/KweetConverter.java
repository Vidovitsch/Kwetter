package util;

import dao.interfaces.IProfileDao;
import domain.Kweet;
import domain.Profile;
import domain.User;
import viewmodels.TimelineItem;
import viewmodels.UserUsernameView;

import java.util.ArrayList;
import java.util.List;

public class KweetConverter {

    public static TimelineItem toTimelineItem(Kweet kweet, boolean owner, IProfileDao profileDao) {
        TimelineItem timelineItem = new TimelineItem();
        timelineItem.setKweetId(kweet.getId());
        timelineItem.setPostDate(kweet.getPublicationDate());
        timelineItem.setMessage(kweet.getMessage());
        timelineItem.setUsername(kweet.getSender().getUsername());
        timelineItem.setOwnKweet(owner);
        timelineItem.setHearts(getUserViewsByHearts(kweet));
        timelineItem.setMentions(getUserViewsByMentions(kweet));

        Profile profile = profileDao.findByUser(kweet.getSender());
        if (profile != null) {
            timelineItem.setProfileName(profile.getName());
            timelineItem.setProfileImage(profile.getImage());
        }

        return timelineItem;
    }
    

    private static List<UserUsernameView> getUserViewsByHearts(Kweet kweet) {
        List<UserUsernameView> hearts = new ArrayList<>();
        for (User user : kweet.getHearts()) {
            hearts.add(new UserUsernameView(user.getUsername(), user.getId()));
        }

        return hearts;
    }

    private static List<UserUsernameView> getUserViewsByMentions(Kweet kweet) {
        List<UserUsernameView> mentions = new ArrayList<>();
        for (User user : kweet.getMentions()) {
            mentions.add(new UserUsernameView(user.getUsername(), user.getId()));
        }

        return mentions;
    }
}
