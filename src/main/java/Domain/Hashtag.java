package Domain;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

@Entity(name = "Hashtag")
//@Table(name = "Hashtag")
public class Hashtag {

    // region Fields

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //@Column(name = "ID")
    private long id;

    //@Column(name = "Name", nullable = false)
    private String name;

    @Temporal(TemporalType.TIMESTAMP)
    //@Column(name = "LastUsedDate")
    private Date lastUsed;

    @ManyToMany(mappedBy = "hashtags")
    private Collection<Kweet> kweets = new HashSet<Kweet>();

    private int timesUsed;

    // endregion

    public Hashtag() { }

    public Hashtag(String name) {
        this.name = name;
    }

    public Hashtag(long id, String name) {
        this.id = id;
        this.name = name;
    }

    // region Getters and Setters

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getLastUsed() {
        return lastUsed;
    }

    public void setLastUsed(Date lastUsed) {
        this.lastUsed = lastUsed;
    }

    public Collection<Kweet> getKweets() {
        return kweets;
    }

    public void setKweets(Collection<Kweet> kweets) {
        this.kweets = kweets;
    }

    public int getTimesUsed() {
        return timesUsed;
    }

    public void setTimesUsed(int timesUsed) {
        this.timesUsed = timesUsed;
    }

    //endregion
}
