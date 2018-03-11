package ViewModels;

import Domain.Kweet;

import java.util.Collection;
import java.util.Date;

public class KweeterData {
    Date lastKweetDate;
    String lastKweetMessage;
    int totalKweets;
    Collection<HomePageUserView> following;
    Collection<HomePageUserView> followers;

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

    public Collection<HomePageUserView> getFollowing() {
        return following;
    }

    public void setFollowing(Collection<HomePageUserView> following) {
        this.following = following;
    }

    public Collection<HomePageUserView> getFollowers() {
        return followers;
    }

    public void setFollowers(Collection<HomePageUserView> followers) {
        this.followers = followers;
    }

    public KweeterData() {
    }


}
