package DAO.Mock;

import DaoInterfaces.IKweetDao;
import Domain.Kweet;
import Domain.User;

import java.util.*;

public class KweetDaoMock implements IKweetDao{

    private Collection<Kweet> mockKweets;

    public KweetDaoMock(Collection<User> users) {

        this.mockKweets = createDummyKweets(users);
    }

    public Collection<Kweet> findAll() {
        return mockKweets;
    }

    public Kweet findById(long id) {
        for(Kweet k : mockKweets){
            if(k.getId() == id){
                return k;
            }
        }
        return null;
    }

    public Collection<Kweet> findByMessage(String message) {
        Collection<Kweet> foundKweets = new ArrayList<Kweet>();
        for(Kweet k : mockKweets){
            if (k.getMessage().toLowerCase().contains(message.toLowerCase())){
                foundKweets.add(k);
            }
        }
        return foundKweets;
    }

    public Collection<Kweet> findBySender(User sender) {
        Collection<Kweet> foundKweets = new ArrayList<Kweet>();
        for(Kweet k : mockKweets){
            if(k.getSender() == sender){
                foundKweets.add(k);
            }
        }
        return foundKweets;
    }

    public Kweet insertKweet(Kweet kweet) {
        mockKweets.add(kweet);
        return kweet;
    }

    public Kweet updateKweet(Kweet kweet) {
        Kweet existingKweet = findById(kweet.getId());
        if(existingKweet == null){
            kweets.add(kweet);
        }
        else{
            kweets.remove(existingKweet);
            kweets.add(kweet);
        }
        return kweet;
    }

    public boolean deleteKweet(Kweet kweet) {
        return mockKweets.remove(kweet);
    }

    public Collection<Kweet> getTimeline(User user) {
        return null;
    }

    public Collection<Kweet> search(String term) {
        return null;
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
