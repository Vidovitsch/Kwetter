package DAO.Mock;

import DaoInterfaces.IKweetDao;
import Domain.Hashtag;
import Domain.Kweet;
import Domain.User;

import java.util.*;

public class KweetDaoMock implements IKweetDao{

    private Collection<Kweet> mockKweets;

    public KweetDaoMock(Collection<User> users) {

        this.mockKweets = createMockKweets(users);
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

    public Collection<Kweet> findBySenderName(String senderName) {
        Collection<Kweet> foundKweets = new ArrayList<Kweet>();
        for(Kweet k : mockKweets){
            if(k.getSender().getUsername() == senderName){
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

    public Collection<Kweet> getTimeline(User user) {
        List<Kweet> kweets = new ArrayList<Kweet>();
        for (User following : user.getFollowing()) {
            kweets.addAll(following.getKweets());
        }
        kweets.addAll(user.getKweets());
        Collections.sort(kweets, new Comparator.KweetComparator());

        return kweets;
    }

    public Collection<Kweet> search(String term) {
        Collection<Kweet> results = new ArrayList<Kweet>();
        term = term.toLowerCase();
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

    private ArrayList<Kweet> createMockKweets(Collection<User> users) {
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
