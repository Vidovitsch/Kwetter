package DAO.Mock;

import DaoInterfaces.IProfileDao;
import Domain.Profile;
import Domain.User;

import java.util.ArrayList;
import java.util.Collection;

public class ProfileDaoMock implements IProfileDao {

    private Collection<Profile> mockProfiles;

    public ProfileDaoMock(Collection<User> users) {
        this.mockProfiles = createMockProfiles(users);
    }

    public Collection<Profile> findAll() {
        return mockProfiles;
    }

    public Profile findById(long id) {
        for (Profile profile : mockProfiles) {
            if (profile.getId() == id) {
                return profile;
            }
        }

        return null;
    }

    public Profile findByUser(User user) {
        for (Profile profile : mockProfiles) {
            if (profile.getUser() == user) {
                return profile;
            }
        }

        return null;
    }

    public Collection<Profile> findByName(String name) {
        Collection<Profile> profiles = new ArrayList<Profile>();
        for (Profile profile : mockProfiles) {
            if (profile.getName().equals(name)) {
                profiles.add(profile);
            }
        }

        return profiles;
    }

    public Profile insertProfile(Profile profile) {
        mockProfiles.add(profile);

        return profile;
    }

    public Profile updateProfile(Profile profile) {
        Profile existingProfile = findById(profile.getId());
        if (existingProfile == null) {
            mockProfiles.add(profile);
        } else {
            mockProfiles.remove(existingProfile);
            mockProfiles.add(profile);
        }
        return profile;
    }

    public boolean deleteProfile(Profile profile) {
        return mockProfiles.remove(profile);
    }

    private ArrayList<Profile> createMockProfiles(Collection<User> users) {
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
