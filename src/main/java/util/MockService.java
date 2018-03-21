package util;

import domain.*;

import java.util.ArrayList;
import java.util.List;

public class MockService {

    private List<Kweet> kweets = null;
    private List<User> users = null;
    private List<Role> roles = null;
    private List<Profile> profiles = null;
    private List<Hashtag> hashtags = null;

    private static MockService instance = null;

    public static MockService getInstance() {
        if (instance == null) {
            instance = new MockService();
            setMockData(instance);
        }
        return instance;
    }

    public List<Kweet> getKweets() {
        return kweets;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public List<Profile> getProfiles() {
        return profiles;
    }

    public List<Hashtag> getHashtags() {
        return hashtags;
    }

    public static void renewMockData() {
        setMockData(MockService.getInstance());
    }

    // Don't change order!!!
    private static void setMockData(MockService instance) {
        instance.setMockUsers();
        instance.setMockKweets();
        instance.setMockProfiles();
        instance.setMockRoles();
        instance.setMockHashtags();
    }

    @SuppressWarnings("unchecked")
    private void setMockHashtags() {
        this.hashtags = toHashtags(MockFactory.createMocks(Hashtag.class, 2));
        MockFactory.setNewIds(hashtags);

        createDummyHashtags();
    }

    @SuppressWarnings("unchecked")
    private void setMockKweets() {
        this.kweets = toKweets(MockFactory.createMocks(Kweet.class, 90));
        MockFactory.setNewIds(kweets);

        createDummyKweets();
    }

    @SuppressWarnings("unchecked")
    private void setMockProfiles() {
        this.profiles = toProfiles(MockFactory.createMocks(Profile.class, 10));
        MockFactory.setNewIds(profiles);

        connectDummyProfiles();
    }

    @SuppressWarnings("unchecked")
    private void setMockRoles() {
        this.roles = toRoles(MockFactory.createMocks(Role.class, 10));
        MockFactory.setNewIds(roles);

        connectDummyRoles();
    }

    @SuppressWarnings("unchecked")
    private void setMockUsers() {
        this.users = toUsers(MockFactory.createMocks(User.class, 10));
        MockFactory.setNewIds(users);

        connectDummyUsers();
    }

    private void createDummyHashtags() {
        for (Kweet kweet : kweets) {
            kweet.setHashtags(hashtags);
        }
        for (Hashtag hashtag : hashtags) {
            hashtag.setKweets(kweets);
        }
    }

    private void createDummyKweets() {
        for (int i = 0; i < users.size(); i++) {
            List<Kweet> kweetsToSend = kweets.subList(i * 9, i * 9 + 9);
            List<User> mentions = new ArrayList<>(users);
            mentions.remove(users.get(i));
            users.get(i).setKweets(kweetsToSend);
            for (Kweet kweet : kweetsToSend) {
                kweet.setSender(users.get(i));
                kweet.setMentions(mentions);
            }
        }
    }

    private void connectDummyProfiles() {
        if (users.size() >= profiles.size()) {
            for (int i = 0; i < profiles.size(); i++) {
                profiles.get(i).setUser(users.get(i));
            }
        }
    }

    private void connectDummyRoles() {
        for (User user : users) {
            user.setRoles(roles);
        }
        for (Role role : roles) {
            role.setUsers(users);
        }
    }

    private void connectDummyUsers() {
        for (User dummyUser : users) {
            List<User> others = new ArrayList<>(users);
            others.remove(dummyUser);

            dummyUser.setFollowers(others);
            dummyUser.setFollowing(others);
        }
    }

    public static List<User> toUsers(List<Mockable> mockables) {
        List<User> users = new ArrayList<>();
        for (Mockable mockable : mockables) {
            users.add((User) mockable);
        }
        return users;
    }

    public static List<Profile> toProfiles(List<Mockable> mockables) {
        List<Profile> profiles = new ArrayList<>();
        for (Mockable mockable : mockables) {
            profiles.add((Profile) mockable);
        }
        return profiles;
    }

    public static List<Hashtag> toHashtags(List<Mockable> mockables) {
        List<Hashtag> hashtags = new ArrayList<>();
        for (Mockable mockable : mockables) {
            hashtags.add((Hashtag) mockable);
        }
        return hashtags;
    }

    public static List<Role> toRoles(List<Mockable> mockables) {
        List<Role> roles = new ArrayList<>();
        for (Mockable mockable : mockables) {
            roles.add((Role) mockable);
        }
        return roles;
    }

    public static List<Kweet> toKweets(List<Mockable> mockables) {
        List<Kweet> kweets = new ArrayList<>();
        for (Mockable mockable : mockables) {
            kweets.add((Kweet) mockable);
        }
        return kweets;
    }
}
