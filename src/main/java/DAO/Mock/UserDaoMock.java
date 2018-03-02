package DAO.Mock;

import DaoInterfaces.IUserDao;
import Domain.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class UserDaoMock implements IUserDao{
    Collection<User> users;
    public UserDaoMock() {
        users = createDummyUsers();
    }

    public Collection<User> findAll() {
        return null;
    }

    public User findById(long id) {
        return null;
    }

    public Collection<User> findByUsername(String username) {
        return null;
    }

    public User insertUser(User user) {
        return null;
    }

    public User updateUser(User user) {
        return null;
    }

    public boolean deleteUser(User user) {
        return false;
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

            dummyUser.setFollowers((HashSet<User>)others);
            dummyUser.setFollowing((HashSet<User>)others);
        }
    }
}