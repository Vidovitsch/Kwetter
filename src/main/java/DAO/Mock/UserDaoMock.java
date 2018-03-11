package DAO.Mock;

import DaoInterfaces.IUserDao;
import Qualifier.Mock;
import Util.MockFactory;
import Domain.User;
import Util.MockService;

import javax.enterprise.context.Dependent;
import java.util.ArrayList;
import java.util.List;

@Mock
@Dependent
public class UserDaoMock implements IUserDao {

    private List<User> mockUsers;

    public UserDaoMock() {
        mockUsers = MockService.getInstance().getUsers();
    }

    @Override
    public List<User> findAll() {
        return mockUsers;
    }

    @Override
    public User findById(Long id) {
        for (User u : mockUsers) {
            if (u.getId().equals(id)) {
                return u;
            }
        }
        return null;
    }

    @Override
    public User findByUsername(String username) {
        for (User u : mockUsers) {
            if (u.getUsername().equals(username)) {
                return u;
            }
        }
        return null;
    }

    @Override
    public User create(User user) {
        MockFactory.setNextId(user, mockUsers);
        mockUsers.add(user);
        return user;
    }

    @Override
    public List<User> create(List<User> users) {
        MockFactory.setNextIds(users, mockUsers);
        mockUsers.addAll(users);
        return users;
    }

    @Override
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

    @Override
    public boolean remove(User user) {
        return mockUsers.remove(user);
    }
}
