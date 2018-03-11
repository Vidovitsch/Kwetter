package DAO.Mock;

import DaoInterfaces.IHashtagDao;
import Domain.*;
import Qualifier.Mock;
import Util.MockFactory;
import Util.MockService;

import javax.enterprise.context.Dependent;
import java.util.*;

@Mock
@Dependent
public class HashtagDaoMock implements IHashtagDao {

    private List<Hashtag> mockHashtags;

    public HashtagDaoMock() {
        mockHashtags = MockService.getInstance().getHashtags();
    }

    @Override
    public List<Hashtag> findAll() {
        return mockHashtags;
    }

    @Override
    public Hashtag findById(Long id) {
        for(Hashtag h : mockHashtags) {
            if(h.getId().equals(id)) {
                return h;
            }
        }
        return null;
    }

    @Override
    public Hashtag findByName(String name) {
        for (Hashtag hashtag : mockHashtags) {
            if (hashtag.getName().equals(name)) {
                return hashtag;
            }
        }
        return null;
    }

    @Override
    public Hashtag create(Hashtag hashtag) {
        MockFactory.setNextId(hashtag, mockHashtags);
        mockHashtags.add(hashtag);

        return hashtag;
    }

    @Override
    public List<Hashtag> create(List<Hashtag> hashtags) {
        MockFactory.setNextIds(hashtags, mockHashtags);
        mockHashtags.addAll(hashtags);
        return hashtags;
    }

    @Override
    public Hashtag update(Hashtag hashtag) {
        Hashtag existingHashtag = findById(hashtag.getId());
        if(existingHashtag == null) {
            mockHashtags.add(hashtag);
        }
        else{
            mockHashtags.remove(existingHashtag);
            mockHashtags.add(hashtag);
        }
        return hashtag;
    }

    @Override
    public boolean remove(Hashtag hashtag) {
        return mockHashtags.remove(hashtag);
    }
}
