package DAO.Mock;

import DaoInterfaces.IHashtagDao;
import Domain.Hashtag;
import Domain.Kweet;

import javax.swing.text.html.HTMLDocument;
import java.util.*;

public class HashtagDaoMock implements IHashtagDao {

    private Collection<Hashtag> hashtags;

    public HashtagDaoMock(Collection<Kweet> kweets) {
        this.hashtags = createDummyHashtags(kweets);
    }

    public Collection<Hashtag> findAll() {
        return hashtags;
    }

    public Hashtag findById(long id) {
        for(Hashtag h : hashtags){
            if(h.getId() == id) {
                return h;
            }
        }
        return null;
    }

    public Collection<Hashtag> findByName(String name) {
        Collection<Hashtag> foundHashtags = new ArrayList<Hashtag>();
        for(Hashtag h : hashtags){
            if(h.getName().equals(name)) {
                foundHashtags.add(h);
            }
        }
        return foundHashtags;
    }

    public Hashtag insertHashtag(Hashtag Hashtag) {
        hashtags.add(Hashtag);
        return Hashtag;
    }

    public Hashtag updateHashtag(Hashtag Hashtag) {
        for(Hashtag h : hashtags){
            if(h.getId() == Hashtag.getId()) {
                h = Hashtag;
                return h;
            }
        }
        return null;
    }

    public boolean deleteHashtag(Hashtag Hashtag) {
        try{hashtags.remove(Hashtag); return true;}catch (Exception e) {return false;}
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
