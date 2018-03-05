package DaoInterfaces;

import Domain.Kweet;
import Domain.User;

import java.util.Collection;

public interface IKweetDao {

    Collection<Kweet> findAll();

    Kweet findById(long id);

    Collection<Kweet> findByMessage(String message);

    Collection<Kweet> findBySender(User sender);

    Kweet insertKweet(Kweet Kweet);

    Kweet updateKweet(Kweet Kweet);

    boolean deleteKweet(Kweet Kweet);

    Collection<Kweet> getTimeline(User user);

    Collection<Kweet> search(String term);
}
