package DAO.Mock;

import DaoInterfaces.IProfileDao;
import Qualifier.Mock;
import Util.MockFactory;
import Domain.Profile;
import Domain.User;
import Util.MockService;

import javax.enterprise.context.Dependent;
import java.util.ArrayList;
import java.util.List;

@Mock
@Dependent
public class ProfileDaoMock implements IProfileDao {

    private List<Profile> mockProfiles;

    public ProfileDaoMock() {
        mockProfiles = MockService.getInstance().getProfiles();
    }

    @Override
    public List<Profile> findAll() {
        return mockProfiles;
    }

    @Override
    public Profile findById(Long id) {
        for (Profile profile : mockProfiles) {
            if (profile.getId().equals(id)) {
                return profile;
            }
        }

        return null;
    }

    @Override
    public Profile findByUser(User user) {
        for (Profile profile : mockProfiles) {
            if (profile.getUser() == user) {
                return profile;
            }
        }

        return null;
    }

    @Override
    public Profile create(Profile profile) {
        MockFactory.setNextId(profile, mockProfiles);
        mockProfiles.add(profile);
        return profile;
    }

    @Override
    public List<Profile> create(List<Profile> profiles) {
        MockFactory.setNextIds(profiles, mockProfiles);
        mockProfiles.addAll(profiles);
        return profiles;
    }

    @Override
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

    @Override
    public boolean remove(Profile profile) {
        return mockProfiles.remove(profile);
    }
}
