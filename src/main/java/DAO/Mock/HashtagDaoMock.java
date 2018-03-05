package DAO.Mock;

import Comparator.TrendComparator;
import DaoInterfaces.IHashtagDao;
import Domain.Hashtag;
import Domain.Kweet;

import java.util.*;

public class HashtagDaoMock implements IHashtagDao {

    private Collection<Hashtag> mockHashtags;

    public HashtagDaoMock(Collection<Kweet> kweets) {
        this.mockHashtags = createMockHashtags(kweets);
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

    public Hashtag findByName(String name) {
        for (Hashtag hashtag : mockHashtags) {
            if (hashtag.getName().equals(name)) {
                return hashtag;
            }
        }
        return null;
    }

    public Hashtag insertHashtag(Hashtag hashtag) {
        mockHashtags.add(hashtag);
        return hashtag;
    }

    public Hashtag updateHashtag(Hashtag hashtag) {
        Hashtag existingHashtag = findById(hashtag.getId());
        if(existingHashtag == null){
            mockHashtags.add(hashtag);
        }
        else{
            mockHashtags.remove(existingHashtag);
            mockHashtags.add(hashtag);
        }
        return hashtag;
    }

    public boolean deleteHashtag(Hashtag hashtag) {
        return mockHashtags.remove(hashtag);
    }

    public List<Hashtag> getTrend() {
        List<Hashtag> trends = new ArrayList<Hashtag>();
        Date weekAgo = getDateWeekAgo();
        for (Hashtag hashtag : mockHashtags) {
            if (hashtag.getLastUsed().after(weekAgo)) {
                trends.add(hashtag);
            }
        }
        Collections.sort(trends, new TrendComparator());
        return trends;
    }

    private Date getDateWeekAgo() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, -30);

        return cal.getTime();
    }

    private ArrayList<Hashtag> createMockHashtags(Collection<Kweet> kweets) {
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
