package DaoInterfaces;

import Domain.Kweet;

import java.util.Collection;

public interface IKweetDao {
    Collection<Kweet> findAll();
    Kweet findById();
    Collection<Kweet> findByName();
    Kweet insertKweet(Kweet Kweet);
    Kweet updateKweet(Kweet Kweet);
    boolean deleteKweet(Kweet Kweet);
}
