package DAO.Mock;

import DaoInterfaces.IProfileDao;
import Domain.Profile;
import Domain.User;
import java.util.ArrayList;
import java.util.List;

public class ProfileDaoMock implements IProfileDao {

    private List<Profile> mockProfiles;

    public ProfileDaoMock(List<User> users) {
        this.mockProfiles = createMockProfiles(users);
    }

    public List<Profile> findAll() {
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

    public List<Profile> findByName(String name) {
        List<Profile> profiles = new ArrayList<Profile>();
        for (Profile profile : mockProfiles) {
            if (profile.getName().equals(name)) {
                profiles.add(profile);
            }
        }

        return profiles;
    }

    public Profile create(Profile profile) {
        mockProfiles.add(profile);

        return profile;
    }

    public Profile update(Profile profile) {
        Profile existingProfile = findById(profile.getId());
        if (existingProfile == null) {
            mockProfiles.add(profile);
        } else {
            mockProfiles.remove(existingProfile);
            mockProfiles.add(profile);
        }
        return profile;
    }

    public boolean remove(Profile profile) {
        return mockProfiles.remove(profile);
    }

    private ArrayList<Profile> createMockProfiles(List<User> users) {
        ArrayList<Profile> profiles = new ArrayList<Profile>();
        for (User user : users) {
            Profile dummyProfile = new Profile((long)0, user.getUsername() + " Test");
            dummyProfile.setUser(user);
            user.setProfile(dummyProfile);
            profiles.add(dummyProfile);
        }

        return profiles;
    }
}
