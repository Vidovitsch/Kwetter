package DaoInterfaces;

import Domain.Profile;
import Domain.User;

import java.util.Collection;
import java.util.List;

public interface IProfileDao {

    Collection<Profile> findAll();

    Profile findById(long id);

    Profile findByUser(User user);

    List<Profile> findByName(String name);

    Profile insertProfile(Profile Profile);

    Profile updateProfile(Profile Profile);

    boolean deleteProfile(Profile Profile);
}
