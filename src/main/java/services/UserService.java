package services;

import dao.implementations.RoleDaoImpl;
import dao.interfaces.IProfileDao;
import dao.interfaces.IRoleDao;
import dao.interfaces.IUserDao;
import domain.Profile;
import domain.Role;
import domain.User;
import viewmodels.JsfUser;
import viewmodels.OtherUserView;
import viewmodels.UserUsernameView;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Named(value = "userService")
@Stateless
public class UserService {

    @Inject
    private IUserDao userDao;

    @Inject
    private IProfileDao profileDao;

    @Inject
    private IRoleDao roleDao;

    public void setUserDao(IUserDao userDao) {
        this.userDao = userDao;
    }

    public void setRoleDao(IRoleDao roleDao) {
        this.roleDao = roleDao;
    }

    public void setProfileDao(IProfileDao profileDao) {
        this.profileDao = profileDao;
    }

    /**
     * Adds a user(follwoing) that this user will follow
     *
     * @param username   of the user that follows
     * @param following: the user that will get followed
     * @return true if the user follows a user successfully, false if the user already follows the user
     */
    public Boolean addFollowing(String username, String following) {
        User user = userDao.findByUsername(username);
        User followingUser = userDao.findByUsername(following);

        List<User> followingUsers = user.getFollowing();
        if (followingUsers.contains(followingUser)) {
            return false;
        } else {
            followingUsers.add(followingUser);
            followingUser.getFollowers().add(user);
            return true;
        }
    }

    /**
     * Gets a list of the followers of the user with the given username.
     * The list is in a view format and consists only of useful visual data.
     *
     * @param username of the user with the followers
     * @return a list of follwers in a view format
     */
    public List<OtherUserView> getFollowers(String username) {
        User user = userDao.findByUsername(username);
        return generateOtherUserViews(user.getFollowers());
    }

    /**
     * Gets a list of the following of the user with the given username.
     * The list is in a view format and consists only of useful visual data.
     *
     * @param username of the user with the following
     * @return a list of following in a view format
     */
    public List<OtherUserView> getFollowing(String username) {
        User user = userDao.findByUsername(username);
        return generateOtherUserViews(user.getFollowing());
    }

    private List<OtherUserView> generateOtherUserViews(List<User> users) {
        ArrayList<OtherUserView> otherUserViews = new ArrayList<>();
        for (User user : users) {
            Profile p = profileDao.findByUser(user);
            String image;
            if (p != null) {
                image = p.getImage();
            } else {
                image = null;
            }
            otherUserViews.add(new OtherUserView(user.getUsername(), image));
        }

        return otherUserViews;
    }

    public boolean deleteUser(String username) {
        try {
            userDao.remove(userDao.findByUsername(username));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<UserUsernameView> getUsers() {
        List<UserUsernameView> users = new ArrayList<>();
        for (User u : userDao.findAll()) {
            users.add(new UserUsernameView(u.getUsername(), u.getId()));
        }
        return users;
    }

    public List<UserUsernameView> searchUsers(String filter) {
        List<UserUsernameView> users = new ArrayList<>();
        for (User u : userDao.findAll()) {
            if (u.getUsername().toLowerCase().contains(filter.toLowerCase())) {
                users.add(new UserUsernameView(u.getUsername(), u.getId()));
            }
        }
        return users;
    }

    public List<JsfUser> searchJsfUsers(String filter) {
        List<JsfUser> users = new ArrayList<>();
        for (User u : userDao.findAll()) {
            if (u.getUsername().toLowerCase().contains(filter.toLowerCase())) {
                users.add(new JsfUser(u.getId(), u.getUsername(), rolesString(u)));
            }
        }
        return users;
    }

    public List<JsfUser> searchJsfUsers() {
        List<JsfUser> users = new ArrayList<>();
        for (User u : userDao.findAll()) {
            users.add(new JsfUser(u.getId(), u.getUsername(), rolesString(u)));
        }
        return users;
    }

    private String rolesString(User u) {
        String roles = "";
        boolean first = true;
        if (u.getRoles() != null) {
            for (Role r : u.getRoles()) {
                if (first) {
                    roles += r.getName();
                    first = false;
                } else {
                    roles += ", " + r.getName();
                }
            }
        }
        return roles;
    }

    public boolean UpdateUserRoles(String username, String roles) {
        try {
            User u = userDao.findByUsername(username);
            roles = roles.replaceAll("\\s+", "");
            List<Role> newRoles = new ArrayList<>();
            String[] rolesArray = roles.split(",");
            for (String role : rolesArray) {
                Role r = roleDao.findByName(role);
                if (r == null) {
                    Role newRole = new Role();
                    newRole.setName(role);
                    roleDao.create(newRole);
                }
                newRoles.add(r);
            }
            u.setRoles(newRoles);
            userDao.update(u);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

