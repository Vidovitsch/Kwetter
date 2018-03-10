package DaoInterfaces;

import Domain.Profile;
import Domain.User;
import java.util.List;

public interface IProfileDao {

    List<Profile> findAll();

    Profile findById(Long id);

    Profile findByUser(User user);

    List<Profile> findByName(String name);

    Profile create(Profile profile);

    List<Profile> create(List<Profile> profiles);

    Profile update(Profile profile);

    boolean remove(Profile profile);
}
