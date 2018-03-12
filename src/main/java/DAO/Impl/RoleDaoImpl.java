package DAO.Impl;

import DaoInterfaces.IRoleDao;
import Domain.Role;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import java.util.List;

@Default
@Stateless
public class RoleDaoImpl implements IRoleDao {

    public RoleDaoImpl() { }

    @Override
    public List<Role> findAll() {
        return null;
    }

    @Override
    public Role findById(Long id) {
        return null;
    }

    @Override
    public Role findByName(String name) {
        return null;
    }

    @Override
    public Role create(Role role) {
        return null;
    }

    @Override
    public List<Role> create(List<Role> roles) {
        return null;
    }

    @Override
    public Role update(Role role) {
        return null;
    }

    @Override
    public boolean remove(Role role) {
        return false;
    }
}
