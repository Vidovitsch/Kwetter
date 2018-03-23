package servlets;

import services.KweetService;
import services.UserService;
import viewmodels.UserUsernameView;

import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named(value = "adminbean")
@ManagedBean
public class AdminServlet {

    @Inject
    UserService userService;

    @Inject
    KweetService kweetService;

    public AdminServlet() {
    }

    public boolean DeleteUser(String username){
        return userService.deleteUser(username);
    }

    public List<UserUsernameView> users(){
        return userService.getUsers();
    }

    public boolean setRole(String username, String role){
        return true;
    }

    public boolean DeleteKweet(long kweetid){
        return kweetService.delete(kweetid);
    }
}
