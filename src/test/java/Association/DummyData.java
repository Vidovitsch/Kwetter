package Association;

import Domain.*;
import java.util.ArrayList;
import java.util.List;

public class DummyData {

    private ArrayList<User> dummyUsers;
    private ArrayList<Kweet> dummyKweets;
    private ArrayList<Role> dummyRoles;
    private ArrayList<Hashtag> dummyHashtags;
    private ArrayList<Profile> dummyProfiles;

    public DummyData() {
        buildScenario();
    }

    public ArrayList<User> getDummyUsers() {
        return dummyUsers;
    }

    public ArrayList<Kweet> getDummyKweets() {
        return dummyKweets;
    }

    public ArrayList<Role> getDummyRoles() {
        return dummyRoles;
    }

    public ArrayList<Hashtag> getDummyHashtags() {
        return dummyHashtags;
    }

    public ArrayList<Profile> getDummyProfiles() {
        return dummyProfiles;
    }

    public void reset() {
        buildScenario();
    }

    /**
     * Scenario for tests:
     *
     * 1. Association: User - Kweet
     *
     * 1.1. 10 unique users will use Kwetter.
     * 1.2. One user follows the 9 other users. This results in 9 following and 9 followers for each user.
     * 1.3. Each user sends 9 unique kweets. In each kweet one of the 9 other users gets mentioned.
     *      This results in 9 mentions for each user.
     *
     * 2. Association: User - Role
     *
     * 2.1. Each user has the 'Kweeter' and 'Moderator' role. This results in a list of 10 users for both
     *      the 'Kweeter' and 'Moderator' role.
     *
     * 3. Association: Kweet - Hashtag
     *
     * 3.1. Each Kweet contains the hashtag '#Test' and #'Kwetter'. In this scenario the hashtag will be added
     *      manually. In a real life scenario, hashtags will be fetched from the message of the kweet.
     *
     * 4. Association: User - Profile
     *
     * 4.1. Each user has one unique profile.
     */
    private void buildScenario() {
        this.dummyUsers = createDummyUsers();
        this.dummyKweets = createDummyKweets(this.dummyUsers);
        this.dummyRoles = createDummyRoles(this.dummyUsers);
        this.dummyHashtags = createDummyHashtags(this.dummyKweets);
        this.dummyProfiles = createDummyProfiles(this.dummyUsers);

        connectDummyUsers(this.dummyUsers);
    }

    private ArrayList<User> createDummyUsers() {
        ArrayList<User> users = new ArrayList<User>();
        for (int i = 0; i < 10; i++) {
            users.add(new User(i, "DummyUser" + i));
        }

        return users;
    }

    private void connectDummyUsers(List<User> users) {
        for (User dummyUser : users) {
            List<User> others = new ArrayList<User>(users);
            others.remove(dummyUser);

            dummyUser.setFollowers(others);
            dummyUser.setFollowing(others);
        }
    }

    private ArrayList<Kweet> createDummyKweets(List<User> users) {
        ArrayList<Kweet> allKweets = new ArrayList<Kweet>();

        for (User user : users) {
            List<User> otherUsers = new ArrayList<User>(users);
            otherUsers.remove(user);

            List<Kweet> kweets = new ArrayList<Kweet>();
            for (User otherUser : otherUsers) {
                List<User> mentions = new ArrayList<User>();
                mentions.add(otherUser);

                Kweet kweet = new Kweet(-1, user, mentions, user.getUsername() + otherUser.getUsername());
                kweets.add(kweet);
                allKweets.add(kweet);
            }

            user.setKweets(kweets);
        }

        return allKweets;
    }

    private ArrayList<Role> createDummyRoles(List<User> users) {
        ArrayList<Role> roles = new ArrayList<Role>();

        Role role1 = new Role(-1,"Kweeter");
        Role role2 = new Role(-1,"Moderator");
        roles.add(role1);
        roles.add(role2);

        for (User user : users) {
            user.setRoles(roles);
        }
        role1.setUsers(users);
        role2.setUsers(users);

        return roles;
    }

    private ArrayList<Hashtag> createDummyHashtags(List<Kweet> kweets) {
        ArrayList<Hashtag> hashtags = new ArrayList<Hashtag>();

        Hashtag hashtag1 = new Hashtag(-1,"Test");
        Hashtag hashtag2 = new Hashtag(-1,"Kwetter");
        hashtags.add(hashtag1);
        hashtags.add(hashtag2);

        for (Kweet kweet : kweets) {
            kweet.setHashtags(hashtags);
        }
        hashtag1.setKweets(kweets);
        hashtag2.setKweets(kweets);

        return hashtags;
    }

    private ArrayList<Profile> createDummyProfiles(List<User> users) {
        ArrayList<Profile> profiles = new ArrayList<Profile>();

        for (User user : users) {
            Profile dummyProfile = new Profile(-1,user.getUsername() + " Test");
            dummyProfile.setUser(user);
            user.setProfile(dummyProfile);
            profiles.add(dummyProfile);
        }

        return profiles;
    }
}
