package ViewModels;

public class UserTotalsView {
    
    int following;
    int followers;
    int kweets;
    
    public UserTotalsView(int following, int followers, int kweets) {
        this.following = following;
        this.followers = followers;
        this.kweets = kweets;
    }
    
    public UserTotalsView() {
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public int getkweets() {
        return kweets;
    }

    public void setkweets(int kweets) {
        this.kweets = kweets;
    }
}
