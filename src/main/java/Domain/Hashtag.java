package Domain;

import Util.Mockable;

import javax.persistence.*;
import java.util.*;

@Entity(name = "Hashtag")
@Table(name = "Hashtag")
@NamedQueries({
        @NamedQuery(name = "Hashtag.findByName", query = "SELECT a FROM Hashtag AS a WHERE a.name = :name")
})
public class Hashtag implements Mockable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "lastUsed")
    private Date lastUsed;

    @ManyToMany(mappedBy = "hashtags")
    private List<Kweet> kweets = new ArrayList<>();

    @Column(name = "timesUsed")
    private int timesUsed;

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
