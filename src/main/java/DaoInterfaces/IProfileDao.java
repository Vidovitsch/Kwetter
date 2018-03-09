package DaoInterfaces;

import Domain.Profile;
import Domain.User;
import java.util.List;

public interface IProfileDao {

    List<Profile> findAll();

    Profile findById(long id);

    Profile findByUser(User user);

    List<Profile> findByName(String name);

    Profile create(Profile Profile);

    Profile update(Profile Profile);

    boolean remove(Profile Profile);
}
