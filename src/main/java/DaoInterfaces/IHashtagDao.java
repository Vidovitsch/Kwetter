package DaoInterfaces;

import Domain.Hashtag;
import java.util.List;

public interface IHashtagDao {

    List<Hashtag> findAll();

    Hashtag findById(long id);

    Hashtag findByName(String name);

    Hashtag insertHashtag(Hashtag Hashtag);

    Hashtag updateHashtag(Hashtag Hashtag);

    boolean deleteHashtag(Hashtag Hashtag);

    List<Hashtag> getTrend();
}
