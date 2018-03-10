package DAO.Mock;

import DaoInterfaces.IHashtagDao;
import Domain.*;
import Util.MockFactory;

import java.util.*;

public class HashtagDaoMock implements IHashtagDao {

    private List<Hashtag> mockHashtags;

    @SuppressWarnings("unchecked")
    public HashtagDaoMock(List<Kweet> kweets) {
        mockHashtags = (List<Hashtag>) MockFactory.createMocks(Hashtag.class, 2);
        MockFactory.setNewIds(mockHashtags);

        createDummyHashtags(mockHashtags, kweets);
    }

    public List<Hashtag> findAll() {
        return mockHashtags;
    }

    public Hashtag findById(Long id) {
        for(Hashtag h : mockHashtags){
            if(h.getId().equals(id)) {
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

    public Hashtag create(Hashtag hashtag) {
        MockFactory.setNextId(hashtag, mockHashtags);
        mockHashtags.add(hashtag);
        return hashtag;
    }

    public List<Hashtag> create(List<Hashtag> hashtags) {
        MockFactory.setNextIds(hashtags, mockHashtags);
        mockHashtags.addAll(hashtags);
        return hashtags;
    }

    public Hashtag update(Hashtag hashtag) {
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

    public boolean remove(Hashtag hashtag) {
        return mockHashtags.remove(hashtag);
    }

    // For mock purposes
    private void createDummyHashtags(List<Hashtag> hashtags, List<Kweet> kweets) {
        for (Kweet kweet : kweets) {
            kweet.setHashtags(hashtags);
        }
        for (Hashtag hashtag : hashtags) {
            hashtag.setKweets(kweets);
        }
    }
}
