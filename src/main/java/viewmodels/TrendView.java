package viewmodels;

import java.util.Date;

public class TrendView {

    private String name;
    private Date lastUsed;
    private int timesUsed;

    public TrendView() {
    }

    public TrendView(String name, Date lastUsed, int timesUsed) {
        this.name = name;
        this.lastUsed = lastUsed;
        this.timesUsed = timesUsed;
    }

    public String getHashtagName() {
        return name;
    }

    public void setHashtagName(String hashtagName) {
        this.name = hashtagName;
    }

    public Date getLastUsed() { return this.lastUsed; }

    public void setLastUsed(Date lastUsed) {
        this.lastUsed = lastUsed;
    }

    public int getTimesUsed() {
        return this.timesUsed;
    }

    public void setTimesUsed(int timesUsed) {
        this.timesUsed = timesUsed;
    }
}
