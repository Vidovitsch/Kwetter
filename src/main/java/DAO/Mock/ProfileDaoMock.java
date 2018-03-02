package DAO.Mock;

import DaoInterfaces.IProfileDao;
import Domain.Profile;
import Domain.User;
import java.util.ArrayList;
import java.util.Collection;

public class ProfileDaoMock implements IProfileDao {

    private Collection<Profile> dummyProfiles;

    public ProfileDaoMock(Collection<User> users) {
        this.dummyProfiles = createDummyProfiles(users);
    }

    public Collection<Profile> findAll() {
        return dummyProfiles;
    }

    public Profile findById(long id) {
        for (Profile profile : dummyProfiles) {
            if (profile.getId() == id) {
                return profile;
            }
        }

        return null;
    }

    public Profile findByUser(User user) {
        for (Profile profile : dummyProfiles) {
            if (profile.getUser() == user) {
                return profile;
            }
        }

        return null;
    }

    public Collection<Profile> findByName(String name) {
        Collection<Profile> profiles = new ArrayList<Profile>();
        for (Profile profile : dummyProfiles) {
            if (profile.getName().equals(name)) {
                profiles.add(profile);
            }
        }

        return profiles;
    }

    public Profile insertProfile(Profile profile) {
        dummyProfiles.add(profile);

        return profile;
    }

    public Profile updateProfile(Profile profile) {
        Profile existingProfile = findById(profile.getId());
        if (existingProfile == null) {
            dummyProfiles.add(profile);
        } else {
            ArrayList<Profile> profiles = (ArrayList<Profile>)dummyProfiles;
            profiles.set(profiles.indexOf(existingProfile), profile);
        }

        return profile;
    }

    public boolean deleteProfile(Profile profile) {
        return dummyProfiles.remove(profile);
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
