package dao.mocks;

import dao.interfaces.IRoleDao;
import qualifier.Mock;
import util.MockFactory;
import domain.Role;
import util.MockService;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import java.util.List;

@Mock
@Stateless
public class RoleDaoMock implements IRoleDao {

    private List<Role> mockRoles;

    public RoleDaoMock() {
        mockRoles = MockService.getInstance().getRoles();
    }

    @Override
    public EntityManager getEntityManager() {
        return null;
    }

    @Override
    public List<Role> findAll() {
        return mockRoles;
    }

    @Override
    public Role findById(Long id) {
        for (Role role : mockRoles) {
            if (role.getId().equals(id)) {
                return role;
            }
        }

        return null;
    }

    @Override
    public Role findByName(String name) {
        for (Role role : mockRoles) {
            if (role.getName().equals(name)) {
                return role;
            }
        }

        return null;
    }

    @Override
    public Role create(Role role) {
        MockFactory.setNextId(role, mockRoles);
        mockRoles.add(role);
        return role;
    }

    @Override
    public List<Role> create(List<Role> roles) {
        MockFactory.setNextIds(roles, mockRoles);
        mockRoles.addAll(roles);
        return roles;
    }

    @Override
    public Role update(Role role) {
        Role existingRole = findById(role.getId());
        if (existingRole == null) {
            mockRoles.add(role);
        } else {
            mockRoles.remove(existingRole);
            mockRoles.add(role);
        }
        return role;
    }

    @Override
    public boolean remove(Role role) {
        return mockRoles.remove(role);
    }
}
