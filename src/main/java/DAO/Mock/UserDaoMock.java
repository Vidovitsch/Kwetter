package DAO.Mock;

import DaoInterfaces.IUserDao;
import Domain.User;
import java.util.ArrayList;
import java.util.List;

public class UserDaoMock implements IUserDao {

    private List<User> mockUsers;

    public UserDaoMock() {
        mockUsers = createDummyUsers();
    }

    public List<User> findAll() {
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
        if(u == null) {
            mockUsers.add(user);
        } else {
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
            User user = new User(0, "DummyUser" + i);
            user.setId(i + 100);
            users.add(user);
        }
        connectDummyUsers(users);
        return users;
    }

    private void connectDummyUsers(List<User> users) {
        for (User dummyUser : users) {
            List<User> others = new ArrayList<User>(users);
            others.remove(dummyUser);

            dummyUser.setFollowers(others);
            dummyUser.setFollowing(others);
        }
    }
}
