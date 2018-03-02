package DaoInterfaces;

import Domain.Kweet;

import java.util.List;

public interface IKweetDao {
    List<Kweet> findAll();
    Kweet findById();
    List<Kweet> findByName();
    Kweet insertKweet(Kweet Kweet);
    Kweet updateKweet(Kweet Kweet);
    boolean deleteKweet(Kweet Kweet);
}
