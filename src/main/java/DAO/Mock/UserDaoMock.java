package DAO.Mock;

import DaoInterfaces.IUserDao;
import Domain.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class UserDaoMock implements IUserDao {
    Collection<User> users;

    public UserDaoMock() {
        users = createDummyUsers();
    }

    public Collection<User> findAll() {
        return users;
    }

    public User findById(long id) {
        for (User u : users) {
            if (u.getId() == id) {
                return u;
            }
        }
        return null;
    }

    public User findByUsername(String username) {
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                return u;
            }
        }
        return null;
    }

    public User insertUser(User user) {
        users.add(user);
        return user;
    }

    public User updateUser(User user) {
        User u = findById(user.getId());
        if(u == null){
            u = insertUser(user);
        }
        return u;
    }

    public boolean deleteUser(User user) {
        return users.remove(user);
    }

    private ArrayList<User> createDummyUsers() {
        ArrayList<User> users = new ArrayList<User>();
        for (int i = 0; i < 10; i++) {
            users.add(new User("DummyUser" + i));
        }
        connectDummyUsers(users);
        return users;
    }

    private void connectDummyUsers(Collection<User> users) {
        for (User dummyUser : users) {
            Collection<User> others = new HashSet<User>(users);
            others.remove(dummyUser);

            dummyUser.setFollowers((HashSet<User>) others);
            dummyUser.setFollowing((HashSet<User>) others);
        }
    }
}
