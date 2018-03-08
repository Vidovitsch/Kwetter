package DAO.Mock;

import Comparator.KweetComparator;
import DaoInterfaces.IKweetDao;
import Domain.Hashtag;
import Domain.Kweet;
import Domain.User;
import java.util.*;

public class KweetDaoMock implements IKweetDao{

    private List<Kweet> mockKweets;

    public KweetDaoMock(List<User> users) {
        this.mockKweets = createMockKweets(users);
    }

    public List<Kweet> findAll() {
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

    public List<Kweet> findByMessagePart(String message) {
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

    public Kweet insertKweet(Kweet kweet) {
        mockKweets.add(kweet);
        return kweet;
    }

    public Kweet updateKweet(Kweet kweet) {
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

    public boolean deleteKweet(Kweet kweet) {
        return mockKweets.remove(kweet);
    }

    public List<Kweet> getTimeline(User user) {
        List<Kweet> kweets = new ArrayList<Kweet>();
        for (User following : user.getFollowing()) {
            kweets.addAll(following.getKweets());
        }
        kweets.addAll(user.getKweets());
        Collections.sort(kweets, new KweetComparator());

        return kweets;
    }

    public List<Kweet> search(String term) {
        List<Kweet> results = new ArrayList<Kweet>();
        for (Kweet kweet : mockKweets) {
            if (kweet.getSender().getUsername().contains(term)) {
                results.add(kweet);
            } else {
                for (Hashtag hashtag : kweet.getHashtags()) {
                    if (hashtag.getName().contains(term)) {
                        results.add(kweet);
                        break;
                    }
                }
            }
        }
        return results;
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

                Kweet kweet = new Kweet(0, user, mentions, user.getUsername() + otherUser.getUsername());
                kweets.add(kweet);
                allKweets.add(kweet);
            }

            user.setKweets(kweets);
        }

        return allKweets;
    }
}
