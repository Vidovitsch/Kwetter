package DaoInterfaces;

import Domain.Hashtag;

import java.util.Collection;

public interface IHashtagDao {
    Collection<Hashtag> findAll();
    Hashtag findById(long id);
    Collection<Hashtag> findByName(String name);
    Hashtag insertHashtag(Hashtag Hashtag);
    Hashtag updateHashtag(Hashtag Hashtag);
    boolean deleteHashtag(Hashtag Hashtag);
}
