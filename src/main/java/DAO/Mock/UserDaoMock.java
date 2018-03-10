package DAO.Mock;

import DaoInterfaces.IUserDao;
import Util.MockFactory;
import Domain.User;
import java.util.ArrayList;
import java.util.List;

public class UserDaoMock implements IUserDao {

    private List<User> mockUsers;

    @SuppressWarnings("unchecked")
    public UserDaoMock() {
        mockUsers = (List<User>)MockFactory.createMocks(User.class, 10);
        MockFactory.setNewIds(mockUsers);

        connectDummyUsers(mockUsers);
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
        MockFactory.setNextId(user, mockUsers);
        mockUsers.add(user);
        return user;
    }

    public List<User> create(List<User> users) {
        MockFactory.setNextIds(users, mockUsers);
        mockUsers.addAll(users);
        return users;
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

    // For mock purposes
    private void connectDummyUsers(List<User> users) {
        for (User dummyUser : users) {
            List<User> others = new ArrayList<>(users);
            others.remove(dummyUser);

            dummyUser.setFollowers(others);
            dummyUser.setFollowing(others);
        }
    }
}
