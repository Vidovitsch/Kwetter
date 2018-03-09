package DaoInterfaces;

import Domain.Kweet;
import Domain.User;
import java.util.List;

public interface IKweetDao {

    List<Kweet> findAll();

    Kweet findById(long id);

    List<Kweet> findByTerm(String term);

    List<Kweet> findBySenderName(String senderName);

    Kweet create(Kweet Kweet);

    Kweet update(Kweet Kweet);

    boolean remove(Kweet Kweet);
}
