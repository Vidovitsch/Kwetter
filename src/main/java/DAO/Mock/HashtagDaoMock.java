package DAO.Mock;

import DaoInterfaces.IHashtagDao;
import Domain.Hashtag;
import Domain.Kweet;

import java.util.*;

public class HashtagDaoMock implements IHashtagDao {

    private Collection<Hashtag> mockHashtags;

    public HashtagDaoMock(Collection<Kweet> kweets) {
        this.mockHashtags = createDummyHashtags(kweets);
    }

    public Collection<Hashtag> findAll() {
        return mockHashtags;
    }

    public Hashtag findById(long id) {
        for(Hashtag h : mockHashtags){
            if(h.getId() == id) {
                return h;
            }
        }
        return null;
    }

    public Collection<Hashtag> findByName(String name) {
        Collection<Hashtag> foundHashtags = new ArrayList<Hashtag>();
        for(Hashtag h : mockHashtags){
            if(h.getName().equals(name)) {
                foundHashtags.add(h);
            }
        }
        return foundHashtags;
    }

    public Hashtag insertHashtag(Hashtag Hashtag) {
        mockHashtags.add(Hashtag);
        return Hashtag;
    }

    public Hashtag updateHashtag(Hashtag hashtag) {
        Hashtag existingHashtag = findById(hashtag.getId());
        if(existingHashtag == null){
            hashtags.add(hashtag);
        }
        else{
            hashtags.remove(existingHashtag);
            hashtags.add(hashtag);
        }
        return hashtag;
    }

    public boolean deleteHashtag(Hashtag hashtag) {
        return mockHashtags.remove(hashtag);
    }

    public Collection<Hashtag> getTrend() {
        return null;
    }

    private ArrayList<Hashtag> createDummyHashtags(Collection<Kweet> kweets) {
        ArrayList<Hashtag> hashtags = new ArrayList<Hashtag>();

        Hashtag hashtag1 = new Hashtag("Test");
        Hashtag hashtag2 = new Hashtag("Kwetter");
        hashtags.add(hashtag1);
        hashtags.add(hashtag2);

        for (Kweet kweet : kweets) {
            kweet.setHashtags(hashtags);
        }
        hashtag1.setKweets(kweets);
        hashtag2.setKweets(kweets);

        return hashtags;
    }
}
