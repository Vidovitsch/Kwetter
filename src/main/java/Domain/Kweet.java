package Domain;

import Util.Mockable;

import javax.persistence.*;
import java.util.*;

@Entity(name = "Kweet")
@Table(name = "Kweet")
public class Kweet implements Mockable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "message", nullable = false)
    private String message;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User sender;

    @ManyToMany(mappedBy = "mentions")
    private List<User> mentions = new ArrayList<>();

    @ManyToMany(mappedBy = "hearts")
    private Set<User> hearts = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "KweetTag",
            joinColumns = @JoinColumn(name="kweet_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name="hashtag_id", referencedColumnName = "id", nullable = false))
    private List<Hashtag> hashtags = new ArrayList<>();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "publicationDate")
    private Date publicationDate;

    public Kweet() {
        this.publicationDate = new Date();
    }

    public Kweet(Long id, User sender, String message) {
        this.id = id;
        this.sender = sender;
        this.message = message;
        this.publicationDate = new Date();
    }

    public Kweet(Long id, User sender, List<User> mentions, String message) {
        this.id = id;
        this.sender = sender;
        this.mentions = mentions;
        this.message = message;
        this.publicationDate = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
