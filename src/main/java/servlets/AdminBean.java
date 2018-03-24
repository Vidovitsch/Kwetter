package servlets;

import domain.Kweet;
import domain.User;
import viewmodels.JsfKweet;

import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named(value = "AdminBean")
@ManagedBean
@SessionScoped
public class AdminBean implements Serializable {

    public String getKweetfilter() {
        return kweetfilter;
    }

    public void setKweetfilter(String kweetfilter) {
        this.kweetfilter = kweetfilter;
    }


    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }

    public String getUserfilter() {
        return userfilter;
    }

    public void setUserfilter(String userfilter) {
        this.userfilter = userfilter;
    }

    private boolean adminrole;

    public boolean isAdminrole() {
        return adminrole;
    }

    public void setAdminrole(boolean adminrole) {
        this.adminrole = adminrole;
    }

    private String userfilter;
    private String kweetfilter;

    public void setSelectedKweet(JsfKweet selectedKweet) {
        this.selectedKweet = selectedKweet;
    }

    public JsfKweet getSelectedKweet() {
        return selectedKweet;
    }

    private JsfKweet selectedKweet;
    private User selectedUser;
}
