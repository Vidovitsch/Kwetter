package viewmodels;

import java.util.Date;
import java.util.List;

public class KweeterData {

    private Date lastKweetDate;
    private String lastKweetMessage;
    private int totalKweets;
    private List<UserImageView> following;
    private List<UserImageView> followers;

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

    public List<UserImageView> getFollowing() {
        return following;
    }

    public void setFollowing(List<UserImageView> following) {
        this.following = following;
    }

    public List<UserImageView> getFollowers() {
        return followers;
    }

    public void setFollowers(List<UserImageView> followers) {
        this.followers = followers;
    }
}
