package DAO.Impl;

import DaoInterfaces.IUserDao;
import Domain.User;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import java.util.List;

public class UserDaoImpl implements IUserDao {

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User findById(Long id) {
        return null;
    }

    @Override
    public User findByUsername(String username) {
        return null;
    }

    @Override
    public User create(User user) {
        return null;
    }

    @Override
    public List<User> create(List<User> users) {
        return null;
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public boolean remove(User user) {
        return false;
    }
}
