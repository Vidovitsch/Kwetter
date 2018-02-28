package Domain;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;

@Entity(name = "User")
//@Table(name = "User")
public class User {

    // region Fields

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //@Column(name = "ID")
    private long id;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
    private Profile profile;

    //@Column(name = "Username", nullable = false)
    private String username;

    @ManyToMany
    //@JoinTable(name = "UserRole",
    //        joinColumns = @JoinColumn(name="User_ID", referencedColumnName = "ID", nullable = false),
    //        inverseJoinColumns = @JoinColumn(name="Role_ID", referencedColumnName = "ID", nullable = false))
    private Collection<Role> roles = new HashSet<Role>();

    @ManyToMany
    //@JoinTable(name = "Following",
    //        joinColumns = @JoinColumn(name="User_ID", referencedColumnName = "ID", nullable = false),
    //        inverseJoinColumns = @JoinColumn(name="Following_ID", referencedColumnName = "ID", nullable = false))
    private Collection<User> following = new HashSet<User>();

    @ManyToMany(mappedBy = "following")
    private Collection<User> followers = new HashSet<User>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sender")
    private Collection<Kweet> kweets = new HashSet<Kweet>();

    @ManyToMany
    //@JoinTable(name = "Mention",
    //        joinColumns = @JoinColumn(name="User_ID", referencedColumnName = "ID", nullable = false),
    //        inverseJoinColumns = @JoinColumn(name="Kweet_ID", referencedColumnName = "ID", nullable = false))
    private Collection<Kweet> mentions = new HashSet<Kweet>();

    // endregion

    public User() { }

    public User(String username) {
        this.username = username;
    }

    // region Getters and Setters

    public long getId() {
        return id;
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

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    public Collection<User> getFollowing() {
        return following;
    }

    public void setFollowing(Collection<User> following) {
        this.following = following;
    }

    public Collection<User> getFollowers() {
        return followers;
    }

    public void setFollowers(Collection<User> followers) {
        this.followers = followers;
    }

    public Collection<Kweet> getKweets() {
        return kweets;
    }

    public void setKweets(Collection<Kweet> kweets) {
        this.kweets = kweets;
    }

    public Collection<Kweet> getMentions() {
        return mentions;
    }

    // endregion
}