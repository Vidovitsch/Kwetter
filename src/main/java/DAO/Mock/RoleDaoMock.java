package DAO.Mock;

import DaoInterfaces.IRoleDao;
import Domain.Role;
import Domain.User;

import java.util.ArrayList;
import java.util.Collection;

public class RoleDaoMock implements IRoleDao{

    private Collection<Role> mockRoles;

    public RoleDaoMock(Collection<User> users) {
        this.mockRoles = createmockRoles(users);
    }

    public Collection<Role> findAll() {
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

    public Role insertRole(Role role) {
        mockRoles.add(role);

        return role;
    }

    public Role updateRole(Role role) {
        Role existingRole = findById(role.getId());
        if (existingRole == null) {
            mockRoles.add(role);
        } else {
            ArrayList<Role> roles = (ArrayList<Role>)mockRoles;
            roles.set(roles.indexOf(existingRole), role);
        }

        return role;
    }

    public boolean deleteRole(Role role) {
        return mockRoles.remove(role);
    }

    private ArrayList<Role> createmockRoles(Collection<User> users) {
        ArrayList<Role> roles = new ArrayList<Role>();

        Role role1 = new Role("Kweeter");
        Role role2 = new Role("Moderator");
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
