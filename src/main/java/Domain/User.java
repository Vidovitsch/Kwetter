package Domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "User")
public class User implements Mockable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
    private Profile profile;

    @Column(nullable = false)
    private String username;

    @ManyToMany
    //@JoinTable(name = "UserRole",
    //        joinColumns = @JoinColumn(name="User_ID", referencedColumnName = "ID", nullable = false),
    //        inverseJoinColumns = @JoinColumn(name="Role_ID", referencedColumnName = "ID", nullable = false))
    private List<Role> roles = new ArrayList<Role>();

    @ManyToMany
    //@JoinTable(name = "Following",
    //        joinColumns = @JoinColumn(name="User_ID", referencedColumnName = "ID", nullable = false),
    //        inverseJoinColumns = @JoinColumn(name="Following_ID", referencedColumnName = "ID", nullable = false))
    private List<User> following = new ArrayList<User>();

    @ManyToMany(mappedBy = "following")
    private List<User> followers = new ArrayList<User>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sender")
    private List<Kweet> kweets = new ArrayList<Kweet>();

    @ManyToMany
    //@JoinTable(name = "Mention",
    //        joinColumns = @JoinColumn(name="User_ID", referencedColumnName = "ID", nullable = false),
    //        inverseJoinColumns = @JoinColumn(name="Kweet_ID", referencedColumnName = "ID", nullable = false))
    private List<Kweet> mentions = new ArrayList<Kweet>();

    @ManyToMany(mappedBy = "hearts")
    private List<Kweet> hearts = new ArrayList<Kweet>();

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