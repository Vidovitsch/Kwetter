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

    // Column count instead of 'GROUP BY' query on linked table.
    // This prevents intensive 'GROUP BY' queries on every reload on the 'trend' page.
    //@Column(name = "Count")
    private long count;

    @OneToMany(mappedBy = "hashtags")
    private Collection<Kweet> kweets = new HashSet<Kweet>();

    // endregion

    public Hashtag() { }

    public Hashtag(String name) {
        this.name = name;
    }

    // region Getters and Setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public Collection<Kweet> getKweets() {
        return kweets;
    }

    public void setKweets(HashSet<Kweet> kweets) {
        this.kweets = kweets;
    }

    //endregion
}
