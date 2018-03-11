package ViewModels;

import java.util.Collection;
import java.util.Date;

public class KweeterData {

    private Date lastKweetDate;
    private String lastKweetMessage;
    private int totalKweets;
    private Collection<HomePageUserView> following;
    private Collection<HomePageUserView> followers;

    public Date getLastKweetDate() {
        return lastKweetDate;
    }

    public void setLastKweetDate(Date lastKweetDate) {
        this.lastKweetDate = lastKweetDate;
    }

    public String getLastKweetMessage() {
        return lastKweetMessage;
    }

    public void setLastKweetMessage(String lastKweetMessage) {
        this.lastKweetMessage = lastKweetMessage;
    }

    public int getTotalKweets() {
        return totalKweets;
    }

    public void setTotalKweets(int totalKweets) {
        this.totalKweets = totalKweets;
    }

    public Collection<UserImageView> getFollowing() {
        return following;
    }

    public void setFollowing(Collection<UserImageView> following) {
        this.following = following;
    }

    public Collection<UserImageView> getFollowers() {
        return followers;
    }

    public void setFollowers(Collection<UserImageView> followers) {
        this.followers = followers;
    }
}
