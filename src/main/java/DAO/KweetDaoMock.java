package DAO;

import DaoInterfaces.IKweetDao;
import Domain.Kweet;
import Domain.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class KweetDaoMock implements IKweetDao{

    Collection<Kweet> kweets;

    public KweetDaoMock(List<User> users) {

        this.kweets = createDummyKweets(users);
    }

    public List<Kweet> findAll() {
        return null;
    }

    public Kweet findById() {
        return null;
    }

    public List<Kweet> findByName() {
        return null;
    }

    public Kweet insertKweet(Kweet Kweet) {
        return null;
    }

    public Kweet updateKweet(Kweet Kweet) {
        return null;
    }

    public boolean deleteKweet(Kweet Kweet) {
        return false;
    }

    private ArrayList<Kweet> createDummyKweets(Collection<User> users) {
        ArrayList<Kweet> allKweets = new ArrayList<Kweet>();

        for (User user : users) {
            Collection<User> otherUsers = new HashSet<User>(users);
            otherUsers.remove(user);

            Collection<Kweet> kweets = new HashSet<Kweet>();
            for (User otherUser : otherUsers) {
                Collection<User> mentions = new HashSet<User>();
                mentions.add(otherUser);

                Kweet kweet = new Kweet(user, mentions, user.getUsername() + otherUser.getUsername());
                kweets.add(kweet);
                allKweets.add(kweet);
            }

            user.setKweets(kweets);
        }

        return allKweets;
    }

}
