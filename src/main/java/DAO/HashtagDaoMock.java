package DAO;

import DaoInterfaces.IHashtagDao;
import Domain.Hashtag;
import Domain.Kweet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class HashtagDaoMock implements IHashtagDao{
    Collection<Hashtag> hashtags;

    public HashtagDaoMock(Collection<Kweet> kweets) {
        this.hashtags = createDummyHashtags(kweets);
    }

    public List<Hashtag> findAll() {
        return null;
    }

    public Hashtag findById() {
        return null;
    }

    public List<Hashtag> findByName() {
        return null;
    }

    public Hashtag insertHashtag(Hashtag Hashtag) {
        return null;
    }

    public Hashtag updateHashtag(Hashtag Hashtag) {
        return null;
    }

    public boolean deleteHashtag(Hashtag Hashtag) {
        return false;
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
