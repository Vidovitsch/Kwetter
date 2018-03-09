package Service;

import DaoInterfaces.IProfileDao;
import Domain.Profile;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class UserService {

    @EJB
    private IProfileDao profileDao;

    public boolean createProfile(Profile profile) {
        if (profile.getName().equals("") || profile.getName() == null || profile.getUser() == null) {
            return false;
        } else {
            profileDao.create(profile);
            return true;
        }
    }

    public boolean editProfile(Profile profile) {
        return false;
    }
}
