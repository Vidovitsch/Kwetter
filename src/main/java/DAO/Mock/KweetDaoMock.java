package DAO.Mock;

import Comparator.KweetComparator;
import DaoInterfaces.IKweetDao;
import Domain.Hashtag;
import Domain.Kweet;
import Domain.MockFactory;
import Domain.User;
import java.util.*;

public class KweetDaoMock implements IKweetDao {

    private List<Kweet> mockKweets;

    public KweetDaoMock(List<User> users) {
        this.mockKweets = createMockKweets(users);
    }

    public List<Kweet> findAll() {
        return mockKweets;
    }

    public Kweet findById(Long id) {
        for(Kweet k : mockKweets){
            if(k.getId().equals(id)){
                return k;
            }
        }
        return null;
    }

    public List<Kweet> findByTerm(String message) {
        List<Kweet> foundKweets = new ArrayList<Kweet>();
        for(Kweet k : mockKweets){
            if (k.getMessage().toLowerCase().contains(message.toLowerCase())){
                foundKweets.add(k);
            }
        }
        return foundKweets;
    }

    public List<Kweet> findBySenderName(String senderName) {
        List<Kweet> foundKweets = new ArrayList<Kweet>();
        for(Kweet k : mockKweets){
            if(k.getSender().getUsername().equals(senderName)){
                foundKweets.add(k);
            }
        }
        return foundKweets;
    }

    public Kweet create(Kweet kweet) {
        MockFactory.setNextId(kweet, mockKweets);
        mockKweets.add(kweet);
        return kweet;
    }

    public Kweet update(Kweet kweet) {
        Kweet existingKweet = findById(kweet.getId());
        if(existingKweet == null){
            mockKweets.add(kweet);
        }
        else{
            mockKweets.remove(existingKweet);
            mockKweets.add(kweet);
        }
        return kweet;
    }

    public boolean remove(Kweet kweet) {
        return mockKweets.remove(kweet);
    }

    private ArrayList<Kweet> createMockKweets(List<User> users) {
        ArrayList<Kweet> allKweets = new ArrayList<Kweet>();

        for (User user : users) {
            List<User> otherUsers = new ArrayList<User>(users);
            otherUsers.remove(user);

            List<Kweet> kweets = new ArrayList<Kweet>();
            for (User otherUser : otherUsers) {
                List<User> mentions = new ArrayList<User>();
                mentions.add(otherUser);

                Kweet kweet = new Kweet((long)0, user, mentions, user.getUsername() + otherUser.getUsername());
                kweets.add(kweet);
                allKweets.add(kweet);
            }

            user.setKweets(kweets);
        }

        return allKweets;
    }
}
