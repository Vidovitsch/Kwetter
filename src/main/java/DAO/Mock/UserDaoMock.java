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

    public User findById(Long id) {
        for (User u : mockUsers) {
            if (u.getId().equals(id)) {
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

    public User create(User user) {
        mockUsers.add(user);
        return user;
    }

    public User update(User user) {
        User u = findById(user.getId());
        if(u == null) {
            mockUsers.add(user);
        } else {
            mockUsers.remove(u);
            mockUsers.add(user);
        }
        return user;
    }

    public boolean remove(User user) {
        return mockUsers.remove(user);
    }

    private ArrayList<User> createDummyUsers() {
        ArrayList<User> users = new ArrayList<User>();
        for (int i = 0; i < 10; i++) {
            User user = new User((long)i + 100, "DummyUser" + i);
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
