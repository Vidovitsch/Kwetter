package DaoInterfaces;

import Domain.Hashtag;
import java.util.List;

public interface IHashtagDao {

    List<Hashtag> findAll();

    Hashtag findById(Long id);

    Hashtag findByName(String name);

    Hashtag create(Hashtag Hashtag);

    Hashtag update(Hashtag Hashtag);

    boolean remove(Hashtag Hashtag);
}
