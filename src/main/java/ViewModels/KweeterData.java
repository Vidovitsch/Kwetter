package ViewModels;

import Domain.Kweet;

import java.util.Collection;

public class KweeterData {
    Kweet lastKweet;
    int totalKweets;
    Collection<HomePageUserView> following;

    public Kweet getLastKweet() {
        return lastKweet;
    }

    public void setLastKweet(Kweet lastKweet) {
        this.lastKweet = lastKweet;
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

    Collection<HomePageUserView> followers;

    public KweeterData(){}


}
