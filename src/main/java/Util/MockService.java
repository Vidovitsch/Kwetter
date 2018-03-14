package Util;

import Domain.*;

import java.util.ArrayList;
import java.util.HashSet;
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

    public static void resetMockData() {
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
        hashtags = (List<Hashtag>) MockFactory.createMocks(Hashtag.class, 2);
        MockFactory.setNewIds(hashtags);

        createDummyHashtags();
    }

    @SuppressWarnings("unchecked")
    private void setMockKweets() {
        kweets = (List<Kweet>) MockFactory.createMocks(Kweet.class, 90);
        MockFactory.setNewIds(kweets);

        createDummyKweets();
    }

    @SuppressWarnings("unchecked")
    private void setMockProfiles() {
        profiles = (List<Profile>) MockFactory.createMocks(Profile.class, 10);
        MockFactory.setNewIds(profiles);

        connectDummyProfiles();
    }

    @SuppressWarnings("unchecked")
    private void setMockRoles() {
        roles = (List<Role>)MockFactory.createMocks(Role.class, 10);
        MockFactory.setNewIds(roles);

        connectDummyRoles();
    }

    @SuppressWarnings("unchecked")
    private void setMockUsers() {
        users = (List<User>)MockFactory.createMocks(User.class, 10);
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
                users.get(i).setProfile(profiles.get(i));
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
}
