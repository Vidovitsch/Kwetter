package DaoInterfaces;

import Domain.Hashtag;
import java.util.List;

public interface IHashtagDao {

    List<Hashtag> findAll();

    Hashtag findById(Long id);

    Hashtag findByName(String name);

    Hashtag create(Hashtag hashtag);

    List<Hashtag> create(List<Hashtag> hashtags);

    Hashtag update(Hashtag hashtag);

    boolean remove(Hashtag hashtag);
}
