package DAO.Impl;

import DaoInterfaces.IRoleDao;
import Domain.Role;

import javax.ejb.Stateless;
import java.util.List;

public class RoleDaoImpl implements IRoleDao {

    public List<Role> findAll() {
        return null;
    }

    public Role findById(Long id) {
        return null;
    }

    public Role findByName(String name) {
        return null;
    }

    public Role create(Role role) {
        return null;
    }

    @Override
    public List<Role> create(List<Role> roles) {
        return null;
    }

    public Role update(Role role) {
        return null;
    }

    public boolean remove(Role role) {
        return false;
    }
}
