package DAO;

import DaoInterfaces.IKweetDao;
import Domain.Kweet;
import Domain.User;

import java.util.*;

public class KweetDaoMock implements IKweetDao{

    Collection<Kweet> kweets;

    public KweetDaoMock(Collection<User> users) {

        this.kweets = createDummyKweets(users);
    }

    public Collection<Kweet> findAll() {
        return kweets;
    }

    public Kweet findById(long id) {
        Iterator<Kweet> kweetIterator = kweets.iterator();
        Kweet foundKweet = null;
        while(kweetIterator.hasNext()){
            Kweet currentKweet = kweetIterator.next();
            if(currentKweet.getId() == id){
                foundKweet = currentKweet;
            break;
            }
        }
        return foundKweet;
    }

    public Collection<Kweet> findByMessage(String message) {
        Iterator<Kweet> kweetIterator = kweets.iterator();
        List<Kweet> foundKweets = null;
        while(kweetIterator.hasNext()){
            Kweet currentKweet = kweetIterator.next();
            if(currentKweet.getMessage().toLowerCase().contains(message.toLowerCase())){
                foundKweets.add(currentKweet);
            }
        }
        return foundKweets;
    }

    public Collection<Kweet> findBySender(User sender) {
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
