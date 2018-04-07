package viewmodels;

public class TrendView {
    String hashtagName;

    public TrendView() {
    }

    public TrendView(String hashtagName) {

        this.hashtagName = hashtagName;
    }

    public String getHashtagName() {
        return hashtagName;
    }

    public void setHashtagName(String hashtagName) {
        this.hashtagName = hashtagName;
    }
}
