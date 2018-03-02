package DAO;

import DaoInterfaces.IProfileDao;
import Domain.Profile;
import Domain.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ProfileDaoMock implements IProfileDao{
    Collection<Profile> profiles;

    public ProfileDaoMock(Collection<User> users) {
        this.profiles = createDummyProfiles(users);
    }

    public Collection<Profile> findAll() {
        return null;
    }

    public Profile findById(long id) {
        return null;
    }

    public Profile findByUser(User user) {
        return null;
    }

    public Collection<Profile> findByName(String name) {
        return null;
    }

    public Profile insertProfile(Profile Profile) {
        return null;
    }

    public Profile updateProfile(Profile Profile) {
        return null;
    }

    public boolean deleteProfile(Profile Profile) {
        return false;
    }
    private ArrayList<Profile> createDummyProfiles(Collection<User> users) {
        ArrayList<Profile> profiles = new ArrayList<Profile>();

        for (User user : users) {
            Profile dummyProfile = new Profile(user.getUsername() + " Test");
            dummyProfile.setUser(user);
            user.setProfile(dummyProfile);
            profiles.add(dummyProfile);
        }

        return profiles;
    }
}
