package services;

import dao.interfaces.IUserDao;
import domain.User;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
@Default
public class AuthenticationService {

    private PasswordAuthentication passwordAuthentication;

    @Inject
    private IUserDao userDao;

    public AuthenticationService() {
        passwordAuthentication = new PasswordAuthentication();
    }

    public boolean registerUser(String username, String password){
        String hashedPassword = passwordAuthentication.hash(password.toCharArray());
        User u = new User();
        u.setPasswordhash(hashedPassword);
        u.setUsername(username);
        userDao.create(u);
        User createdUser = userDao.findByUsername(u.getUsername());
        try{
            if(createdUser.getId()!= null)return true;
        } catch (Exception e){
            Logger.getAnonymousLogger().log(Level.SEVERE, e.getMessage());
        }
        return false;
    }

    public User login(String username, String password){
        User u = null;
        User reguestedUser = userDao.findByUsername(username);
        if(passwordAuthentication.authenticate(password.toCharArray(), reguestedUser.getPasswordhash())) return reguestedUser;
        return u;
    }
}
