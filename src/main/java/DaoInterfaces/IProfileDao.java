package DaoInterfaces;

import Domain.Profile;

import java.util.Collection;

public interface IProfileDao {
    Collection<Profile> findAll();
    Profile findById();
    Collection<Profile> findByName();
    Profile insertProfile(Profile Profile);
    Profile updateProfile(Profile Profile);
    boolean deleteProfile(Profile Profile);
}
