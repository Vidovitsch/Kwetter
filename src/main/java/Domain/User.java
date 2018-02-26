package Domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.NoSuchElementException;


@Entity(name = "User")
//@Table(name = "User")
public class User {

    //region Fields
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name="Profile_Id", nullable = false)
    private User user;
    private Profile profile;
    private String username;
    private HashSet<Role> roles = new HashSet<Role>();

    private HashSet<User> following = new HashSet<User>();
    private HashSet<User> followers = new HashSet<User>();

    private HashSet<Kweet> kweets = new HashSet<Kweet>();
    private HashSet<Kweet> mentions = new HashSet<Kweet>();
    //endregion

    //region Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public HashSet<Role> getRoles() {
        return roles;
    }

    public void setRoles(HashSet<Role> roles) {
        this.roles = roles;
    }

    public HashSet<User> getFollowing() {
        return following;
    }

    public void setFollowing(HashSet<User> following) {
        this.following = following;
    }

    public HashSet<User> getFollowers() {
        return followers;
    }

    public void setFollowers(HashSet<User> followers) {
        this.followers = followers;
    }

    public HashSet<Kweet> getKweets() {
        return kweets;
    }

    public void setKweets(HashSet<Kweet> kweets) {
        this.kweets = kweets;
    }

    public HashSet<Kweet> getMentions() {
        return mentions;
    }

    public void setMentions(HashSet<Kweet> mentions) {
        this.mentions = mentions;
    }
    //endregion=
}