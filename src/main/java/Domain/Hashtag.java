package Domain;

import javax.persistence.*;
import java.util.*;

@Entity(name = "Hashtag")
public class Hashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUsed;

    @ManyToMany(mappedBy = "hashtags")
    private List<Kweet> kweets = new ArrayList<Kweet>();
    private int timesUsed = 0;

    public Hashtag() { }

    public Hashtag(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public List<Kweet> getKweets() {
        return kweets;
    }

    public void setKweets(List<Kweet> kweets) {
        this.kweets = kweets;
    }

    public int getTimesUsed() {
        return timesUsed;
    }

    public void setTimesUsed(int timesUsed) {
        this.timesUsed = timesUsed;
    }
}
