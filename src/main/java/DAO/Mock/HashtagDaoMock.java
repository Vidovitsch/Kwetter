package DAO.Mock;

import DaoInterfaces.IHashtagDao;
import Domain.Hashtag;
import Domain.Kweet;

import javax.swing.text.html.HTMLDocument;
import java.util.*;

public class HashtagDaoMock implements IHashtagDao{
    Collection<Hashtag> hashtags;

    public HashtagDaoMock(Collection<Kweet> kweets) {
        this.hashtags = createDummyHashtags(kweets);
    }

    public Collection<Hashtag> findAll() {
        return hashtags;
    }

    public Hashtag findById(long id) {
        Hashtag foundHashtag = null;
        Iterator<Hashtag> hashtagIterator = hashtags.iterator();
        while (hashtagIterator.hasNext()){
            Hashtag currentHashtag = hashtagIterator.next();
            if(currentHashtag.getId() == id) {
                foundHashtag = currentHashtag;
                break;
            }
        }
        return foundHashtag;
    }

    public Collection<Hashtag> findByName(String name) {
        Collection<Hashtag> foundHashtags = null;
        Iterator<Hashtag> hashtagIterator = hashtags.iterator();
        while (hashtagIterator.hasNext()){
            Hashtag currentHashtag = hashtagIterator.next();
            if(currentHashtag.getName() == name) {
                foundHashtags.add(currentHashtag);
            }
        }
        return foundHashtags;
    }

    public Hashtag insertHashtag(Hashtag Hashtag) {
        hashtags.add(Hashtag);
        return Hashtag;
    }

    public Hashtag updateHashtag(Hashtag Hashtag) {
        Hashtag updatedHashtag = null;
        Iterator<Hashtag> hashtagIterator = hashtags.iterator();
        while (hashtagIterator.hasNext()){
            Hashtag currentHashtag = hashtagIterator.next();
            if(currentHashtag.getId() == Hashtag.getId()) {
                currentHashtag = Hashtag;
                updatedHashtag = currentHashtag;
                break;
            }
        }
        return updatedHashtag;
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
