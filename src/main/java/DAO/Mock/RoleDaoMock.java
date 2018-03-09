package DAO.Mock;

import DaoInterfaces.IRoleDao;
import Domain.Role;
import Domain.User;
import java.util.ArrayList;
import java.util.List;

public class RoleDaoMock implements IRoleDao {

    private List<Role> mockRoles;

    public RoleDaoMock(List<User> users) {
        this.mockRoles = createMockRoles(users);
    }

    public List<Role> findAll() {
        return mockRoles;
    }

    public Role findById(long id) {
        for (Role role : mockRoles) {
            if (role.getId() == id) {
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

    private ArrayList<Role> createMockRoles(List<User> users) {
        ArrayList<Role> roles = new ArrayList<Role>();

        Role role1 = new Role((long)0, "Kweeter");
        Role role2 = new Role((long)0, "Moderator");
        roles.add(role1);
        roles.add(role2);

        for (User user : users) {
            user.setRoles(roles);
        }
        role1.setUsers(users);
        role2.setUsers(users);

        return roles;
    }
}
