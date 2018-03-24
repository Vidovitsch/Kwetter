package servlets;

import domain.Kweet;
import domain.User;
import services.KweetService;
import services.UserService;
import viewmodels.TimelineItem;
import viewmodels.UserUsernameView;

import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named(value = "adminbean1")
@ManagedBean
public class AdminServlet implements Serializable{

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

    public List<UserUsernameView> users(String filter){
        if(filter.equals(null) || filter.equals("")){
            return users();
        }
        return userService.searchUsers(filter);
    }


    public boolean setRole(String username, String role){
        return true;
    }

    public boolean DeleteKweet(long kweetid){
        return kweetService.delete(kweetid);
    }

    public List<TimelineItem> kweets(){
        return kweetService.allKweets();
    }
    public List<TimelineItem> kweets(String filter){
        if(filter.equals(null) || filter.equals("")){
            return kweets();
        }
        return kweetService.search(filter);
    }

    public String logintext(){
        return "log in here";
    }
}
