package Domain;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

@Entity(name = "Kweet")
//@Table(name = "Kweet")
public class Kweet {

    // region Fields

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //@Column(name = "ID")
    private long id;

    @ManyToOne
    //@JoinColumn(name = "User_ID", nullable = false)
    private User sender;

    @ManyToMany(mappedBy = "mentions")
    private Collection<User> mentions = new HashSet<User>();

    @ManyToMany
    //@JoinTable(name = "KweetTag",
    //        joinColumns = @JoinColumn(name="Kweet_ID", referencedColumnName = "ID", nullable = false),
    //        inverseJoinColumns = @JoinColumn(name="Hashtag_ID", referencedColumnName = "ID", nullable = false))
    private Collection<Hashtag> hashtags = new HashSet<Hashtag>();

    //@Column(name = "Hearts")
    private long hearts;

    @Temporal(TemporalType.TIMESTAMP)
    //@Column(name = "PublicationDate")
    private Date publicationDate;

    // endregion

    public Kweet(User sender) {
        this.sender = sender;
    }

    public Kweet(User sender, Collection<User> mentions) {
        this.sender = sender;
        this.mentions = mentions;
    }

    // region Fields

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

    public Collection<User> getMentions() {
        return mentions;
    }

    public void setMentions(HashSet<User> mentions) {
        this.mentions = mentions;
    }

    public Collection<Hashtag> getHashtags() {
        return hashtags;
    }

    public void setHashtags(HashSet<Hashtag> hashtags) {
        this.hashtags = hashtags;
    }

    public long getHearts() {
        return hearts;
    }

    public void setHearts(long hearts) {
        this.hearts = hearts;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    // endregion
}
