package DAO.Mock;

import DaoInterfaces.IRoleDao;
import Qualifier.Mock;
import Util.MockFactory;
import Domain.Role;
import Util.MockService;

import java.util.List;

@Mock
public class RoleDaoMock implements IRoleDao {

    private List<Role> mockRoles;

    public RoleDaoMock() {
        mockRoles = MockService.getInstance().getRoles();
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
