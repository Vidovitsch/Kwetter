package DAO.Mock;

import DaoInterfaces.IUserDao;
import Domain.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class UserDaoMock implements IUserDao {
    Collection<User> mockUsers;

    public UserDaoMock() {
        mockUsers = createDummyUsers();
    }

    public Collection<User> findAll() {
        return mockUsers;
    }

    public User findById(long id) {
        for (User u : mockUsers) {
            if (u.getId() == id) {
                return u;
            }
        }
        return null;
    }

    public User findByUsername(String username) {
        for (User u : mockUsers) {
            if (u.getUsername().equals(username)) {
                return u;
            }
        }
        return null;
    }

    public User insertUser(User user) {
        mockUsers.add(user);
        return user;
    }

    public User updateUser(User user) {
        User u = findById(user.getId());
        if(u == null){
            mockUsers.add(user);
        }else{
            mockUsers.remove(u);
            mockUsers.add(user);
        }
        return user;
    }

    public boolean deleteUser(User user) {
        return mockUsers.remove(user);
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
