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

    // endregion

    public Hashtag() { }

    public Hashtag(String name) {
        this.name = name;
    }

    // region Getters and Setters

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getLastUsed() {
        return lastUsed;
    }

    public Collection<Kweet> getKweets() {
        return kweets;
    }

    //endregion
}
