package dao.mocks;

import dao.interfaces.IUserDao;
import qualifier.Mock;
import util.MockFactory;
import domain.User;
import util.MockService;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import java.util.List;

@Mock
@Stateless
public class UserDaoMock implements IUserDao {

    private List<User> mockUsers;

    public UserDaoMock() {
        mockUsers = MockService.getInstance().getUsers();
    }

    @Override
    public EntityManager getEntityManager() {
        return null;
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
