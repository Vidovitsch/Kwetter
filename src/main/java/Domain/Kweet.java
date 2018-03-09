package Domain;

import javax.persistence.*;
import java.util.*;

@Entity(name = "Kweet")
public class Kweet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String message;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User sender;

    @ManyToMany(mappedBy = "mentions")
    private List<User> mentions = new ArrayList<User>();

    @ManyToMany(mappedBy = "hearts")
    private Set<User> hearts = new HashSet<User>();

    @ManyToMany
    //@JoinTable(name = "KweetTag",
    //        joinColumns = @JoinColumn(name="Kweet_ID", referencedColumnName = "ID", nullable = false),
    //        inverseJoinColumns = @JoinColumn(name="Hashtag_ID", referencedColumnName = "ID", nullable = false))
    private List<Hashtag> hashtags = new ArrayList<Hashtag>();

    @Temporal(TemporalType.TIMESTAMP)
    private Date publicationDate;

    public Kweet() { }

    public Kweet(long id, User sender, String message) {
        this.id = id;
        this.sender = sender;
        this.message = message;
    }

    public Kweet(long id, User sender, List<User> mentions, String message) {
        this.id = id;
        this.sender = sender;
        this.mentions = mentions;
        this.message = message;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<User> getMentions() {
        return mentions;
    }

    public void setMentions(List<User> mentions) {
        this.mentions = mentions;
    }

    public List<Hashtag> getHashtags() {
        return hashtags;
    }

    public void setHashtags(List<Hashtag> hashtags) {
        this.hashtags = hashtags;
    }

    public Set<User> getHearts() {
        return hearts;
    }

    public void setHearts(Set<User> hearts) {
        this.hearts = hearts;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }
}
