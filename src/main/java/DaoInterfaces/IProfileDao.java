package DaoInterfaces;

import Domain.Profile;
import Domain.User;

import java.util.Collection;

public interface IProfileDao {
    Collection<Profile> findAll();
    Profile findById(long id);
    Profile findByUser(User user);
    Collection<Profile> findByName(String name);
    Profile insertProfile(Profile Profile);
    Profile updateProfile(Profile Profile);
    boolean deleteProfile(Profile Profile);
}
