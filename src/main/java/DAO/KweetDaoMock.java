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
        for(Kweet k : kweets){
            if(k.getId() == id){
                return k;
            }
        }
        return null;
    }

    public Collection<Kweet> findByMessage(String message) {
        Collection<Kweet> foundKweets = new ArrayList<Kweet>();
        for(Kweet k : kweets){
            if(k.getMessage().toLowerCase().contains(message.toLowerCase())){
                foundKweets.add(k);
            }
        }
        return foundKweets;
    }

    public Collection<Kweet> findBySender(User sender) {
        Collection<Kweet> foundKweets = new ArrayList<Kweet>();
        for(Kweet k : kweets){
            if(k.getSender() == sender){
                foundKweets.add(k);
            }
        }
        return foundKweets;
    }

    public Kweet insertKweet(Kweet kweet) {
        kweets.add(kweet);
        return kweet;
    }

    public Kweet updateKweet(Kweet kweet) {
        for(Kweet k : kweets){
            if(k.getId() == kweet.getId()){
                k = kweet;
                return k;
            }
        }
        return insertKweet(kweet);
    }

    public boolean deleteKweet(Kweet kweet) {
        try{kweets.remove(kweet); return true;}catch (Exception e) {return false;}
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
