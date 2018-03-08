package DaoInterfaces;

import Domain.Kweet;
import Domain.User;
import java.util.List;

public interface IKweetDao {

    List<Kweet> findAll();

    Kweet findById(long id);

    List<Kweet> findByMessagePart(String message);

    List<Kweet> findBySenderName(String senderName);

    Kweet insertKweet(Kweet Kweet);

    Kweet updateKweet(Kweet Kweet);

    boolean deleteKweet(Kweet Kweet);

    List<Kweet> getTimeline(User user);

    List<Kweet> search(String term);
}
