package dao.mocks;

import dao.interfaces.IProfileDao;
import qualifier.Mock;
import util.MockFactory;
import domain.Profile;
import domain.User;
import util.MockService;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import java.util.List;

@Mock
@Stateless
public class ProfileDaoMock implements IProfileDao {

    private List<Profile> mockProfiles;

    public ProfileDaoMock() {
        mockProfiles = MockService.getInstance().getProfiles();
    }

    @Override
    public EntityManager getEntityManager() {
        return null;
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
