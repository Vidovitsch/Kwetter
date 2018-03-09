package Service;

import DAO.Mock.ProfileDaoMock;
import DAO.Mock.UserDaoMock;
import DaoInterfaces.IProfileDao;
import DaoInterfaces.IUserDao;
import Domain.Profile;
import Exception.*;
import org.apache.commons.validator.UrlValidator;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class ProfileService {

    @EJB
    IUserDao userDao = new UserDaoMock();

    @EJB
    IProfileDao profileDao = new ProfileDaoMock(userDao.findAll());

    public ProfileService() {
    }

    public Profile CreateProfile(Profile p, String username) throws InvalidProfileException {
        if (p.getName() == null || p.getName().isEmpty()) throw new InvalidProfileException("Profile name can't be empty");
        if (p.getwebsite() != null) {
            if(!ValidateUrl(p.getwebsite()))throw new InvalidProfileException("The given web address is invalid");
        }
        p.setUser(userDao.findByUsername(username));
        return profileDao.create(p);
    }

    private boolean ValidateUrl(String url) {
        String[] schemes = {"http", "https"}; // DEFAULT schemes = "http", "https", "ftp"
        UrlValidator urlValidator = new UrlValidator(schemes);
        if (urlValidator.isValid(url)) {
            return true;
        } else {
            return false;
        }
    }

    public Profile EditProfile(Profile p) throws InvalidProfileException {
        if(p.getId() == 0)throw new InvalidProfileException("This profile does not have a valid ID, create one before you edit");
        if(p.getUser() == null)throw new InvalidProfileException("This profile does not have a user linked yet");
        if (p.getName() == null) throw new InvalidProfileException("Profile name can't be empty");
        if (p.getwebsite() != null) {
            if(!ValidateUrl(p.getwebsite()))throw new InvalidProfileException("The given web address is invalid");
        }
        return profileDao.update(p);
    }




}
