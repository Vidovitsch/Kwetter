package DAO.Impl;

import DaoInterfaces.IUserDao;
import Domain.User;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class UserDaoImpl implements IUserDao {

    public List<User> findAll() {
        return null;
    }

    public User findById(Long id) {
        return null;
    }

    public User findByUsername(String username) {
        return null;
    }

    public User create(User user) {
        return null;
    }

    @Override
    public List<User> create(List<User> users) {
        return null;
    }

    public User update(User user) {
        return null;
    }

    public boolean remove(User user) {
        return false;
    }
}
