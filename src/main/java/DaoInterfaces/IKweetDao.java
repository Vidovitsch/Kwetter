package DaoInterfaces;

import Domain.Kweet;
import java.util.List;

public interface IKweetDao {

    List<Kweet> findAll();

    Kweet findById(Long id);

    List<Kweet> findByTerm(String term);

    List<Kweet> findBySenderName(String senderName);

    Kweet create(Kweet kweet);

    List<Kweet> create(List<Kweet> kweets);

    Kweet update(Kweet kweet);

    boolean remove(Kweet kweet);
}
