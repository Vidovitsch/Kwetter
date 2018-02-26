package Domain;

import java.util.HashSet;
import java.util.NoSuchElementException;

public class User {

    // region Fields
    private int id;

    private Profile profile;
    private String username;
    private HashSet<Role> roles = new HashSet<Role>();

    private HashSet<User> following = new HashSet<User>();
    private HashSet<User> followers = new HashSet<User>();

    private HashSet<Kweet> kweets = new HashSet<Kweet>();
    private HashSet<Kweet> mentions = new HashSet<Kweet>();
    //endregion

    public User(String username) {
        this.username = username;
    }

    //region Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
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
    //endregion

    /**
     * Creates profile for this user.
     * If the user already has a profile, the current profile will be updated.
     *
     * @param profileName publicly visible name of the profile. This name consist of max. 50 characters.
     *                    If the max. gets exceeded an IllegalArgumentException will be thrown.
     * @return created profile
     */
    public Profile createProfile(String profileName) {
        // To Do
        return null;
    }

    /**
     * Creates a new kweet with this user as a sender.
     *
     * @param message the message of the kweet with max. 140 characters.
     *                If the max. gets exceeded an IllegalArgumentException will be thrown.
     * @return created kweet
     */
    public Kweet createKweet(String message) throws IllegalArgumentException {
        return createKweet(message, new HashSet<String>());
    }

    /**
     * Creates a new kweet with this user as a sender.
     *
     * @param message the message of the kweet with max. 140 characters.
     *                If the max. gets exceeded an IllegalArgumentException will be thrown.
     * @param mentions the mentions of this kweet (@{username}).
     *                 The users that get mentioned in the kweet will receive this kweet in their 'mention-view'.
     *
     * @return created kweet
     */
    public Kweet createKweet(String message, HashSet<String> mentions)  throws IllegalArgumentException {
        // To Do
        return null;
    }

    /**
     * Removes an existing kweet sent by this user.
     * If the kweet does not exist, a NoSuchElementException will be thrown.
     *
     * @param kweet the kweet that will be removed
     */
    public void removeKweet(Kweet kweet) throws NoSuchElementException {
        // To Do
        kweets.remove(kweet);
    }

    /**
     * Adds a new user that this user will follow.
     * If this user already follows the 'following' user, an IllegalArgumentException will be thrown.
     *
     * @param following the user that this user will follow
     */
    public void addFollowing(User following) throws IllegalArgumentException {
        // To Do
    }

    /**
     * Removes an existing user that this user follows.
     * If the 'following' user does not exist, a NoSuchElementException will be thrown
     *
     * @param following the user that this user will follow
     */
    public void removeFollowing(User following) throws NoSuchElementException {
        // To Do
    }
}