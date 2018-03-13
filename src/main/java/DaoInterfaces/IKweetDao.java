package DaoInterfaces;

import Domain.Kweet;
import Domain.User;

import java.util.List;

public interface IKweetDao {

    List<Kweet> findAll();

    Kweet findById(Long id);

    List<Kweet> findBySender(User sender);

    Kweet create(Kweet kweet);

    List<Kweet> create(List<Kweet> kweets);

    Kweet update(Kweet kweet);

    boolean remove(Kweet kweet);
}
