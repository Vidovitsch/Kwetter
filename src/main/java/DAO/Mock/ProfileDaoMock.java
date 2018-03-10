package DAO.Mock;

import DaoInterfaces.IProfileDao;
import Domain.Role;
import Util.MockFactory;
import Domain.Profile;
import Domain.User;
import java.util.ArrayList;
import java.util.List;

public class ProfileDaoMock implements IProfileDao {

    private List<Profile> mockProfiles;

    @SuppressWarnings("unchecked")
    public ProfileDaoMock(List<User> users) {
        mockProfiles = (List<Profile>)MockFactory.createMocks(Profile.class, 10);
        MockFactory.setNewIds(mockProfiles);

        connectDummyProfiles(mockProfiles, users);
    }

    public List<Profile> findAll() {
        return mockProfiles;
    }

    public Profile findById(Long id) {
        for (Profile profile : mockProfiles) {
            if (profile.getId().equals(id)) {
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
        MockFactory.setNextId(profile, mockProfiles);
        mockProfiles.add(profile);
        return profile;
    }

    public List<Profile> create(List<Profile> profiles) {
        MockFactory.setNextIds(profiles, mockProfiles);
        mockProfiles.addAll(profiles);
        return profiles;
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

    // For mock purposes
    private void connectDummyProfiles(List<Profile> profiles, List<User> users) {
        if (users.size() >= profiles.size()) {
            for (int i = 0; i < profiles.size(); i++) {
                profiles.get(i).setUser(users.get(i));
                users.get(i).setProfile(profiles.get(i));
            }
        }
    }
}
