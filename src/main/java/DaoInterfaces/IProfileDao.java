package DaoInterfaces;

import Domain.Profile;

import java.util.List;

public interface IProfileDao {
    List<Profile> findAll();
    Profile findById();
    List<Profile> findByName();
    Profile insertProfile(Profile Profile);
    Profile updateProfile(Profile Profile);
    boolean deleteProfile(Profile Profile);
}
