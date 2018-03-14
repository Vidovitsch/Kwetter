package Domain;

import Util.Mockable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "User")
@Table(name = "User")
@NamedQueries({
        @NamedQuery(name = "User.findByUsername", query = "SELECT a FROM User AS a WHERE a.username = :username")
})
public class User implements Mockable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
    private Profile profile;

    @Column(name = "username", nullable = false)
    private String username;

    @ManyToMany
    @JoinTable(name = "UserRole",
            joinColumns = @JoinColumn(name="user_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name="role_id", referencedColumnName = "id", nullable = false))
    private List<Role> roles = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "Following",
            joinColumns = @JoinColumn(name="user_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name="following_id", referencedColumnName = "id", nullable = false))
    private List<User> following = new ArrayList<>();

    @ManyToMany(mappedBy = "following")
    private List<User> followers = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sender")
    private List<Kweet> kweets = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "Mention",
            joinColumns = @JoinColumn(name="user_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name="kweet_id", referencedColumnName = "id", nullable = false))
    private List<Kweet> mentions = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "KweetHeart",
            joinColumns = @JoinColumn(name="user_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name="kweet_id", referencedColumnName = "id", nullable = false))
    private List<Kweet> hearts = new ArrayList<>();

    public User() { }

    public User(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<User> getFollowing() {
        return following;
    }

    public void setFollowing(List<User> following) {
        this.following = following;
    }

    public List<User> getFollowers() {
        return followers;
    }

    public void setFollowers(List<User> followers) {
        this.followers = followers;
    }

    public List<Kweet> getKweets() {
        return kweets;
    }

    public void setKweets(List<Kweet> kweets) {
        this.kweets = kweets;
    }

    public List<Kweet> getMentions() {
        return mentions;
    }

    public void setMentions(List<Kweet> mentions) {
        this.mentions = mentions;
    }

    public List<Kweet> getHearts() {
        return hearts;
    }

    public void setHearts(List<Kweet> hearts) {
        this.hearts = hearts;
    }
}