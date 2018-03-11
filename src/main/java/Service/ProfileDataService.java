package Service;

import DAO.Mock.KweetDaoMock;
import DAO.Mock.ProfileDaoMock;
import DAO.Mock.UserDaoMock;
import DaoInterfaces.IKweetDao;
import DaoInterfaces.IProfileDao;
import DaoInterfaces.IUserDao;
import Domain.Profile;
import ViewModels.ProfileDataView;

public class ProfileDataService {
    IUserDao userDao = new UserDaoMock();
    IKweetDao kweetDao = new KweetDaoMock(userDao.findAll());
    IProfileDao profileDao = new ProfileDaoMock(userDao.findAll());

    public ProfileDataView GetProfileData(long userid){
        Profile p = userDao.findById(userid).getProfile();
        ProfileDataView profileDataView = new ProfileDataView(p.getName(), p.getLocation(), p.getwebsite(), p.getBiography());
        return profileDataView;
    }
}
