package DAO.Mock;

import DaoInterfaces.IRoleDao;
import Util.MockFactory;
import Domain.Role;
import Domain.User;
import java.util.List;

public class RoleDaoMock implements IRoleDao {

    private List<Role> mockRoles;

    @SuppressWarnings("unchecked")
    public RoleDaoMock(List<User> users) {
        mockRoles = (List<Role>)MockFactory.createMocks(Role.class, 10);
        MockFactory.setNewIds(mockRoles);

        connectDummyRoles(mockRoles, users);
    }

    public List<Role> findAll() {
        return mockRoles;
    }

    public Role findById(Long id) {
        for (Role role : mockRoles) {
            if (role.getId().equals(id)) {
                return role;
            }
        }

        return null;
    }

    public Role findByName(String name) {
        for (Role role : mockRoles) {
            if (role.getName().equals(name)) {
                return role;
            }
        }

        return null;
    }

    public Role create(Role role) {
        MockFactory.setNextId(role, mockRoles);
        mockRoles.add(role);
        return role;
    }

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

    public boolean remove(Role role) {
        return mockRoles.remove(role);
    }

    // For mock purposes
    private void connectDummyRoles(List<Role> roles, List<User> users) {
        for (User user : users) {
            user.setRoles(roles);
        }
        for (Role role : roles) {
            role.setUsers(users);
        }
    }
}
